package codegen.symbols

import org.antlr.v4.runtime.tree.TerminalNode

abstract class Symbol(val name: String)

case class ModelReferenceSymbol(override val name: String, components: List[TerminalNode]) extends Symbol(name) {
  override def toString: String = s"ModelReference($name, ${components.head}, ${components.tail})"
}

case class ValidationRuleSymbol(override val name: String) extends Symbol(name)

case class DefinedTermSymbol(override val name: String) extends Symbol(name)

case class LocalVariable(override val name: String) extends Symbol(name)

case class ModelParameterSymbol(override val name: String, val reference: ModelReferenceSymbol) extends Symbol(name) {
  override def toString: String = s"ModelParameter($name) ==> $reference"
}

case class CollectionIndexSymbol(override val name: String, val collection: ModelReferenceSymbol) extends Symbol(name) {
  override def toString: String = s"CollectionIndex($name) ==> $collection"
}
