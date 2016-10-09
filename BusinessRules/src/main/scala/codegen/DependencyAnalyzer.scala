package codegen

import codegen.symbols.{Parameter, SymbolTable}

class DependencyAnalyzer(symbolTable: SymbolTable) {

  import DependencyGraph._

  val graph = new DependencyGraph()

  // Gather Input Nodes from the symbol table - walk every scope
  case class Input(override val name: String, param: Parameter) extends InputNode(name)





}
