package codegen

import java.io.{File, FileOutputStream, PrintWriter}

import org.antlr.v4.runtime.ParserRuleContext
import rules.BusinessRulesParser.{DefinitionContext, FileBodyContext}

import scala.collection.mutable.ListBuffer


//
// Generate code from Scala strings and interpolated variables.
//

class ScalaGenerator(fileBodyContext: FileBodyContext, packageName: String, className: String, outputPath: File) {

  var verbose = true

  require(outputPath.isDirectory, s"Parameter outputPath does not contain a directory: outputPath = <$outputPath>")

  val ostrm = new FileOutputStream(outputPath.getCanonicalPath + File.separator + className + ".scala")
  val pw = new PrintWriter(ostrm)

  def print(s: String): Unit = {
    pw.print(s)
  }

  def emit(msg: String)(text: => String): Unit = {
    System.err.println(">>> " + msg)
    pw.print(text.stripMargin('|'))
    System.err.println("<<< " + msg)
  }

  def emit()(text: => String): Unit = {
    pw.print(text.stripMargin('|'))
  }

  def genScalaClass(): Unit = {

    genScalaClassHeader()
    genScalaClassBody()
    genScalaClassFooter()

    pw.flush()
    ostrm.close()

  }

  def genScalaClassHeader(): Unit = emit("Class Header") {
    s"""$packageName
       |
       |class $className {
       |"""
  }

  def genScalaClassBody(): Unit = {
    val definitions: List[DefinitionContext] =
      selectChildren(fileBodyContext.declarations.declaration().iterator) {
        child =>
          child.getClass == classOf[DefinitionContext]
      }

    definitions.foreach(d => System.err.println("Definition\n============\n" + d.toStringTree))

  }

  def genScalaClassFooter(): Unit = emit() {
    """
      |}
      |
      |
    """
  }

  def selectChildren[P, T](children: java.util.Iterator[P])(filterBy: (T) => Boolean): List[T] = {

    val selected = new ListBuffer[T]()

    while (children.hasNext) {
      children.next.getClass match {
        case child:  classOf[P]=>
        case otherwise =>
      }
    }
    selected

  }

}
