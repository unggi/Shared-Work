package codegen.symbols

import codegen.model.Classifier
import rules.BusinessRulesParser.DefinitionContext


abstract class Symbol(val name: String) {
  def asComponents: Array[String] = Array(name)
}


case class ValidationRuleSymbol(override val name: String) extends Symbol(name)

case class DefinedTermSymbol(override val name: String, definition: DefinitionContext) extends Symbol(name)

case class LocalVariable(override val name: String) extends Symbol(name)

case class Parameter(override val name: String, classifier: Classifier) extends Symbol(name) {
  override def toString: String = s"Parameter($name, $classifier)"
}

case class ParameterReference(parameter: Parameter, components: List[String]) extends Symbol(parameter.name) {

  require(components.size > 0, "Component list must have at least one member")
  require(components.head.equals(parameter.name), "First component in Parameter Reference list must be the parameter itself.")

  override def toString: String = s"ParameterReference($parameter, [${components.mkString(", ")}])"

  override def asComponents: Array[String] = (parameter.name :: components).toArray

}

//
// An index into a Collection of Elements.
//
case class CollectionIndexSymbol(override val name: String, collectionRef: Symbol, elementClassifier: Classifier) extends Symbol(name) {
  override def toString: String =
    s"CollectionIndexSymbol(name = $name, collectionRef = [$collectionRef] $elementClassifier)"
}

case class CollectionIndexReference(collectionIndexSymbol: CollectionIndexSymbol, components: List[String]) extends Symbol(collectionIndexSymbol.name) {
  override def asComponents: Array[String] = (collectionIndexSymbol.name :: components).toArray
}







