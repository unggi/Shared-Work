package codegen

import java.io.{FileOutputStream, PrintWriter}

import codegen.modeladaptors.AntlrObjectModelAdaptor
import org.stringtemplate.v4.{AutoIndentWriter, ST, STGroup, STGroupFile}
import rules.BusinessRulesParser.{DefinitionContext, FileBodyContext}
import rules.{BusinessRulesBaseListener, BusinessRulesParser}


class JavaTargetListener(templateGroupPath: String) extends BusinessRulesBaseListener {

  val PACKAGE = "rules.compiled"
  val CLASSNAME = "IntroductoryExample"
  val OUTPUT_FILE_PATH = "gen/rules/compiled"

  var output: AutoIndentWriter = _
  var outputWriter: PrintWriter = _

  val group = new STGroupFile(templateGroupPath)
  STGroup.trackCreationEvents = true;
  group.registerModelAdaptor(classOf[Object], new AntlrObjectModelAdaptor())
  group.registerRenderer(classOf[String], new StringFormatter())

  var st: ST = _

  override def enterFileBody(ctx: FileBodyContext) = {
    super.enterFileBody(ctx)
    try {

      outputWriter = new PrintWriter(new FileOutputStream(OUTPUT_FILE_PATH + "/" + CLASSNAME + ".java"))
      output = new AutoIndentWriter(outputWriter)

      st = group.getInstanceOf("FileBodyHeader")

      st.add("fileBody", ctx)
      st.add("package", PACKAGE)
      st.add("className", CLASSNAME)
      st.write(output)
      println(st.render())

    }
    catch {
      case e: Throwable =>
        System.err.println(s"Could not open output file: " + e.getStackTraceString)
    }
  }

  override def exitDefinition(ctx: DefinitionContext): Unit = {
    super.enterDefinition(ctx)
  }

  override def exitDeclarations(ctx: BusinessRulesParser.DeclarationsContext): Unit = {

    super.exitDeclarations(ctx)

    st = group.getInstanceOf("RuleEvaluator")

    st.add("declarations", ctx)

    st.write(output)
    println(st.render)
  }

  override def exitFileBody(ctx: FileBodyContext) = {
    super.exitFileBody(ctx)

    st = group.getInstanceOf("FileBodyFooter")
    st.add("fileBody", ctx)
    st.add("package", PACKAGE)
    st.add("className", CLASSNAME)

    // Write the templates
    st.write(output)
    println(st.render())

    // Close File Here
    outputWriter.flush
    outputWriter.close
  }
}
