package codegen

import codegen.symbols.{NestedScope, Symbol}
import org.antlr.v4.runtime.tree.ParseTree

import scala.collection.mutable


class ParseTreeScopeAnnotations() {

  val scopes = new NodeMapping[NestedScope]()

  val symbols = new NodeMapping[Symbol]()

  val paths = new NodeMapping[List[String]]

}


class NodeMapping[T <: Object](val verbose: Boolean = true) {
  val map = new mutable.HashMap[ParseTree, T]()

  def apply(node: ParseTree): Option[T] = trace(s"GET(${node.getText}") {
    map.get(node)
  }

  def get(node: ParseTree): Option[T] = trace(s"GET(${node.getText}") {
    map.get(node)
  }

  def put(node: ParseTree, value: T): Option[T] =
    trace(s"PUT(${node.getText} ${value}") {
      map.put(node, value)
    }

  def remove(node: ParseTree): Option[T] = map.remove(node)

  def trace[T](msg: String)(body: => T): T = {
    if (verbose)
      System.err.print(msg + " [")
    val result: T = body
    if (verbose)
      System.err.println(s"] ${identity(result)}")
    result
  }

}
