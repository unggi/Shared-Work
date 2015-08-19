package codegen

import java.io.File
import java.lang.reflect.Method
import java.util.Locale

import org.antlr.v4.runtime.{ANTLRFileStream, CommonTokenStream}
import org.stringtemplate.v4.{STGroup, STGroupFile, StringRenderer}
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

    System.err.println("Parsing is complete")
  }

  def usage(msg: String): Unit = {
    System.err.println(s"Fatal Error: $msg")
    System.exit(1)
  }
}

class JavaTargetListener(templateGroupPath: String) extends BusinessRulesBaseListener {

  val group = new STGroupFile(templateGroupPath)
  STGroup.trackCreationEvents = true;
  group.registerModelAdaptor(classOf[Object], new AntlrObjectModelAdaptor())
  group.registerRenderer(classOf[String], new StringArticleRenderer())

  val st = group.getInstanceOf("Definition")

  override def exitDefinition(ctx: DefinitionContext): Unit = {
    super.enterDefinition(ctx)

    st.add("definition", ctx)
    st.add("package", "rules")
    st.add("classname", "TestClass")

    System.err.println("Binary Predicate = " + ctx.constraint().logicalStatement(0).predicate.getClass)
  }

  override def exitDeclarations(ctx: BusinessRulesParser.DeclarationsContext): Unit = {
    super.exitDeclarations(ctx)
    System.err.println(st.render())
    st.inspect()
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
    val unquoted = s.stripPrefix("\"").stripSuffix("\"")
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
    s.stripPrefix("\"").stripSuffix("\"")
}
