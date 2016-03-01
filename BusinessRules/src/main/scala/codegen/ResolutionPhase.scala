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

    val scope = nodeScopes.get(collectionMemberConstraint).get.asInstanceOf[CollectionMemberScope]

    scope.collectionSymbol = Some(collectionMemberConstraint.reference.symbol)
  }

  def resolve(reference: ModelReferenceContext): Option[Symbol] = {

    val scopeOpt = nodeScopes.get(reference)

    assume(scopeOpt.isDefined, "Scope must have been set during declaration phase: node is " + reference.toStringTree)

    val root = reference.path.get(0).getText

    scopeOpt.get.resolve(root) match {
      case Some(found) =>
        Some(found)
      case None =>
        resolveImplicitParameter(scopeOpt.get) match {
          case Some(parameter) => Some(parameter)
          case None => None
        }
    }
  }

  def find[T <: NestedScope](cls: Class[T], start: NestedScope): Option[T] =
    if (start.getClass == cls)
      Some(start.asInstanceOf[T])
    else if (start.parent.isDefined)
      find[T](cls, start.parent.get)
    else
      None


  // Walk back to a containing scope which is a rule scope and find the model reference there.
  def resolveImplicitParameter(scope: NestedScope): Option[Symbol] =
    find(classOf[CollectionMemberScope], scope) match {
      case Some(collectionScope) => collectionScope.collectionSymbol
      case None =>
        find(classOf[MatchScope], scope) match {
          case Some(matchScope) => Some(matchScope.parameter)
          case None =>
            None
        }
    }
}