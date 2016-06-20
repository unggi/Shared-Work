package codegen


import codegen.symbols.{Parameter, _}
import org.antlr.v4.runtime.tree.TerminalNode
import org.antlr.v4.runtime.{ParserRuleContext, Token}
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._


//
// Find every declaration and create a symbol table entry.
//
class DeclarationPhase(symbolTable: SymbolTableBuilder) extends BusinessRulesBaseListener {

  import StringFormatter._

  import scala.collection.JavaConversions._

  //
  // We track the scope which applies for each node by using a Parse Tree property. This are
  // not real Java attributes of a node  - just annotations to the AST stored in a dictionary which maps nodes
  // to scopes.
  //
  val annotator = new ParseTreeScopeAnnotations()

  override def enterEveryRule(ctx: ParserRuleContext) = {
    annotator.scopes.put(ctx, symbolTable.scope)
  }

  override def enterValidationRule(ctx: ValidationRuleContext): Unit = {

    val ref = ctx.context.parameterDeclaration()

    val parameter = tokenToText(ref.alias)

    val symbol = makeParameter(parameter, ref.modelReference)
    val scope = new RuleScope(symbolTable.scope, symbol)
    symbolTable.openScope(scope)
    annotator.scopes.put(ctx, symbolTable.scope)

  }

  override def enterDefinition(ctx: DefinitionContext): Unit = {
    val references = ctx.parameterDeclarations().parameterDeclaration().toList

    val params: List[Parameter] = references.map {
      ref => makeParameter(tokenToText(ref.alias), ref.modelReference)
    }

    val scope = new DefinitionScope(symbolTable.scope, params)

    symbolTable.openScope(scope)
    annotator.scopes.put(ctx, symbolTable.scope)
  }

  override def exitDeclaration(ctx: DeclarationContext) =
    symbolTable.closeScope()

  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    assume(ctx.reference != null)

    val scope = new CollectionMemberScope(symbolTable.scope)
    symbolTable.openScope(scope)
    annotator.scopes.put(ctx, symbolTable.scope)
  }

  override def exitCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit =
    symbolTable.closeScope()

  def pathComponents(ref: ModelReferenceContext): List[String] =
    if (ref.dotPath != null)
      ref.dotPath.ModelElementName.map(_.getText).toList
    else
      ref.propPath.ModelElementName.map(_.getText).toList

  def makeParameter(alias: String, ref: ModelReferenceContext): Parameter = {

    val components: List[String] = pathComponents(ref)
    val base = components.head

    new Parameter(alias, base)
  }

  def tokenToText(token: Token): String = token match {
    case node: TerminalNode => unquote(node.getText)
    case token: Token => unquote(token.getText)
    case unknown =>
      assert(true, "Unhandled token type " + unknown)
      ""
  }

}

