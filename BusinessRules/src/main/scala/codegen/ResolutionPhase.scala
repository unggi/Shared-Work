package codegen

import codegen.TreeUtilities._
import codegen.model.Association
import codegen.symbols._
import org.antlr.v4.runtime.tree.ParseTreeWalker
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

//
// Find every model reference and resolve it depending on whether it is
// in a definition or a validation rule.
//
class ResolutionPhase(symbolTable: SymbolTable, annotator: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener {

  override def enterValidationRule(ctx: ValidationRuleContext): Unit = {
    val walker = new ParseTreeWalker()
    val resolver = new ValidationRuleResolutionPhase(annotator)
    walker.walk(resolver, ctx.constraint())
  }

  override def enterDefinition(ctx: DefinitionContext): Unit = {
    val walker = new ParseTreeWalker()
    val resolver = new DefinitionResolutionPhase(annotator)
    walker.walk(resolver, ctx)
  }
}

//
// Analyze a definition to resolve its' model references.
//
class DefinitionResolutionPhase(annotator: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener with TypeChecking {

  override def enterModelReference(ctx: ModelReferenceContext): Unit = {
    val scopeOpt = annotator.scopes(ctx)
    assume(scopeOpt.isDefined, s"Scope not found for parse tree node: ${nameOf(ctx)}")

    val scope = scopeOpt.get.asInstanceOf[DefinitionScope]
    assume(scope.parent.isDefined, s"Current Scope does not have a parent: $scope")

    val base = root(ctx)
    val path = pathComponents(ctx)

    val line = ctx.start.getLine
    val column = ctx.start.getCharPositionInLine

    val symbol =
      scope.resolve(base) match {
        case Some(s: Parameter) =>
          checkParameterRefTypes(ParameterReference(s, path), line, column)
        case Some(other) => require(other.isInstanceOf[Parameter]); null
        case None =>
          // By default choose first parameter as the implicit parameter - ???
          checkParameterRefTypes(ParameterReference(scope.parameters.head, path), line, column)
      }

    ctx.symbol = symbol
  }
}

class ValidationRuleResolutionPhase(annotator: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener with TypeChecking {

  override def enterModelReference(ctx: ModelReferenceContext): Unit =
    ctx.symbol =
      annotator.scopes(ctx) match {
        case Some(scope) =>
          scope.resolve(root(ctx), pathComponents(ctx)) match {
            case ref: ParameterReference =>
              checkParameterRefTypes(ref, ctx.start.getLine, ctx.start.getCharPositionInLine)
            case ndx: CollectionIndexReference =>
              ndx
          }
        case None =>
          failure(s"Scope not found for parse tree node: ${nameOf(ctx)}")
      }


  override def enterDefinedTermReferenceTerm(ctx: DefinedTermReferenceTermContext): Unit =
    annotator.scopes(ctx) match {
      case Some(scope) =>
        val name = ctx.frag.getText.stripPrefix("<<").stripSuffix(">>")
        val defn = scope.resolve(name)
        // TODO - Insert the defined term here ????
        System.err.println(s"The definition [$name]  is : " + defn)
      case None =>
        failure(s"Scope not found for parse tree node: ${nameOf(ctx)}")
    }

  //
  // At the point where the constraint is entered for a collection member test,
  // the symbol has been declared but we don't know the collection it applies to yet
  // because the collectionRef (i.e. a model reference) has't been established.
  // This method sets the collection member scope's index symbol to the fully known
  // reference to a symbol.
  //
  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    val paramRef = ctx.collectionRef.symbol.asInstanceOf[ParameterReference]
    debug(s"CollectionMemberConstraint - $paramRef")

    val association: Option[Association] = paramRef.classifier.findAssociationByTargetName(paramRef.components.last)

    val scope = annotator.scopes(ctx).get.asInstanceOf[CollectionMemberScope]
    scope.indexSymbol = Option(CollectionIndexSymbol("_", association.get))
  }
}


trait TypeChecking {

  def checkParameterRefTypes(ref: ParameterReference, line: Int, column: Int): Symbol = {
    var cls = ref.parameter.classifier

    // Try walking the classifiers first and then try associations to match the component name.
    for (component <- ref.components.tail)
      cls.findClassifierOfProperty(component) match {
        case Some(classifier) =>
          cls = classifier
        case None =>
          cls.findAssociationByTargetName(component) match {
            case Some(association) =>
              cls = association.target
            case None =>
              failure(s"""Member \"$component\" of ${cls.name} not found in path ${ref.asComponents.mkString(".")} at $line:$column""")
          }
      }
    // Reference is valid if we get to this point.
    ref
  }

  def checkCollectionIndexReference(ref: CollectionIndexReference, line: Int, column: Int): Symbol = {
    ref
  }

}
