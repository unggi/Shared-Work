package codegen.model

import scala.collection.mutable.ListBuffer


class ModelElement(val name: String) {

}

class Classifier(override val name: String) extends ModelElement(name) {
  val attributes = new ListBuffer[Attribute]()
}

class Attribute(override val name: String) extends ModelElement(name) {

}
