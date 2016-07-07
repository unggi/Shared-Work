package codegen

import java.io.{File, FileOutputStream, PrintWriter}

/**
  * Created by unggi on 6/29/16.
  */
abstract class TemplateSupport(path: File, basename: String, suffix: String) {

  val verbose = false

  require(path.isDirectory, s"Parameter outputPath does not contain a directory: outputPath = <$path>")

  val ostrm = new FileOutputStream(path.getCanonicalPath + File.separator + basename + suffix)
  val pw = new PrintWriter(ostrm)

  def print(s: String): Unit = {
    pw.print(s)
  }

  def emit(text: String): Unit = {
    pw.print(text.stripMargin('|'))
    System.err.print(text.stripMargin('|'))
  }

  def emit(text: => String): Unit = {
    pw.print(text.stripMargin('|'))
    System.err.print(text.stripMargin('|'))
  }

  def tbd(obj: Any): String = {
    val msg =
      obj match {
        case s: String => s
        case o => o.getClass.getSimpleName
      }
    val top = Thread.currentThread().getStackTrace()
    s"NOT IMPLEMENTED ($msg)\n"
  }

  var indent = 0
  val tabsize = 4

  def template(text: => String): String =
    if (verbose) {
      indent = indent + tabsize
      val top = Thread.currentThread.getStackTrace
      val scope =
        top(2).getMethodName match {
          case "required" => top(3).getMethodName
          case "optional" => top(3).getMethodName
          case "apply" => top(3).getMethodName
          case "alternates" => top(3).getMethodName
          case "template" => top(3).getMethodName
          case other => top(2).getMethodName
        }
      val tab: String = " " * indent
      val s = s"""\n$tab>>> $scope [${text.stripMargin('|').trim}]\n$tab<<< $scope"""
      indent = indent - tabsize
      s
    }
    else
      text.stripMargin('|')

  def alternates[T](rule: T)(matcher: (T) => String): String = template {
    matcher(rule)
  }

  def required[T](value: T, body: (T) => String): String = template {
    require(value != null, "Required template section expected a non-null value")
    body(value)
  }

  def optional[T](value: T, body: (T) => String): String = template {
    if (value != null)
      body(value)
    else ""
  }

}
