package codegen

import codegen.symbols._
import org.antlr.v4.runtime.tree.ParseTreeWalker
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

//
// Find every model reference and resolve it depending on the enclosing scope
//
class ResolutionPhase(symbolTable: SymbolTable, annotator: ParseTreeScopeAnnotations) extends BusinessRulesBaseListener {

  def root(ctx: ModelReferenceContext): String =
    if (ctx.dotPath != null)
      ctx.dotPath.root.getText
    else
      ctx.propPath.root.getText


  class ValidationRuleResolutionPhase() extends BusinessRulesBaseListener {
    override def enterModelReference(ctx: ModelReferenceContext): Unit = {
      System.err.println(s"Validation Rule Scope ")
      annotator.symbols.put(ctx, resolve(ctx).get)
    }

    def resolve(reference: ModelReferenceContext): Option[Symbol] = {

      val scopeOpt = annotator.scopes(reference)

      assume(scopeOpt.isDefined, "Scope must have been set during declaration phase: node is " + reference.toStringTree)

      val base = root(reference)

      scopeOpt.get.resolve(base) match {
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
      annotator.symbols.put(ctx, resolve(ctx).get)
    }

    //
    // In a definition occurrences of a model reference are resolved firstly
    // against the rule parameters. If these don't apply then the reference is ambiguous.
    //
    def resolve(reference: ModelReferenceContext): Option[Symbol] = {

      val scopeOpt = annotator.scopes(reference)

      assume(scopeOpt.isDefined, "Scope must have been set during declaration phase: node is " + reference.toStringTree)

      val scope = scopeOpt.get
      val base = root(reference)

      System.err.println(s"Resolving in a Definition: <$base> in ${scopeOpt.get.descriptor}")

      find(classOf[CollectionMemberScope], scope) match {
        case Some(collectionScope) =>
          collectionScope.collectionSymbol
        case None =>
          find(classOf[DefinitionScope], scope) match {
            case Some(definitionScope) if definitionScope.parameters.size > 0 =>
              definitionScope.resolveInScope(base)
            case None =>
              None
          }
      }
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
        find(classOf[RuleScope], scope) match {
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