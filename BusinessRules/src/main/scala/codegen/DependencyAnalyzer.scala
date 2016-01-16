package codegen

import codegen.symbols.SymbolTable
import rules.BusinessRulesBaseListener

class DependencyAnalyzer(symbolTable: SymbolTable) extends BusinessRulesBaseListener {

  // every context is a document input node
  // every rule that contains a context is a rule node that links to the corresponding document input node
  // every model reference within a constraint is a data reference node linked to the rule it occurs in and the document input node of it's context
  // every model

}
