package spg

import java.io.{File, FileOutputStream, PrintWriter}
import java.lang.annotation.Annotation

import spg.datamodel._

import scala.collection.mutable

object ModelGenerator {

  def main(args: Array[String]): Unit = {
    println("Model Generator")

    generateUML(classOf[Trade], classOf[Counterparty], classOf[CounterpartySalesCoverage])
  }

  def generateUML(classes: Class[_]*): Unit = {

    val path = new File("generated/doc/")
    if (!path.exists)
      path.mkdirs()

    // Generate a single file called classes which shows all relationships.
    val filename = "Classes.puml"
    val pw = new PrintWriter(new FileOutputStream(path + File.separator + filename))

    generateFileHeader(pw, true)

    generateNodeClasses(classes, pw)
    pw.println("\n' Relationships\n")
    generateRelationships(classes, pw)

    generateFileFooter(pw)

    pw.flush()
    pw.close()

    classes.foreach {
      cls =>
        generateNodePropertyDiagram(cls, path)
    }
  }

  def generateNodePropertyDiagram(cls: Class[_], path: File): Unit = {
    val className = getGraphNodeName(cls)

    val pw = new PrintWriter(new FileOutputStream(path + File.separator + className + ".puml"))
    generateFileHeader(pw, false)
    generateClassProperties(cls, pw)
    generateFileFooter(pw)
    pw.flush()
    pw.close()
  }


  def generateClassProperties(cls: Class[_], pw: PrintWriter): Unit = {

    val className = getGraphNodeName(cls)
    pw.println(s"class $className {")
    cls.getDeclaredFields().foreach {
      field =>
        val qualifiers = new StringBuilder()
        for (annotation <- field.getDeclaredAnnotations)
          annotation match {
            case ass: Association =>
            case id: ID => qualifiers.append(" ID")
            case opt: Optional => qualifiers.append("?")
            case ann: Annotation =>
              println(s"Unknown annotation ${annotation.getClass.getSimpleName} on field ${field.getName}")
          }

        pw.println(s"    <b>${field.getName}</b>: ${field.getType.getSimpleName}$qualifiers")
    }

    pw.println("}")

  }

  def generateFileHeader(pw: PrintWriter, hideMembers: Boolean): Unit =
    pw.println(
      s"""@startuml
        |
        |!include Skin.iuml
        |
        |${if (hideMembers) "hide members" else ""}
        |
      """.stripMargin)


  def generateFileFooter(pw: PrintWriter): Unit =
    pw.println(
      """
        |@enduml""".stripMargin)


  def generateNodeClasses(classes: Seq[Class[_]], pw: PrintWriter): Unit = {

    var visited = new mutable.HashSet[Class[_]]()

    classes.foreach {
      cls =>

        if (!visited.contains(cls)) {
          val nodeName = getGraphNodeName(cls)
          pw.println( s"""class "$nodeName" as $nodeName""")
        }

        cls.getDeclaredFields.foreach {
          field =>

            field.getDeclaredAnnotations.foreach {

              case r: Association =>

                if (!visited.contains(r.toClass)) {
                  visited.add(r.toClass)
                  pw.println(s"class ${getGraphNodeName(r.toClass)} ")
                }

              case id: ID =>

              case other => // Skip

            }
        }
    }
  }

  def getGraphNodeName(cls: Class[_]): String =
    cls.getAnnotation(classOf[GraphNode]) match {
      case null => cls.getSimpleName
      case g: GraphNode => g.label()
    }

  def generateRelationships(classes: Seq[Class[_]], pw: PrintWriter): Unit = {

    classes.foreach {
      cls =>
        val className = getGraphNodeName(cls)
        cls.getDeclaredFields.foreach {
          field =>
            field.getDeclaredAnnotations.foreach {

              case r: Association =>
                pw.println(s"$className ${cardinalityToText(r.srcCount())}--->${cardinalityToText(r.targetCount())} ${getGraphNodeName(r.toClass)} : \\#${field.getName} >")
              case id: ID =>

              case other => // Skip
            }
        }
    }
  }

  import spg.datamodel.Association.Cardinality._

  def cardinalityToText(count: Association.Cardinality): String =
    count match {
      case ONE => "\"1\""
      case ZERO_OR_MORE => "\"*\""
      case ONE_OR_MORE => "\"+\""
    }

}
