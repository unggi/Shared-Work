package codegen

import codegen.symbols._
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

/**
 * Walk through the parse tree and resolve symbols to declarations of symbols collected earlier
 */
class ResolutionPhase(symbolTable: SymbolTable, nodeScopes: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener {

  // IsKindOfPredicate
  override def enterIsKindOfPredicate(ctx: IsKindOfPredicateContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference).get

  override def enterModelReferenceTerm(ctx: ModelReferenceTermContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get

  override def enterModelReferenceIdentifier(ctx: ModelReferenceIdentifierContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get

  override def enterSumOfExpression(ctx: SumOfExpressionContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get

  override def enterNumberOfExpression(ctx: NumberOfExpressionContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get

  override def enterCollectionIndex(ctx: CollectionIndexContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get

  override def enterCastExpression(ctx: CastExpressionContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get

  override def enterSelectionExpression(ctx: SelectionExpressionContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get

  override def enterConstrainedCollectionMembership(ctx: ConstrainedCollectionMembershipContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get


  override def enterNotExistsStatement(ctx: NotExistsStatementContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get

  override def enterForallStatement(ctx: ForallStatementContext): Unit =
    ctx.modelReference.symbol = resolve(ctx.modelReference()).get


  override def enterCollectionMemberConstraint(collectionMemberConstraint: CollectionMemberConstraintContext): Unit = {

    collectionMemberConstraint.reference.symbol = resolve(collectionMemberConstraint.reference).get

    val scope = nodeScopes.get(collectionMemberConstraint).get.asInstanceOf[CollectionMemberScope]

    scope.parameterName = "_"
    scope.collectionSymbol = collectionMemberConstraint.reference.symbol

  }

  def resolve(reference: ModelReferenceContext): Option[Symbol] = {
    val scopeOpt = nodeScopes.get(reference)

    assume(scopeOpt.isDefined, "Scope must have been set during declaration phase: node is " + reference.toStringTree)

    val scope = scopeOpt.get

    val symbol =
      scope.resolve(reference.path.get(0).getText) match {
        case Some(found) =>
          //
          // Model Reference path starts with some previously defined variable or context parameter
          //
          Some(found)
        case None =>
          //
          // Model Reference path starts with an implicit context parameter. We need to find it and set this as the
          // symbol for the this ModelReference context.
          //
          resolveImplicitParameter(scope)
      }
    System.err.println(s"RULE = ${reference.parent.getClass.getSimpleName} SYMBOL ${symbol.get}")

    symbol
  }

  // Walk back to a containing scope which is a rule scope and find the model reference there.
  def resolveImplicitParameter(scope: NestedScope): Option[Symbol] =
    scope match {
      case ruleScope: MatchScope => Some(ruleScope.modelParameterType)
      case other: NestedScope if other.parent.isDefined => resolveImplicitParameter(other.parent.get)
      case unknown => None
    }
}
