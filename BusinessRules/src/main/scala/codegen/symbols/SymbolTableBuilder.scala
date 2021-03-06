package codegen.symbols

import codegen.model.Model

import scala.collection.mutable


class SymbolTable(val model: Model) {

  val global = new GlobalScope()

  def print(): Unit = {
    println("Symbol Table")
    global.print(4)
  }
}

class SymbolTableBuilder(val model: Model,var verbose: Boolean = true) {

  var symbolTable: SymbolTable = new SymbolTable(model)

  var scopeStack = new mutable.Stack[NestedScope]() {
    push(symbolTable.global)
  }

  def scope = scopeStack.top

  def declare(entry: Symbol): Option[Symbol] =
    trace(s"Declare: name = ${entry.name}") {
      scopeStack.top.declare(entry)
    }

  def openScope(scope: NestedScope): Unit =
    trace("Open Scope") {
      scopeStack.top.addSubScope(scope)
      scopeStack.push(scope)
    }

  def closeScope(): Unit =
    trace("Close Scope") {
      scopeStack.pop()
    }

  def trace[T](msg: String)(body: => T): T = {
    if (verbose)
      System.err.print(msg + " [")
    val result: T = body
    if (verbose)
      System.err.println(s"] ${identity(result)}")
    result
  }

}
