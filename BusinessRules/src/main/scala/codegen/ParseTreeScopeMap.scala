package codegen

import codegen.symbols.NestedScope
import org.antlr.v4.runtime.tree.ParseTree
import scala.collection.mutable


class ParseTreeScopeMap() {
  var annotations = new mutable.HashMap[ParseTree, NestedScope]()

  def get(node: ParseTree): Option[NestedScope] = annotations.get(node)

  def put(node: ParseTree, value: NestedScope): Unit = annotations.put(node, value)

  def removeFrom(node: ParseTree): Option[NestedScope] = annotations.remove(node)
}
