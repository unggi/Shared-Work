package codegen

import codegen.symbols.{CollectionMemberScope, MatchScope, ModelReferenceSymbol, SymbolTableBuilder}
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

  override def enterEveryRule(ctx: ParserRuleContext) = {
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def enterValidationRule(ctx: ValidationRuleContext) = {

    val ref = ctx.context.modelReferenceParameter.modelReference

    assume(ref.dotPath != null || ref.propPath != null)
    val components = ref.path.toList
    val alias =
      ctx.context.modelReferenceParameter.alias match {
        case null => components.mkString(".")
        case node: TerminalNode => unquote(node.getText)
        case token: Token => unquote(token.getText)
      }

    val symbol = new ModelReferenceSymbol(alias, components.toList)

    symbolTable.openScope(new MatchScope(symbolTable.scope, alias, symbol))
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    System.err.println("REFERENCE = " + ctx.reference.getClass)
    assume(ctx.reference != null)
    assume(ctx.reference.symbol == null, "In declaration phase the symbol has not been set.")

    symbolTable.openScope(new CollectionMemberScope(symbolTable.scope))

    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def exitCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    symbolTable.closeScope()

  }

  override def exitDeclaration(ctx: DeclarationContext) = {
    symbolTable.closeScope()
  }

}

