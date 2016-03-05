package codegen

import codegen.symbols._
import org.antlr.v4.runtime.tree.TerminalNode
import org.antlr.v4.runtime.{ParserRuleContext, Token}
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._


class DeclarationPhase(symbolTable: SymbolTableBuilder) extends BusinessRulesBaseListener {

  import StringFormatter._

  import scala.collection.JavaConversions._

  //
  // We track the scope which applies for each node by using a Parse Tree property. This are
  // not real Java attributes of a node  - just annotations to the AST stored in a dictionary which maps nodes
  // to scopes.
  //
  val nodeScopes = new ParseTreeScopeAnnotations()

  var isCollectionMemberConstraint = false

  override def enterEveryRule(ctx: ParserRuleContext) =
    nodeScopes.put(ctx, symbolTable.scope)

  var isInsideValidationRuleDecl = false

  override def enterValidationRule(ctx: ValidationRuleContext): Unit = {

    val ref = ctx.context.modelReferenceParameter.modelReference

    val symbolOpt: Option[ModelParameterSymbol] =
      ctx.context.modelReferenceParameter.alias match {
        case node: TerminalNode => makeModelParameterSymbol(unquote(node.getText), ref)
        case token: Token => makeModelParameterSymbol(unquote(token.getText), ref)
        case unknown =>
          assert(true, "Unhandled token type " + unknown)
          None
      }

    val scope = new MatchScope(symbolTable.scope, symbolOpt.get)
    symbolTable.openScope(scope)
    nodeScopes.put(ctx, symbolTable.scope)

    isInsideValidationRuleDecl = true
  }

  override def exitContext(ctx: ContextContext): Unit = {
    isInsideValidationRuleDecl = false
  }

  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    assume(ctx.reference != null)

    val scope = new CollectionMemberScope(symbolTable.scope)
    scope.collectionSymbol = Some(CollectionIndexSymbol("_", ctx.reference.symbol))
    symbolTable.openScope(scope)
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def exitCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit =
    symbolTable.closeScope()

  override def exitDeclaration(ctx: DeclarationContext) =
    symbolTable.closeScope()

  override def enterModelReference(ctx: ModelReferenceContext): Unit = {
    System.err.println("Entering MODEL REFERENCE => " + ctx.path.mkString("."))
    makeModelReferenceSymbol(ctx)
  }

  def makeModelReferenceSymbol(ref: ModelReferenceContext): Option[Symbol] =
    if (!isInsideValidationRuleDecl) {
      var components: List[String] = ref.path.map(p => p.getText).toList
      val scope = symbolTable.scope
      val base = components.head
      scope.resolve(base) match {
        case Some(symbol) =>
        case None =>
          val param = scope.resolveImplicitParameter()
          assume(param.isDefined, "Must be able to find an implict parameter.")
          components = param.get.name :: components
      }
      ref.symbol = ModelReferenceSymbol(components.mkString("."), components)
      Some(ref.symbol)
    }
    else
      None

  def makeModelParameterSymbol(alias: String, ref: ModelReferenceContext): Option[ModelParameterSymbol] =
    if (!isInsideValidationRuleDecl) {
      //
      // Create a Model Reference Symbol
      //
      // Context always has an alias
      //
      var components: List[String] = ref.path.map(p => p.getText).toList
      val scope = symbolTable.scope
      val base = components.head

      val modelRef = new ModelReferenceSymbol(base, components)
      Some(new ModelParameterSymbol(alias, modelRef))

    } else
      None


}

