package codegen

import java.io.{File, FileOutputStream, PrintWriter}

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import rules.BusinessRulesParser.{DeclarationContext, DefinitionContext, FileBodyContext}

import scala.collection._


//
// Generate code from Scala strings and interpolated variables.
//

class ScalaGenerator(fileBodyContext: FileBodyContext, packageName: String, className: String, outputPath: File) {

  import scala.collection.JavaConversions._

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
    // Collect Definitions
    val definitions = query(fileBodyContext.declarations()) {
      case decl: DeclarationContext => Option(decl.definition())
      case otherwise => None
    }

    definitions.foreach {
      defn =>
      // Generate definition code body
    }

    // Collect Validation Rules


  }

  def genScalaClassFooter(): Unit = emit() {
    """
      |}
      |
      |
    """
  }

  def find[T <: ParserRuleContext](ctx: ParserRuleContext, cls: Class[T]): immutable.List[T] =
    ctx.getRuleContexts(cls).toList

  def query[T <: ParserRuleContext](ctx: ParserRuleContext)(mapper: (ParserRuleContext) => Option[T]): immutable.List[T] =
    find(ctx, classOf[ParserRuleContext]).flatMap{ mapper(_) }


}
