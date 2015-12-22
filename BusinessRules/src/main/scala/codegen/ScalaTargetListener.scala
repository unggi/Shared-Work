package codegen

import java.io.{FileOutputStream, PrintWriter}

import org.stringtemplate.v4.{AutoIndentWriter, ST, STGroup, STGroupFile}
import rules.BusinessRulesParser.FileBodyContext
import rules.{BusinessRulesBaseListener, BusinessRulesParser}

class ScalaTargetListener(template: String, pkg: String, className: String, outputFilePath: String)
  extends BusinessRulesBaseListener {

  var output: AutoIndentWriter = _
  var outputWriter: PrintWriter = _

  val group = new STGroupFile(template)
  STGroup.trackCreationEvents = false
  STGroup.verbose = false
  group.registerModelAdaptor(classOf[Object], new AntlrObjectModelAdaptor())
  group.registerRenderer(classOf[String], new StringArticleRenderer())

  var st: ST = _

  override def enterFileBody(ctx: FileBodyContext) = {
    super.enterFileBody(ctx)
    val outputFileName = outputFilePath + "/" + className + ".scala"

    try {

      outputWriter = new PrintWriter(new FileOutputStream(outputFileName))
      output = new AutoIndentWriter(outputWriter)

      st = group.getInstanceOf("FileBodyHeader")

      st.add("fileBody", ctx)
      st.add("package", pkg)
      st.add("className", className)
      st.write(output)
      println(st.render())

    }
    catch {
      case e: Throwable =>
        System.err.println(s"Could not open output file: $outputFileName\n" + e.getMessage)
    }
  }

  override def exitDeclarations(ctx: BusinessRulesParser.DeclarationsContext): Unit = {

    super.exitDeclarations(ctx)

    st = group.getInstanceOf("RuleSet")

    st.add("declarations", ctx)

    st.write(output)
    println(st.render)
    st.inspect
  }

  override def exitFileBody(ctx: FileBodyContext) = {
    super.exitFileBody(ctx)

    st = group.getInstanceOf("FileBodyFooter")
    st.add("fileBody", ctx)
    st.add("package", pkg)
    st.add("className", className)

    // Write the templates
    st.write(output)
    println(st.render())

    // Close File Here
    outputWriter.flush
    outputWriter.close

  }
}
