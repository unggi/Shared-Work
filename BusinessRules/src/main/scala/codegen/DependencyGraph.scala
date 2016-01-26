package codegen

import java.io.PrintWriter

import org.stringtemplate.v4.AutoIndentWriter

import scala.collection.immutable.HashSet
import scala.collection.mutable
import scala.collection.mutable.ListBuffer


object DependencyGraph {

  trait Node {
    val successors = new ListBuffer[Node]()

    def name: String = ""

    def isSuccessor(n: Node): Boolean = successors.contains(n.name)

  }


  class InputNode(override val name: String) extends Node

  class RuleNode(override val name: String) extends Node

  class VariableNode(override val name: String) extends Node

  class OutputNode(override val name: String) extends Node

}

class DependencyGraph {

  /**
   *
   * A simple dependency graph representation to derive rule calculation paths.
   * This is not a generalized graph. It assumes:
   * <li> There are input nodes which are model references into the input document.
   * <li> A rule can produce variables and set them to values which may be inputs
   * to other rules.
   * <li> There are rule nodes which take in inputs of either model references or variables.
   * <li> The graph begins with input nodes which are model references and these have no predecessors.
   * <li> The graph "fires" or propagates from these nodes to the furthest most rule which has no outputs
   * or fails to validate.
   *
   **/

  import DependencyGraph._

  val inputs = new mutable.HashMap[String, Node]()
  val nodes = new mutable.HashMap[String, Node]()


  def addInputs(args: InputNode*): Unit = {
    for (input <- args) {
      inputs.put(input.name, input)
      nodes.put(input.name, input)
    }
  }

  def addRules(rules: RuleNode*) =
    for (rule <- rules)
      nodes.put(rule.name, rule)

  def addVariables(variables: VariableNode*) =
    for (variable <- variables)
      nodes.put(variable.name, variable)


  def addOutputs(outputs: OutputNode*) =
    for (output <- outputs)
      nodes.put(output.name, output)

  def addDependency(from: Node, to: Node) = from.successors.append(to)

  def addDependency(link: Tuple2[Node, Node]) = link._1.successors.append(link._2)

  def isDependency(from: Node, to: Node): Boolean = from.successors.contains(to.name)

  def isDependency(link: Tuple2[Node, Node]) = link._1.successors.contains(link._2)

  def findNode(name: String): Option[Node] = nodes.get(name)

  val indent = "--"

  def render(pw: AutoIndentWriter): Unit = {
    val visited = new HashSet[Node]()
    for (node <- inputs.values) {
      pw.write(s"Input[${node.name}]\n")
      pw.pushIndentation(indent)
      node.successors.foreach(render(_, pw, visited + node))
      pw.popIndentation
    }
  }

  def render(node: Node, pw: AutoIndentWriter, visited: Set[Node]): Unit = {
    pw.write("> ")
    node match {
      case rule: RuleNode => pw.write(s"Rule[${node.name}]\n")
      case input: InputNode => pw.write(s"Input[${node.name}]\n")
      case variable: VariableNode => pw.write(s"Variable[${node.name}]\n")
      case output: OutputNode => pw.write(s"Output[${node.name}, ${}}]\n")
      case unknown =>
        System.err.println(s"Unknown Graph Node type: ${unknown.getClass}")
    }
    pw.pushIndentation(indent)
    node.successors.foreach(render(_, pw, visited + node))
    pw.popIndentation
  }
}
