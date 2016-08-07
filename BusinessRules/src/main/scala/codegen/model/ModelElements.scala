package codegen.model

import java.io.PrintWriter

import codegen.StringFormatter._
import codegen.model.GlobalClassifiers.Primitive
import codegen.model.MultiplicityValues._

import scala.collection.mutable

abstract class ModelElement(val name: String)

class Classifier(override val name: String, isPrimitive: Boolean = false) extends ModelElement(name) {
  val properties = new mutable.HashMap[String, Property]()

  def addProperty(name: String, classifier: Classifier, multiplicity: Multiplicity = One): Classifier = {
    properties.put(name, new Property(name, classifier, multiplicity))
    this
  }

  val outgoing = new mutable.HashMap[String, Association]()

  def addOutgoing(assoc: Association): Classifier = {
    require(assoc.source == this)
    outgoing.put(assoc.targetName, assoc)
    this
  }

  val incoming = new mutable.HashMap[String, Association]()

  def addIncoming(assoc: Association): Classifier = {
    require(assoc.target == this)
    incoming.put(assoc.sourceName, assoc)
    this
  }
}

class Property(override val name: String, val classifier: Classifier, multiplicity: Multiplicity) extends ModelElement(name)

class Association(override val name: String,
                  val source: Classifier,
                  val sourceName: String,
                  val sourceMultiplicity: Multiplicity,
                  val target: Classifier,
                  val targetName: String,
                  val targetMultiplicity: Multiplicity
                 ) extends ModelElement(name) {
  source.addOutgoing(this)
  target.addIncoming(this)
}


object MultiplicityValues {

  sealed trait Multiplicity

  case object Zero extends Multiplicity

  case object One extends Multiplicity

  case object ZeroOrOne extends Multiplicity

  case object ZeroOrMore extends Multiplicity

  case object OneOrMore extends Multiplicity

}

/**
  * Given a model source, create an NRL model representation.
  */
object ModelFactory {

  def create(modelPath: String): Model = {
    modelPath match {
      case "SampleTradeModel" => new Model(SampleTradeModel.tradeClass)
      case otherwise =>
        assert(false, "Unknown Model Path: <" + otherwise + ">")
        null
    }

  }
}

class Model(val root: Classifier) {
  def renderPUML(pw: PrintWriter): Unit = {

    def isPrimitive(prop: Property) = prop.classifier.isInstanceOf[Primitive]

    def render(cls: Classifier): Unit = {

      require(cls != null)

      val scalars = cls.properties.values.filter(isPrimitive(_))

      val objects = cls.properties.values.filter(!isPrimitive(_))

      pw.println(s"""\nclass ${id(cls.name)} as \"${cls.name}\"{""")

      scalars.foreach(renderProp(_))

      pw.println("}")

      objects.foreach(obj => render(obj.classifier))
      objects.foreach(obj => pw.println(s"${cls.name} -> ${obj.name}"))

      cls.outgoing.values.foreach(renderAssociation(_))
    }

    def renderAssociation(assoc: Association) = {
      pw.println(s"${id(assoc.source.name)} -> ${id(assoc.target.name)} : ${assoc.name} >")
      render(assoc.target)

    }

    def renderProp(prop: Property) =
      pw.println(s"\t${prop.name}: ${prop.classifier.name}")

    // Execution Starts Here
    pw.println("@startuml")
    pw.println("hide methods")
    render(root)
    pw.println("@enduml")
  }


}

object GlobalClassifiers {

  trait Primitive

  val IntegerType = new Classifier("Integer") with Primitive

  val DoubleType = new Classifier("Double") with Primitive

  val StringType = new Classifier("String") with Primitive

  val DateType = new Classifier("Date") with Primitive

  val BooleanType = new Classifier("Boolean") with Primitive

}