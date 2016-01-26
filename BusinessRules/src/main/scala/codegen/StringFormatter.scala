package codegen

import java.util.Locale

import org.stringtemplate.v4.StringRenderer
import rules.BusinessRulesParser.ModelReferenceContext

object StringFormatter {
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
    val unquoted = unquote(s)
    if (unquoted.length > 0)
      unquoted.charAt(0).toUpper + unquoted.substring(1)
    else
      unquoted
  }

  def unquote(s: String): String =
    s.stripPrefix("\"").stripSuffix("\"").stripPrefix("\'").stripSuffix("\'")

  def generateIdentifier(s: String): String = unquote(s).replaceAll("[-\\s]+", "_")

  def toDotPath(modelReference: ModelReferenceContext): String =
    modelReference.dotPath.children.toArray.mkString(".")
}

class StringFormatter extends StringRenderer {

  import StringFormatter._

  override def toString(o: Any, formatString: String, locale: Locale): String =
    if (formatString != null)
      formatString.toUpperCase match {
        case "ARTICLE" => articularize(o.asInstanceOf[String])
        case "CAPITALIZE" => capitalize(o.asInstanceOf[String])
        case "UNQUOTED" => unquote(o.asInstanceOf[String])
        case "IDENTIFIER" => generateIdentifier(o.asInstanceOf[String])
        case _ =>
          super.toString(o, formatString, locale)
      }
    else
      super.toString(o, formatString, locale)


}
