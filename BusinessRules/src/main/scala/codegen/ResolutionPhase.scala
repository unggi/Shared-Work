package codegen

import codegen.symbols._
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTreeWalker
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

import scala.collection.JavaConversions._
import scala.collection.immutable


object TreeUtilities {

  def nameOf(any: Any): String = any.getClass.getSimpleName

  def pathComponents(ref: ModelReferenceContext): List[String] =
    if (ref.dotPath != null)
      ref.dotPath.ModelElementName.map(_.getText).toList
    else
      ref.propPath.ModelElementName.map(_.getText).toList


  def find[T <: NestedScope](cls: Class[T], start: NestedScope): Option[T] =
    if (start.getClass == cls)
      Some(start.asInstanceOf[T])
    else if (start.parent.isDefined)
      find[T](cls, start.parent.get)
    else
      None

  def root(ctx: ModelReferenceContext): String =
    if (ctx.dotPath != null)
      ctx.dotPath.root.getText
    else
      ctx.propPath.root.getText

  def find[T <: ParserRuleContext](ctx: ParserRuleContext, cls: Class[T]): immutable.List[T] =
    ctx.getRuleContexts(cls).toList

  def query[T <: ParserRuleContext](ctx: ParserRuleContext)(mapper: (ParserRuleContext) => Option[T]): immutable.List[T] =
    find(ctx, classOf[ParserRuleContext]).flatMap(mapper(_))

  def apply[P <: ParserRuleContext, C <: ParserRuleContext](ctx: P, cls: Class[C])(body: (C) => String): String =
    ctx.getRuleContexts(cls).toList.foldLeft("")(_ + body(_))


  def apply[P <: ParserRuleContext](list: List[P])(body: (P) => String): String =
    list.foldLeft("")(_ + body(_))

}

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

    val symbol =
      scope.resolve(base) match {
        case Some(s) => s
        case None =>
          // Default to the first parameter in the definitions list of declared parameters.
          // Might want to change this to a different criteria later on - especially when types are
          // associated to symbols.
          scope.parameters.head
      }
    System.err.println(s"Definition Resolved <${base}> to <${symbol}>: \nfor node ${ctx.hashCode()}")
    ctx.symbol = symbol
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
        case Some(s) => s
        case None =>
          //
          // Must be an implicit use of the parameter in the current rule scope
          //
          val ruleScope = find(classOf[RuleScope], scope).get
          val parameter = ruleScope.modelParameterSymbol
          new ParameterReference(parameter, pathComponents(ctx))
      }

    System.err.println(s"Rule Resolved ${base} to ${symbol}")
    ctx.symbol = symbol
  }
}

