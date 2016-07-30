package codegen.symbols

import rules.BusinessRulesParser.DefinitionContext


abstract class Symbol(val name: String) {
  def asComponents: Array[String] = Array(name)
}


case class ValidationRuleSymbol(override val name: String) extends Symbol(name)

case class DefinedTermSymbol(override val name: String, definition: DefinitionContext) extends Symbol(name)

case class LocalVariable(override val name: String) extends Symbol(name)

case class Parameter(override val name: String, classifier: String) extends Symbol(name) {
  override def toString: String = s"Parameter($name, $classifier)"
}

case class ParameterReference(parameter: Parameter, components: List[String]) extends Symbol(parameter.name) {

  override def toString: String = s"ParameterReference($parameter, [${components.mkString(", ")}])"

  override def asComponents: Array[String] = (parameter.name :: components).toArray

}

case class CollectionIndexSymbol(override val name: String, collectionRef: Symbol) extends Symbol(name) {
  override def toString: String =
    s"CollectionIndexSymbol(name = $name, collectionRef = [$collectionRef])"
}

case class CollectionIndexReference(collectionIndexSymbol: CollectionIndexSymbol, components: List[String]) extends Symbol(collectionIndexSymbol.name) {
  override def asComponents: Array[String] = (collectionIndexSymbol.name :: components).toArray
}







