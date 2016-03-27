package codegen

import codegen.symbols._
import org.antlr.v4.runtime.tree.ParseTreeWalker
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

//
// Find every model reference and resolve it depending on the enclosing scope
//
class ResolutionPhase(symbolTable: SymbolTable, nodeScopes: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener {

  class ValidationRuleResolutionPhase() extends BusinessRulesBaseListener {
    override def enterModelReference(ctx: ModelReferenceContext): Unit = {
      System.err.println(s"Validation Rule Scope ")
      ctx.symbol = resolve(ctx).get
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
  }

  override def enterValidationRule(ctx: ValidationRuleContext): Unit = {
    val walker = new ParseTreeWalker()
    val resolver = new ValidationRuleResolutionPhase()
    walker.walk(resolver, ctx)
  }

  class DefinitionResolutionPhase() extends BusinessRulesBaseListener {

    override def enterModelReference(ctx: ModelReferenceContext): Unit = {
      System.err.println(s"Definition Scope ")
      ctx.symbol = resolve(ctx).get
    }

  }

  override def enterDefinition(ctx: DefinitionContext): Unit = {
    val walker = new ParseTreeWalker()
    val resolver = new DefinitionResolutionPhase()
    walker.walk(resolver, ctx)
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
      case Some(collectionScope) =>
        collectionScope.collectionSymbol
      case None =>
        find(classOf[MatchScope], scope) match {
          case Some(matchScope) =>
            Some(matchScope.modelParameterSymbol)
          case None =>
            find(classOf[DefinitionScope], scope) match {
              case Some(definitionScope) if definitionScope.parameters.size > 0 =>
                definitionScope.parameters.headOption
              case None =>
                None
            }
        }
    }
}