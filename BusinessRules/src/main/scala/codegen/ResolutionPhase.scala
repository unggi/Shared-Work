package codegen

import codegen.symbols._
import org.antlr.v4.runtime.tree.ParseTreeWalker
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

import scala.collection.JavaConversions._


//
// Find every model reference and resolve it depending on the enclosing scope
//
class ResolutionPhase(symbolTable: SymbolTable, annotator: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener {


  override def enterValidationRule(ctx: ValidationRuleContext): Unit = {
    val walker = new ParseTreeWalker()
    val resolver = new ValidationRuleResolutionPhase()
    walker.walk(resolver, ctx.constraint())
  }

  class ValidationRuleResolutionPhase() extends BusinessRulesBaseListener {

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
      annotator.symbols.put(ctx, symbol)
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
  }

  override def enterDefinition(ctx: DefinitionContext): Unit = {
    val walker = new ParseTreeWalker()
    val resolver = new DefinitionResolutionPhase()
    walker.walk(resolver, ctx.value)
  }

  class DefinitionResolutionPhase() extends BusinessRulesBaseListener {

    override def enterModelReference(ctx: ModelReferenceContext): Unit = {
      System.err.println(s"Definition Scope ")

      val scopeOpt = annotator.scopes(ctx)
      assume(scopeOpt.isDefined, s"Scope not found for parse tree node: ${nameOf(ctx)}")
      val scope = scopeOpt.get.asInstanceOf[DefinitionScope]
      assume(scope.parent.isDefined, s"Current Scope does not have a parent: ${scope}")

      val base = root(ctx)

      val symbol =
        scope.resolve(base) match {
          case Some(s) => s
          case None =>
            val parameter = scope.parameters.head
            System.err.println(s"******* ${pathComponents(ctx)}")
            new ParameterReference(parameter, pathComponents(ctx))
        }
      System.err.println(s"Definition Resolved ${base} to ${symbol}")
      annotator.symbols.put(ctx, symbol)
    }
  }

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


}