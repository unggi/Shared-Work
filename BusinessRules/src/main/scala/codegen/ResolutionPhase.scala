package codegen

import codegen.TreeUtilities._
import codegen.symbols._
import org.antlr.v4.runtime.tree.ParseTreeWalker
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

//
// Find every model reference and resolve it depending on wheterh it is in a definition or a validation rule.
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

class DefinitionResolutionPhase(annotator: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener {

  override def enterModelReference(ctx: ModelReferenceContext): Unit = {
    System.err.println(s"Definition Scope: parent is <${nameOf(ctx.getParent)}>")

    val scopeOpt = annotator.scopes(ctx)
    assume(scopeOpt.isDefined, s"Scope not found for parse tree node: ${nameOf(ctx)}")

    val scope = scopeOpt.get.asInstanceOf[DefinitionScope]
    assume(scope.parent.isDefined, s"Current Scope does not have a parent: $scope")

    val base = root(ctx)
    val path = pathComponents(ctx)

    val symbol =
      scope.resolve(base) match {
        case Some(s: Parameter) => ParameterReference(s, path)
        case Some(other) => require(other.isInstanceOf[Parameter]); null
        case None =>
          // By default choose first parameter as the implicit parameter - ???
          ParameterReference(scope.parameters.head, path)
      }

    ctx.symbol = symbol
    System.err.println(s"Definition Resolved <$base> to <$symbol>: \nfor node ${ctx.hashCode()}")
  }
}

class ValidationRuleResolutionPhase(annotator: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener {

  override def enterModelReference(ctx: ModelReferenceContext): Unit =
    ctx.symbol =
      annotator.scopes(ctx) match {
        case Some(scope) =>
          scope.resolve(root(ctx), pathComponents(ctx)) match {
            case ref: ParameterReference =>
              try {
                checkParameterRefTypes(ref)
              }
              catch {
                case e: Throwable => failure(s"""Error ${e.getMessage} at line ${ctx.start.getLine}""")
              }
              ref
          }
        case None =>
          failure(s"Scope not found for parse tree node: ${nameOf(ctx)}")
      }

  def checkParameterRefTypes(ref: ParameterReference): Unit = {
    var cls = ref.parameter.classifier
    for (component <- ref.components) {

      cls.findClassifierOfProperty(component) match {
        case Some(classifier) =>
          cls = classifier
        case None =>
          cls.findAssociationByTargetName(component) match {
            case Some(association) =>
              cls = association.target
            case None =>
              failure(s"""Member \"$component\" of ${cls.name} not found in path ${ref.asComponents.mkString(".")}""")
          }
      }
    }
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

    val scope = annotator.scopes(ctx).get.asInstanceOf[CollectionMemberScope]
    scope.indexSymbol = Option(CollectionIndexSymbol("_", ctx.collectionRef.symbol))

  }

}



