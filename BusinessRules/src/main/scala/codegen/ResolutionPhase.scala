package codegen

import java.rmi.UnexpectedException

import codegen.symbols._
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTreeWalker
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

import scala.collection.JavaConversions._
import scala.collection.immutable
import TreeUtilities._

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
        case Some(s: Parameter) => new ParameterReference(s, path)
        case Some(other) => require(other.isInstanceOf[Parameter]); null
        case None =>
          // Default to the first parameter in the definitions list of declared parameters.
          // Might want to change this to a different criteria later on - especially when types are
          // associated to symbols. E.g. find the next component element and check to see whether
          // it is a member of the parameter's type. Then assume that if it is a member, this is the
          // parameter reference being referred to.
          new ParameterReference(scope.parameters.head, path)
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
          scope.resolve(root(ctx), pathComponents(ctx))
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
    val rawScope = annotator.scopes(ctx)
    System.err.println("Scope Chain")
    System.err.println(s"$rawScope")
    var s = rawScope
    while (s.isDefined && s.get.parent.isDefined) {
      s = s.get.parent
      System.err.println(s" ==> ${nameOf(s.get)}")
    }

    val scope = annotator.scopes(ctx).get.asInstanceOf[CollectionMemberScope]
    scope.indexSymbol = Option(new CollectionIndexSymbol("_", ctx.collectionRef.symbol))


  }


}



