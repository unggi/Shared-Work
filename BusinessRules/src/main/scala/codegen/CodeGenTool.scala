package codegen

import java.io.{File, FileOutputStream, PrintWriter}
import java.util.Locale

import org.antlr.v4.runtime.{ANTLRFileStream, CommonTokenStream}
import org.stringtemplate.v4._
import rules.BusinessRulesParser._
import rules.{BusinessRulesBaseListener, BusinessRulesLexer, BusinessRulesParser}

object CodeGenerator {

  def TEMPLATE_GROUP_FILE = "src/main/templates/Java.stg"

  def main(args: Array[String]): Unit = {
    println("Business Rules Code Generator")
    if (args.length < 1)
      usage("Invalid Number of Arguments")

    val template = new File(TEMPLATE_GROUP_FILE)
    if (!template.exists())
      usage("Cannot find template group file: " + TEMPLATE_GROUP_FILE)

    args.foreach(process(_))
  }

  def process(fileName: String): Unit = {
    println(s"Processing input file <$fileName>")
    val file = new File(fileName)
    if (!file.exists())
      usage(s"File not found for NRL input source - ${file.getAbsoluteFile}")

    val input = new ANTLRFileStream(file.getAbsolutePath)

    val lexer = new BusinessRulesLexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new BusinessRulesParser(tokens)

    parser.setBuildParseTree(true)

    parser.addParseListener(new JavaTargetListener(TEMPLATE_GROUP_FILE))
    val t: FileBodyContext = parser.fileBody

    println("Parsing is complete")
  }

  def usage(msg: String): Unit = {
    System.err.println(s"Fatal Error: $msg")
    System.exit(1)
  }
}

class JavaTargetListener(templateGroupPath: String) extends BusinessRulesBaseListener {

  val PACKAGE = "rules.compiled"
  val CLASSNAME = "IntroductoryExample"
  val OUTPUT_FILE_PATH = "gen/rules/compiled"

  var output: AutoIndentWriter = _

  var outputWriter: PrintWriter = _

  val group = new STGroupFile(templateGroupPath)
  STGroup.trackCreationEvents = true;
  group.registerModelAdaptor(classOf[Object], new AntlrObjectModelAdaptor())
  group.registerRenderer(classOf[String], new StringArticleRenderer())

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

    st = group.getInstanceOf("Declarations")

    st.add("declarations", ctx)

    st.write(output)
    println(st.render)

    st.inspect()
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

class StringArticleRenderer extends StringRenderer {

  override def toString(o: Any, formatString: String, locale: Locale): String =
    if (formatString != null)
      formatString.toUpperCase() match {
        case "ARTICLE" => articularize(o.asInstanceOf[String])
        case "CAPITALIZE" => capitalize(o.asInstanceOf[String])
        case "UNQUOTED" => unquote(o.asInstanceOf[String])
        case "TRIMMED" => o.asInstanceOf[String].trim
        case _ =>
          super.toString(o, formatString, locale)
      }
    else
      super.toString(o, formatString, locale)

  def isVowel(ch: Char): Boolean =
    ch.toUpper match {
      case 'A' => true
      case 'E' => true
      case 'I' => true
      case 'O' => true
      case 'U' => true
      case _ => false
    }

  def articularize(s: String): String = {
    val unquoted = unquote(s)
    if (isVowel(unquoted.charAt(0)))
      "an " + unquoted
    else
      "a " + unquoted
  }

  def capitalize(s: String): String = {
    val unquoted = s.stripPrefix("\"").stripSuffix("\"")
    if (unquoted.length > 0)
      unquoted.charAt(0).toUpper + unquoted.substring(1)
    else
      unquoted
  }

  def unquote(s: String): String =
    s.stripPrefix("\"").stripSuffix("\"").stripPrefix("\'").stripSuffix("\'")
}
