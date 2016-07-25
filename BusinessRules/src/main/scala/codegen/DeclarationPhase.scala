package codegen


import codegen.symbols.{Parameter, _}
import org.antlr.v4.runtime.ParserRuleContext
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._


//
// Find every declaration and create a symbol table entry.
//
class DeclarationPhase(symbolTable: SymbolTableBuilder) extends BusinessRulesBaseListener {

  import TreeUtilities.tokenToText
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

    val decl = ctx.context.parameterDeclaration()

    val parameter = tokenToText(decl.alias)
    val typeName = tokenToText(decl.typeName)

    val symbol = new Parameter(parameter, typeName)
    val scope = new RuleScope(symbolTable.scope, symbol)
    symbolTable.openScope(scope)
    annotator.scopes.put(ctx, symbolTable.scope)
    decl.symbol = symbol
  }

  override def enterDefinition(ctx: DefinitionContext): Unit = {
    val references = ctx.parameterDeclarations().parameterDeclaration().toList

    val params: List[Parameter] = references.map {
      ref =>
        val param = new Parameter(tokenToText(ref.alias), tokenToText(ref.typeName))
        ref.symbol = param
        param
    }

    val scope = new DefinitionScope(symbolTable.scope, params)

    symbolTable.openScope(scope)
    annotator.scopes.put(ctx, symbolTable.scope)


    val definitionSymbol = new DefinedTermSymbol(unquote(ctx.name.getText), ctx)
    assume(scope.parent.isDefined)
    scope.parent.get.declare(definitionSymbol)
  }

  override def exitDefinition(ctx: DefinitionContext): Unit =
    symbolTable.closeScope()

  override def exitValidationRule(ctx: ValidationRuleContext) =
    symbolTable.closeScope()

  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    val scope = new CollectionMemberScope(symbolTable.scope)
    symbolTable.openScope(scope)
    annotator.scopes.put(ctx, scope)
  }

  override def exitCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {
    symbolTable.closeScope()
  }

}

