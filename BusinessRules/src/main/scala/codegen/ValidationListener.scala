package codegen

import org.antlr.v4.runtime.tree.TerminalNode
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser.{ContextContext, DeclarationContext, DottedModelPathContext}
import rules.runtime.Reference

import scala.collection.JavaConversions._
import scala.collection.mutable

abstract class SymbolValue() {}

case class ModelReference(referencePath: String, components: List[TerminalNode]) extends SymbolValue

class SymbolTable {


  var map = new mutable.HashMap[String, SymbolValue]()

  def put(key: String, entry: SymbolValue): Option[SymbolValue] = map.put(key, entry)

  def lookup(key: String): Option[SymbolValue] = map.get(key)
}

/**
 *
 * A simple dependency graph representation to derive rule calculation paths.
 * This is not a generalized graph. It assumes:
 * <li> There are input nodes which are model references into the input document.
 * <li> A rule can produce variables and set them to values which may be inputs
 * to other rules.
 * <li> There are rule nodes which take in inputs of either model references or variables.
 * <li> The graph begins with input nodes which are model references and these have no predecessors.
 * <li> The graph "fires" or propogates from these nodes to the furthest most rule which has no outputs
 * or fails to validate.
 *
 **/

class DependencyGraph {

  trait Node {

  }

  case class ReferenceNode() extends Node {

  }

  case class RuleNode() extends Node {

  }

  case class VariableNode() extends Node {

  }

  def addModelReference(reference: Reference): Unit = {

  }

  def addDefinition(): Unit = {

  }

}

class ValidationListener(symbolTable: SymbolTable) extends BusinessRulesBaseListener {

  val scopeBindings = new mutable.Stack[String]()

  override def exitContext(ctx: ContextContext) = {
    super.exitContext(ctx)
    ctx.modelReferenceWithAlias.modelReference.modelPath.dottedModelPath() match {
      case null =>
      case path: DottedModelPathContext =>
        scopeBindings.push(addSymbol(path))

    }
  }

  override def exitDeclaration(ctx: DeclarationContext) = {
    super.exitDeclaration(ctx)
    scopeBindings.clear()
  }

  def addSymbol(ctx: DottedModelPathContext): String = {
    val elements = ctx.ModelElementName().toList
    val key = elements.map(_.getText).mkString(".")
    symbolTable.put(key, ModelReference(key, elements)) match {
      case Some(previous: ModelReference) => println("Duplicate Seen - OK")
      case Some(other) => println("Error - duplicate key doesn't have same entry type")
      case None =>
    }

    println(s"Add Symbol <$key> and <$elements>")
    key
  }

  override def exitDottedModelPath(ctx: DottedModelPathContext) = {
    super.exitDottedModelPath(ctx)

    addSymbol(ctx)
  }
}
