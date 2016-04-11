package codegen

import codegen.symbols.{NestedScope, Symbol}
import org.antlr.v4.runtime.tree.ParseTree

import scala.collection.mutable


class ParseTreeScopeAnnotations() {

  val scopes = new NodeMapping[NestedScope]("Scopes")

  val symbols = new NodeMapping[Symbol]("Symbols")

}

class NodeMapping[T <: Object](val id: String, val verbose: Boolean = false) {

  val map = new mutable.HashMap[ParseTree, T]()

  def apply(node: ParseTree): Option[T] = get(node)

  def get(node: ParseTree): Option[T] = trace(s"GET(${node.getClass.getSimpleName}:${node.hashCode()})") {
    map.get(node)
  }

  def put(node: ParseTree, value: T): Option[T] =
    trace(s"PUT(${node.getClass.getSimpleName}:${node.hashCode()}, ${value.getClass.getSimpleName})") {
      map.put(node, value)
    }

  def remove(node: ParseTree): Option[T] = map.remove(node)

  def trace[T](msg: String)(body: => T): T = {
    if (verbose)
      System.err.print(s"$id: $msg")
    val result: T = body
    if (verbose)
      System.err.println(s" ==> ${identity(result)}")
    result
  }

}
