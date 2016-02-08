package codegen.symbols

abstract class Symbol(val name: String)

case class ModelReferenceSymbol(override val name: String, components: List[String]) extends Symbol(name)

case class ValidationRuleSymbol(override val name: String) extends Symbol(name)

case class DefinedTermSymbol(override val name: String) extends Symbol(name)

case class LocalVariable(override val name: String) extends Symbol(name)


