package codegen.symbols

import codegen.model.{Association, Classifier}
import rules.BusinessRulesParser.DefinitionContext


abstract class Symbol(val name: String) {
  def asComponents: Array[String] = Array(name)
}

trait StaticType {
  def classifier: Classifier
}

case class ValidationRuleSymbol(override val name: String) extends Symbol(name)

case class DefinedTermSymbol(override val name: String, definition: DefinitionContext) extends Symbol(name)

case class LocalVariable(override val name: String, classifier: Classifier) extends Symbol(name) with StaticType

case class Parameter(override val name: String, classifier: Classifier) extends Symbol(name) with StaticType {
  override def toString: String = s"Parameter($name, $classifier)"
}

case class ParameterReference(parameter: Parameter, components: List[String]) extends Symbol(parameter.name) with StaticType {

  require(components.size > 0, "Component list must have at least one member")
  require(components.head.equals(parameter.name), "First component in Parameter Reference list must be the parameter itself.")

  override def toString: String = s"ParameterReference($parameter, [${components.mkString(", ")}])"

  override def asComponents: Array[String] = (parameter.name :: components).toArray

  override def classifier: Classifier = parameter.classifier
}

//
// An index into a Collection of Elements.
//
case class CollectionIndexSymbol(override val name: String, association: Association) extends Symbol(name) with StaticType {
  override def toString: String =
    s"CollectionIndexSymbol(name = $name, association = ${association.name}, collection = ${association.targetName} type = ${association.target})"

  override def classifier: Classifier = association.target

}

case class CollectionIndexReference(collectionIndexSymbol: CollectionIndexSymbol, components: List[String]) extends Symbol(collectionIndexSymbol.name) {
  override def asComponents: Array[String] = (collectionIndexSymbol.name :: components).toArray
}







