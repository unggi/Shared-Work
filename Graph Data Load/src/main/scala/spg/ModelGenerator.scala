package spg

import java.io.{File, FileOutputStream, PrintWriter}
import java.lang.reflect.Field

import spg.datamodel.{TradeBean, CounterpartySalesCoverageBean, CounterpartyBean}

import scala.collection.mutable


object ModelGenerator {

  def main(args: Array[String]): Unit = {
    println("Model Generator")

    generateUML(List(classOf[TradeBean], classOf[CounterpartyBean], classOf[CounterpartySalesCoverageBean]))
  }

  def generateUML(classes: List[Class[_]]): Unit = {

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
    val className = getGraphNodeName(cls).getOrElse(cls.getSimpleName)

    val pw = new PrintWriter(new FileOutputStream(path + File.separator + className + ".puml"))
    generateFileHeader(pw, false)
    pw.println(s"class $className {")
    cls.getDeclaredFields().foreach {
      field =>
        pw.println(s"    <b>${field.getName}</b>: ${field.getType.getSimpleName}")
    }

    pw.println("}")
    generateFileFooter(pw)
    pw.flush()
    pw.close()
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


  def generateNodeClasses(classes: List[Class[_]], pw: PrintWriter): Unit = {

    var visited = new mutable.HashSet[String]()

    classes.foreach {
      cls =>

        getGraphNodeName(cls) match {
          case Some(s) =>
            if (!visited.contains(s)) {
              pw.println( s"""class "${s}" as ${s}""")
            }
          case None =>
        }

        cls.getDeclaredFields.foreach {
          field =>

            field.getDeclaredAnnotations.foreach {

              case r: Relationship =>

                if (!visited.contains(r.toClass)) {
                  visited.add(r.toClass)
                  pw.println(s"class ${r.toClass} ")
                }

              case id: ID =>

              case derived: DerivedFromAssociation =>

              case other => // Skip

            }
        }
    }
  }

  def getGraphNodeName(cls: Class[_]): Option[String] =
    cls.getAnnotation(classOf[GraphNode]) match {
      case null => None
      case g: GraphNode => Some(g.name())
    }

  def generateRelationships(classes: List[Class[_]], pw: PrintWriter): Unit = {

    classes.foreach {
      cls =>
        val className = getGraphNodeName(cls).getOrElse(cls.getSimpleName)
        cls.getDeclaredFields.foreach {
          field =>
            field.getDeclaredAnnotations.foreach {

              case r: Relationship =>
                pw.println(s"${className} ---> ${r.toClass} : \\#${field.getName} >")
              case id: ID =>

              case derived: DerivedFromAssociation =>

              case other => // Skip
            }
        }
    }
  }
}
