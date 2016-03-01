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

    val alias: Option[String] =
      ctx.context.modelReferenceParameter.alias match {
        case null => None
        case node: TerminalNode => Some(unquote(node.getText))
        case token: Token => Some(unquote(token.getText))
      }

    makeModelReferenceSymbol(alias, ref)

    isInsideValidationRuleDecl = true
  }

  override def exitContext(ctx: ContextContext): Unit = {
    isInsideValidationRuleDecl = false
  }

  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    assume(ctx.reference != null)
    assume(ctx.reference.symbol == null, "In declaration phase the collection symbol has not been set - we only know the scope. " + ctx.getText)

    val scope = new CollectionMemberScope((symbolTable.scope))
    symbolTable.openScope(scope)
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def exitCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit =
    symbolTable.closeScope()

  override def exitDeclaration(ctx: DeclarationContext) =
    symbolTable.closeScope()

  override def enterModelReference(ctx: ModelReferenceContext): Unit = {
    System.err.println("Entering MODEL REFERENCE => " + ctx.path.mkString("."))

    makeModelReferenceSymbol(None, ctx)
  }

  def makeModelReferenceSymbol(aliasOpt: Option[String], ref: ModelReferenceContext): Unit =
    if (!isInsideValidationRuleDecl) {

      assume(ref.dotPath != null || ref.propPath != null)
      val components = ref.path.map(p => p.getText).toList

      val scope = symbolTable.scope
      aliasOpt match {
        case Some(alias) =>
          val base = components.head
          scope.resolve(base) match {
            case Some(baseSymbol: ModelReferenceSymbol) => new ModelParameterSymbol(alias, baseSymbol)
            case Some(baseSymbol) => assert(false, "The base of a component path does not resolved to a Model Parameter Symbol")
            case None =>
              val param = scope.resolveImplicitParameter()
              assume(param.isDefined, "Implicit Parameter not found")
              new ModelParameterSymbol(param.get.name,)


          }
        case None =>

      }

      nodeScopes.put(ref, symbolTable.scope)
      symbol.scope = Some(symbolTable.scope)
    }


}

