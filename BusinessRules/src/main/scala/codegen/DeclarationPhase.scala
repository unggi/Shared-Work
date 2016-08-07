package codegen


import codegen.model.ModelFactory
import codegen.symbols.{Parameter, _}
import org.antlr.v4.runtime.ParserRuleContext
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._


//
// Find every declaration and create a symbol table entry.
//
class DeclarationPhase(builder: SymbolTableBuilder) extends BusinessRulesBaseListener {

  import StringFormatter._
  import TreeUtilities.tokenToText

  import scala.collection.JavaConversions._

  //
  // We track the scope which applies for each node by using a Parse Tree property. This are
  // not real Java attributes of a node  - just annotations to the AST stored in a dictionary which maps nodes
  // to scopes.
  //
  val annotator = new ParseTreeScopeAnnotations()

  override def enterModelFileReference(ctx: ModelFileReferenceContext): Unit = {
    val modelPath = ctx.DoubleQuotedString().getText
    val model = ModelFactory.create(modelPath)
    builder.symbolTable = new SymbolTable(model)
  }

  override def enterEveryRule(ctx: ParserRuleContext) = {
    annotator.scopes.put(ctx, builder.scope)
  }

  override def enterValidationRule(ctx: ValidationRuleContext): Unit = {

    val decl = ctx.context.parameterDeclaration()

    val parameter = tokenToText(decl.alias)
    val typeName = tokenToText(decl.typeName)

    val symbol = Parameter(parameter, typeName)
    val scope = RuleScope(builder.scope, symbol)
    builder.openScope(scope)
    annotator.scopes.put(ctx, builder.scope)
    decl.symbol = symbol
  }

  override def enterDefinition(ctx: DefinitionContext): Unit = {
    val references = ctx.parameterDeclarations().parameterDeclaration().toList

    val params: List[Parameter] = references.map {
      ref =>
        val param = Parameter(tokenToText(ref.alias), tokenToText(ref.typeName))
        ref.symbol = param
        param
    }

    val scope = DefinitionScope(builder.scope, params)

    builder.openScope(scope)
    annotator.scopes.put(ctx, builder.scope)


    val definitionSymbol = DefinedTermSymbol(unquote(ctx.name.getText), ctx)
    assume(scope.parent.isDefined)
    scope.parent.get.declare(definitionSymbol)
  }

  override def exitDefinition(ctx: DefinitionContext) = builder.closeScope()

  override def exitValidationRule(ctx: ValidationRuleContext) = builder.closeScope()

  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    val scope = new CollectionMemberScope(builder.scope)
    builder.openScope(scope)
    annotator.scopes.put(ctx, scope)
  }

  override def exitCollectionMemberConstraint(ctx: CollectionMemberConstraintContext) = builder.closeScope()
}

