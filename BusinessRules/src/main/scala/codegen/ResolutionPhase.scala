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

  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {
    super.enterCollectionMemberConstraint(ctx)

    val scopeOpt = annotator.scopes(ctx)

    assume(scopeOpt.isDefined, s"Scope not found for parse tree node: ${nameOf(ctx)}")
    assume(scopeOpt.get.isInstanceOf[CollectionMemberScope], "Collection Member Constraing must be linked to CollectionMemberScope")

    val scope = scopeOpt.get.asInstanceOf[CollectionMemberScope]

    assume(scope.parent.isDefined, s"Current scope must have a parent")

    scope.parent match {
      case Some(rule: RuleScope) =>
        scope.collectionSymbol = Some(rule.modelParameterSymbol)
      case Some(definition: DefinitionScope) =>
        scope.collectionSymbol = Some(definition.parameters.head)
      case any =>
    }
    System.err.println(s"Collection Symbol: ${scope.collectionSymbol}")
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
        case None =>
          // Default to the first parameter in the definitions list of declared parameters.
          // Might want to change this to a different criteria later on - especially when types are
          // associated to symbols.
          new ParameterReference(scope.parameters.head, path)
        case otherwise =>
          throw new UnexpectedException(s"Invalid symbol resolution result: $otherwise")
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
          System.err.println(s"rule - $parameter\nContext is ${ctx.getParent.getText}")
          new ParameterReference(parameter, pathComponents(ctx))
        case Some(other) => assert(false); null
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

    scope.parent.get.print(5)

    if (defn.isDefined && defn.get.isInstanceOf[DefinedTermSymbol]) {
    } else {
      assert(false)
    }
  }
}

