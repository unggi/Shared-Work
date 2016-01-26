package codegen

import codegen.symbols.{ModelReferenceEntry, SymbolTable}
import org.antlr.v4.runtime.tree.TerminalNodeImpl
import org.antlr.v4.runtime.{CommonTokenFactory, ParserRuleContext, RuleContext}
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser._

import scala.collection.JavaConversions._
import scala.reflect._


class DeclarationPhase(symbolTable: SymbolTable) extends BusinessRulesBaseListener {

  import StringFormatter._

  //
  // We track the scope which applies for each node by using a Parse Tree property. This are
  // not real properties - just annotations to the AST.
  //
  val nodeScopes = new ParseTreeScopeMap()

  var isCollectionMemberConstraint = false

  override def enterEveryRule(ctx: ParserRuleContext) = {
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def enterDeclaration(ctx: DeclarationContext) = {
    nodeScopes.put(ctx, symbolTable.scope)
  }

  override def enterContext(ctx: ContextContext) = {

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
            unquote(ctx.modelReferenceWithAlias.alias.getText)
          else
            components.mkString(".")
        val entry = new ModelReferenceEntry(alias, components)
        symbolTable.declare(alias, entry)
        symbolTable.openContextScope(entry)
        nodeScopes.put(ctx, symbolTable.scope)
    }
  }

  override def exitDeclaration(ctx: DeclarationContext) = {
    super.exitDeclaration(ctx)
    symbolTable.closeContextScope()
  }

  override def enterModelReference(ctx: ModelReferenceContext) = {
    super.enterModelReference(ctx)
    resolve(ctx, ctx)
  }

  def isChildOf[T: ClassTag](start: RuleContext): Boolean = {
    val cls = classTag[T].runtimeClass
    var node = start
    while (node != null && node.getClass != cls)
      node = node.parent

    node != null && node.getClass == cls
  }

  def resolve(node: ParserRuleContext, child: ModelReferenceContext): Unit =
    if (isChildOf[SimpleOrComplexConstraintContext](node) && isChildOf[ConstrainedCollectionMembershipContext](node)) {
      setFirstToken(child, "_")
    } else
      nodeScopes.get(node) match {
        case Some(scope) =>
          val firstModelElement = child.dottedModelPath.ModelElementName(0).getText

          scope.resolveFromContextScope(firstModelElement) match {

            case Some(entry) if entry.name.equals(firstModelElement) =>
            // The reference is fully qualified already (i.e. alias is the starting point of the dotted path).
            // We don't need to do anything to modify the path - just check it against the model.
            case Some(entry) =>

              // Must be the alias name in which the first model element is a member
              // We will insert a token for this name into the Model Reference at this point.
              setFirstToken(child, entry.name)
            case None =>
          }

        case None =>
          // Exception: Unexpected Missing Scope Type
          println("Unexpected Missing Scope type here.")
      }

  def setFirstToken(child: ModelReferenceContext, newTokenText: String): Unit = {

    val dotPath = child.dottedModelPath()
    val token = CommonTokenFactory.DEFAULT.create(0, newTokenText)
    val terminal = new TerminalNodeImpl(token)
    terminal.parent = dotPath
    dotPath.children.insert(0, terminal)

  }
}

