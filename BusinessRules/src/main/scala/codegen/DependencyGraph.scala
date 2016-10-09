package codegen

import java.io.PrintWriter

import org.stringtemplate.v4.AutoIndentWriter

import scala.collection.immutable.HashSet
import scala.collection.mutable

object DependencyGraph {


  trait Node {
    def name: String = ""
  }

  trait Successors {

    self: Node =>

    val successors = mutable.Map[String, Node]()

    def isSuccessor(n: Node): Boolean = successors.contains(n.name)

    def addSuccessor(to: Node): Successors = {
      successors.put(to.name, to)
      self
    }

    def ->(to: Node): Node = {
      addSuccessor(to)
      to
    }

  }

  trait Predecessors {
    self: Node =>

    val predecessors = mutable.Map[String, Node]()

    def isPredecessor(n: Node): Boolean = predecessors.contains(n.name)

    def addPredecessor(from: Node): Predecessors = {
      predecessors.put(from.name, from)
      self
    }
  }

  trait BiDirectionalNode extends Node with Predecessors with Successors {
    self: Node =>

    override def addSuccessor(to: Node): Successors = {
      if (!self.name.equals(to.name)) {
        super.addSuccessor(to)
        to match {
          case pre: Predecessors =>
            pre.addPredecessor(self)
        }
      }
      self
    }

    override def addPredecessor(from: Node): Predecessors = {
      if (!self.name.equals(from.name)) {
        super.addPredecessor(from)
        from match {
          case succ: Successors =>
            succ.addSuccessor(self)
        }
      }
      this
    }
  }

  class InputNode(override val name: String) extends Node with Successors

  class RuleNode(override val name: String) extends Node with Predecessors

  class VariableNode(override val name: String) extends BiDirectionalNode

  class FactNode(override val name: String) extends BiDirectionalNode

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

  val inputs = mutable.Map[String, InputNode]()
  val nodes = mutable.Map[String, Node]()

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

  def addFacts(facts: FactNode*) =
    for (fact <- facts)
      nodes.put(fact.name, fact)

  def addDependency(from: Successors, to: Node) = from.addSuccessor(to)


  def findNode(name: String): Option[Node] = nodes.get(name)

  val indent = "--"

  def render(pw: AutoIndentWriter): Unit = {
    val visited = new HashSet[Node]()
    val sorted = nodes.values.toArray.sortBy(_.name)
    for (node <- sorted) {
      pw.write(s"Input[${node.name}]\n")
      pw.pushIndentation(indent)
      node match {
        case succ: Successors =>
          succ.successors.values.foreach(render(_, pw, visited + node))
        case _ =>
      }
      pw.popIndentation
    }
  }

  def render(node: Node, pw: AutoIndentWriter, visited: Set[Node]): Unit = {
    pw.write("> ")
    node match {
      case rule: RuleNode => pw.write(s"Rule[${rule.name}]\n")
      case input: InputNode => pw.write(s"Input[${input.name}]\n")
      case variable: VariableNode => pw.write(s"Variable[${variable.name}]\n")
      case fact: FactNode => pw.write(s"Fact[${fact.name}]\n")
      case unknown =>
        System.err.println(s"Unknown Graph Node type: ${unknown.getClass}")
    }
    pw.pushIndentation(indent)
    node match {
      case succ: Successors =>
        succ.successors.values.foreach(render(_, pw, visited + node))
      case x =>
    }
    pw.popIndentation
  }

  def toUml(pw: PrintWriter): Unit = {
    pw.println("@startuml")
    val sorted = nodes.values.toArray.sortBy(_.name)
    for (node <- sorted)
      node match {
        case rule: RuleNode =>
          pw.println(s"""class ${rule.name} as "${simpleName(rule)}[${rule.name}]" """)
        case input: InputNode =>
          pw.println(s"""class ${input.name} as "${simpleName(input)}[${input.name}]" """)
        case variable: VariableNode =>
          pw.println(s"""class ${variable.name} as "${simpleName(variable)}[${variable.name}]" """)
        case fact: FactNode =>
          pw.println(s"""class ${fact.name} as "${simpleName(fact)}[${fact.name}]" """)
        case unknown =>
          System.err.println(s"Unknown Graph Node type: ${unknown.getClass}")
      }
    for (node <- sorted)
      node match {
        case succ: Successors =>
          succ.successors.values.foreach {
            s =>
              pw.println(s"${node.name} ---> ${s.name}: >")
          }
        case _ =>

      }
    for (node <- sorted)
      node match {
        case pre: Predecessors =>
          pre.predecessors.values.foreach {
            p: Node =>
              pw.println(s"${node.name} ---> ${p.name} :  <")
          }
        case _ =>
      }
    pw.println("@enduml")

  }

  def simpleName(obj: Any): String = obj.getClass.getSimpleName
}

