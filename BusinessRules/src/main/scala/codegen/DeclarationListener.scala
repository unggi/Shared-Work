package codegen


import codegen.symbols.{ModelParameterSymbol, ModelReferenceSymbol, _}
import org.antlr.v4.runtime.tree.TerminalNode
import org.antlr.v4.runtime.{ParserRuleContext, Token}
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._


// Use class members to get to all the declarations.
// For each definition and validation rule, create a listener and initiate a walk.
// Each declaration type has different resolution rules.

class DeclarationListener(symbolTable: SymbolTableBuilder) extends BusinessRulesBaseListener {

  import StringFormatter._

  import scala.collection.JavaConversions._

  //
  // We track the scope which applies for each node by using a Parse Tree property. This are
  // not real Java attributes of a node  - just annotations to the AST stored in a dictionary which maps nodes
  // to scopes.
  //
  val annotator = new ParseTreeScopeAnnotations()

  override def enterEveryRule(ctx: ParserRuleContext) =
    annotator.scopes.put(ctx, symbolTable.scope)

  override def enterValidationRule(ctx: ValidationRuleContext): Unit = {

    val ref = ctx.context.modelReferenceParameter

    val parameter = tokenToText(ref.alias)

    val symbol = makeModelParameterSymbol(parameter, ref.modelReference)
    val scope = new RuleScope(symbolTable.scope, symbol.get)
    symbolTable.openScope(scope)
    annotator.scopes.put(ctx, symbolTable.scope)

  }

  override def enterDefinition(ctx: DefinitionContext): Unit = {
    val references = ctx.multipleContextParameter.modelReferenceParameter.toList

    val params =
      for (ref <- references)
        yield makeModelParameterSymbol(tokenToText(ref.alias), ref.modelReference)

    val scope = new DefinitionScope(symbolTable.scope, params.flatten)

    symbolTable.openScope(scope)
    annotator.scopes.put(ctx, symbolTable.scope)
  }

  override def exitDeclaration(ctx: DeclarationContext) =
    symbolTable.closeScope()

  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {

    assume(ctx.reference != null)

    val scope = new CollectionMemberScope(symbolTable.scope)
    val collectionReference = annotator.symbols(ctx.reference)
    assume(collectionReference.isDefined, "Collection symbol has been defined already")
    scope.collectionSymbol = Some(CollectionIndexSymbol("_", collectionReference.get))
    symbolTable.openScope(scope)
    annotator.scopes.put(ctx, symbolTable.scope)
  }

  override def exitCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit =
    symbolTable.closeScope()

  def makeModelParameterSymbol(alias: String, ref: ModelReferenceContext): Option[ModelParameterSymbol] = {

    val components: List[String] = annotator.paths(ref).get
    val base = components.head

    // TODO Validate the path after the head for member references.

    val modelRef = new ModelReferenceSymbol(base, components.tail)

    Some(new ModelParameterSymbol(alias, modelRef))
  }

  def tokenToText(token: Token): String = token match {
    case node: TerminalNode => unquote(node.getText)
    case token: Token => unquote(token.getText)
    case unknown =>
      assert(true, "Unhandled token type " + unknown)
      ""
  }

}

