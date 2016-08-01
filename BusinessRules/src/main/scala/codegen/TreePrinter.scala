package codegen

import java.io.PrintWriter

import codegen.symbols.{CollectionMemberScope, DefinitionScope, NestedScope, RuleScope}
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNode
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser.ModelReferenceContext

class TreePrinter(var indent: Int, annotator: ParseTreeScopeAnnotations, pw: PrintWriter) extends BusinessRulesBaseListener {

  var lastScope: Option[NestedScope] = None

  override def enterEveryRule(child: ParserRuleContext): Unit = {

    if (lastScope.isEmpty || lastScope != annotator.scopes(child)) {
      val space = ">>>>> "
      annotator.scopes(child) match {
        case Some(scope: CollectionMemberScope) =>
          pw.println(s"${space}CollectionScope ${scope}")
        case Some(rule: RuleScope) =>
          pw.println(s"${space}RuleScope ${rule.modelParameterSymbol}")
        case Some(definition: DefinitionScope) =>
          pw.println(s"${space}DefinitionScope ${definition.parameters.mkString(", ")}")
        case None =>
        case any =>
      }
      lastScope = annotator.scopes(child)
    }

    val childClassName = child.getClass.getSimpleName.stripSuffix("Context")
    val childFields = child.getClass.getDeclaredFields

    val ch = if (child.getChildCount > 0) "-" else " "
    pw.print((ch * indent) + "> ")

    if (child.getParent != null) {

      // Find the child's name in the parent node - if it exists
      val parent = child.getParent
      val parentClass = parent.getClass

      val parentNameField = parentClass.getDeclaredFields.find {
        fld =>
          fld.get(parent) match {
            case null => false
            case node =>
              node.equals(child)
          }
      }

      if (parentNameField.isDefined)
        pw.print(parentNameField.get.getName + " = ")
    }

    pw.print(childClassName)
    if (childFields.nonEmpty)
      pw.print(" [" + childFields.map(_.getName).mkString(" | ") + "]")

    child match {
      case ref : ModelReferenceContext => pw.print(s": ${ref.symbol} ")
      case otherwise =>
    }

    pw.println(" " * 10 + "." * 5 + lastScope)

    indent = indent + 2
  }

  override def exitEveryRule(child: ParserRuleContext): Unit = {
    indent = indent - 2
  }

  override def visitTerminal(node: TerminalNode): Unit = {
    pw.println(" " * indent + "| " + node.getText)
  }

}
