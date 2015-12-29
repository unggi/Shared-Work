package codegen

import codegen.symbols.{ModelReferenceEntry, NestedScope, SymbolTable}
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNodeImpl}
import org.antlr.v4.runtime.{CommonTokenFactory, ParserRuleContext}
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

import scala.collection.JavaConversions._
import scala.collection.mutable

class ParseTreeScopeMap() {
  var annotations = new mutable.HashMap[ParseTree, NestedScope]()

  def get(node: ParseTree): Option[NestedScope] = annotations.get(node)

  def put(node: ParseTree, value: NestedScope): Unit = annotations.put(node, value)

  def removeFrom(node: ParseTree): Option[NestedScope] = annotations.remove(node)
}

class ValidationListener(val symbolTable: SymbolTable) extends BusinessRulesBaseListener {

  //
  // We track the scope which applies for each node by using a Parse Tree property. This are
  // not real properties - just annotations to the AST.
  //
  val nodeScopes = new ParseTreeScopeMap()

  override def exitEveryRule(ctx: ParserRuleContext) = {
    super.enterEveryRule(ctx)
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def enterDeclaration(ctx: DeclarationContext) = {
    super.enterDeclaration(ctx)
    symbolTable.openContextScope()
    nodeScopes.put(ctx, symbolTable.scope)

    println("Entering Declaration " + ctx.hashCode())
  }

  override def exitContext(ctx: ContextContext) = {
    super.exitContext(ctx)
    //
    // A context is a single clause in the validation rule which defines aliases
    // that can be referenced explicitly or implicitly in the rule's constraint clause.
    //
    ctx.modelReferenceWithAlias.modelReference.dottedModelPath() match {
      case null =>
      case path: DottedModelPathContext =>
        val components: List[String] = path.ModelElementName.map(_.getText).toList
        val alias =
          if (ctx.modelReferenceWithAlias.alias != null)
            ctx.modelReferenceWithAlias.alias.getText.stripSuffix("\"").stripPrefix("\"")
          else
            components.mkString(".")
        symbolTable.declare(alias, new ModelReferenceEntry(alias, components))
    }
  }

  override def exitDeclaration(ctx: DeclarationContext) = {
    super.exitDeclaration(ctx)
    symbolTable.closeContextScope()
    println("Exiting Declaration " + ctx.hashCode())
  }

  override def enterModelReferenceIdentifier(ctx: ModelReferenceIdentifierContext) = {
    super.enterModelReferenceIdentifier(ctx)
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def exitModelReferenceIdentifier(ctx: ModelReferenceIdentifierContext) = {
    super.exitModelReferenceIdentifier(ctx)

    resolveFromNearestContext(ctx, ctx.modelReference)
  }


  override def enterNumberOfExpression(ctx: NumberOfExpressionContext) = {
    super.enterNumberOfExpression(ctx)
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def exitNumberOfExpression(ctx: NumberOfExpressionContext) = {
    super.exitNumberOfExpression(ctx)
    resolveFromNearestContext(ctx, ctx.modelReference())
  }
                                                   '

  override def enterModelReferenceExists(ctx: ModelReferenceExistsContext) = {
    super.enterModelReferenceExists(ctx)
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def exitModelReferenceExists(ctx: ModelReferenceExistsContext) = {
    super.exitModelReferenceExists(ctx)
    resolveFromNearestContext(ctx,ctx.modelReference)
  }

  def resolveFromNearestContext[N <: ParserRuleContext](node: N, child: ModelReferenceContext): Unit = {
    nodeScopes.get(node) match {
      case Some(scope) =>
        val firstModelElement = child.dottedModelPath.ModelElementName(0).getText

        scope.resolveInContextScope(firstModelElement) match {

          case Some(entry) if entry.name.equals(firstModelElement) =>
          // The reference is fully qualified already (i.e. alias is the starting point of the dotted path).
          // We don't need to do anything to modify the path - just check it against the model.

          case Some(entry) =>
            // Must be the alias name in which the first model element is a member
            // We will insert a token for this name into the Model Reference at this point.
            val dotPath = child.dottedModelPath()
            val token = CommonTokenFactory.DEFAULT.create(0, entry.name)
            val terminal = new TerminalNodeImpl(token)
            terminal.parent = dotPath
            dotPath.children.insert(0, terminal)

          case None =>
            println("Some terrible error has happened here ")

        }

      case None =>

      // Try the alias anyway.
      // Later check this against the data model
      //
    }

  }

}
