package codegen

import java.rmi.UnexpectedException

import codegen.symbols._
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTreeWalker
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

import scala.collection.JavaConversions._
import scala.collection.immutable

//
// Find every model reference and resolve it depending on wheterh it is in a definition or a validation rule.
//
//
class ResolutionPhase(symbolTable: SymbolTable, annotator: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener {

  import TreeUtilities._

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

  import TreeUtilities._

  override def enterModelReference(ctx: ModelReferenceContext): Unit = {
    System.err.println(s"Definition Scope: parent is <${nameOf(ctx.getParent)}>")

    val scopeOpt = annotator.scopes(ctx)
    assume(scopeOpt.isDefined, s"Scope not found for parse tree node: ${nameOf(ctx)}")

    val scope = scopeOpt.get.asInstanceOf[DefinitionScope]
    assume(scope.parent.isDefined, s"Current Scope does not have a parent: ${scope}")

    val base = root(ctx)
    val path = pathComponents(ctx)

    val symbol =
      scope.resolve(base) match {
        case Some(s: Parameter) => new ParameterReference(s, path)
        case Some(other) => require(other.isInstanceOf[Parameter]); null
        case None =>
          // Default to the first parameter in the definitions list of declared parameters.
          // Might want to change this to a different criteria later on - especially when types are
          // associated to symbols.
          new ParameterReference(scope.parameters.head, path)
      }

    ctx.symbol = symbol
    System.err.println(s"Definition Resolved <${base}> to <${symbol}>: \nfor node ${ctx.hashCode()}")

  }
}

class ValidationRuleResolutionPhase(annotator: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener {

  import TreeUtilities._

  override def enterModelReference(ctx: ModelReferenceContext): Unit = {

    val scopeOpt = annotator.scopes(ctx)

    assume(scopeOpt.isDefined, s"Scope not found for parse tree node: ${nameOf(ctx)}")

    val scope = scopeOpt.get

    assume(scope.parent.isDefined, s"Current scope must have a parent")

    val base = root(ctx)

    val symbol =
      scope.resolve(base) match {
        case Some(parameter: Parameter) =>
          new ParameterReference(parameter, pathComponents(ctx))
        case Some(any) => require(false, s"Symbol was resolved to $any but should have been a Parameer"); null
        case None =>
          //
          // Must be an implicit use of the parameter in the current rule scope
          //
          val ruleScope = find(classOf[RuleScope], scope).get
          val parameter = ruleScope.modelParameterSymbol
          new ParameterReference(parameter, parameter.name :: pathComponents(ctx))
      }

    System.err.println(s"Rule Resolved ${base} to ${symbol} ==> ${symbol.components.head}")
    ctx.symbol = symbol
  }

  override def enterDefinedTermReferenceTerm(ctx: DefinedTermReferenceTermContext): Unit = {

    val scopeOpt = annotator.scopes(ctx)

    assume(scopeOpt.isDefined, s"Scope not found for parse tree node: ${nameOf(ctx)}")

    val scope = scopeOpt.get

    assume(scope.parent.isDefined, s"Current scope must have a parent")

    val name = ctx.frag.getText.stripPrefix("<<").stripSuffix(">>")

    val defn = scope.resolve(name)


    System.err.println(s"The definition [${name}]  is : " + defn)

//    scope.parent.get.print(5)

    // TODO - Insert the defined term here ????
  }

  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    val collection = ctx.reference.symbol.asInstanceOf[ParameterReference]
    val constraint = ctx
    val scope = annotator.scopes(constraint).get.asInstanceOf[CollectionMemberScope]

    scope.collectionRef = Some(collection)
  }
}

