package codegen

import codegen.symbols._
import rules.BusinessRulesBaseListener


class DeclarationPhase(symbolTable: SymbolTableBuilder) extends BusinessRulesBaseListener {

  //  import StringFormatter._
  //
  //  import scala.collection.JavaConversions._
  //
  //  //
  //  // We track the scope which applies for each node by using a Parse Tree property. This are
  //  // not real Java attributes of a node  - just annotations to the AST stored in a dictionary which maps nodes
  //  // to scopes.
  //  //
  //  val nodeScopes = new ParseTreeScopeAnnotations()
  //
  //  var isCollectionMemberConstraint = false
  //
  //  override def enterEveryRule(ctx: ParserRuleContext) =
  //    nodeScopes.put(ctx, symbolTable.scope)
  //
  //  var isInsideValidationRuleDecl = false
  //
  //  override def enterValidationRule(ctx: ValidationRuleContext): Unit = {
  //
  //    val ref = ctx.context.modelReferenceParameter
  //
  //    val parameter = tokenToText(ref.alias)
  //
  //    System.err.println(s"Reference path is ${ctx.context.modelReferenceParameter.alias}")
  //    val symbol = makeModelParameterSymbol(parameter, ref.modelReference)
  //    val scope = new RuleScope(symbolTable.scope, symbol.get)
  //    symbolTable.openScope(scope)
  //    nodeScopes.put(ctx, symbolTable.scope)
  //
  //    symbolTable.symbolTable.print
  //
  //    isInsideValidationRuleDecl = true
  //  }
  //
  //  override def exitContext(ctx: ContextContext): Unit = {
  //    isInsideValidationRuleDecl = false
  //  }
  //
  //  override def enterDefinition(ctx: DefinitionContext): Unit = {
  //    val references = ctx.multipleContextParameter.modelReferenceParameter.toList
  //
  //    val params =
  //      for (ref <- references)
  //        yield makeModelParameterSymbol(tokenToText(ref.alias), ref.modelReference)
  //
  //    val scope = new DefinitionScope(symbolTable.scope, params.flatten)
  //
  //    symbolTable.openScope(scope)
  //    nodeScopes.put(ctx, symbolTable.scope)
  //
  //    isInsideValidationRuleDecl = true
  //  }
  //
  //  override def exitMultipleContextParameter(ctx: MultipleContextParameterContext): Unit = {
  //    isInsideValidationRuleDecl = false
  //  }
  //
  //  def tokenToText(token: Token): String = {
  //
  //    token match {
  //      case node: TerminalNode => unquote(node.getText)
  //      case token: Token => unquote(token.getText)
  //      case unknown =>
  //        assert(true, "Unhandled token type " + unknown)
  //        ""
  //    }
  //  }
  //
  //
  //  override def enterCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit = {
  //
  //    assume(ctx.reference != null)
  //
  //    val scope = new CollectionMemberScope(symbolTable.scope)
  //    scope.collectionSymbol = Some(CollectionIndexSymbol("_", ctx.reference.symbol))
  //    symbolTable.openScope(scope)
  //    nodeScopes.put(ctx, symbolTable.scope)
  //  }
  //
  //  override def exitCollectionMemberConstraint(ctx: CollectionMemberConstraintContext): Unit =
  //    symbolTable.closeScope()
  //
  //  override def exitDeclaration(ctx: DeclarationContext) =
  //    symbolTable.closeScope()
  //
  //  override def enterModelReference(ctx: ModelReferenceContext): Unit = {
  //    System.err.println("Entering MODEL REFERENCE => " + ctx.path.mkString("."))
  //    makeModelReferenceSymbol(ctx)
  //  }
  //
  //  def makeModelReferenceSymbol(ref: ModelReferenceContext): Option[Symbol] =
  //    if (!isInsideValidationRuleDecl) {
  //      var components: List[String] = ref.path.map(p => p.getText).toList
  //      val scope = symbolTable.scope
  //      val base = components.head
  //      scope.resolve(base) match {
  //        case Some(symbol) =>
  //        case None =>
  //          val param = scope.resolveImplicitParameter()
  //          assume(param.isDefined, s"Cannot resolve an implicit object for $base")
  //          components = param.get.name :: components
  //      }
  //      ref.symbol = ModelReferenceSymbol(components.mkString("."), components)
  //      Some(ref.symbol)
  //    }
  //    else
  //      None
  //
  //  def makeModelParameterSymbol(alias: String, ref: ModelReferenceContext): Option[ModelParameterSymbol] =
  //    if (!isInsideValidationRuleDecl) {
  //      System.err.println(s"alias = $alias ref = ${ref.getText}")
  //      val components: List[String] = ref.path.map(p => p.getText).toList
  //      val scope = symbolTable.scope
  //      val base = components.head
  //
  //      val modelRef = new ModelReferenceSymbol(base, components)
  //
  //      System.err.println(s"modelRef = ${modelRef}")
  //      Some(new ModelParameterSymbol(alias, modelRef))
  //
  //    } else
  //      None
  //

}

