package codegen

import java.io.{File, FileOutputStream, PrintWriter}
import java.util.Locale

import org.antlr.v4.runtime.{ANTLRFileStream, CommonTokenStream}
import org.stringtemplate.v4._
import rules.BusinessRulesParser._
import rules.{BusinessRulesBaseListener, BusinessRulesLexer, BusinessRulesParser}

import scala.collection.mutable.ArrayBuffer

object CodeGenerator {
  
  var outputTarget = "Scala"
  var templateGroupFile = ""
  var outputPath = "gen/rules/compiled"
  var packageName = "rules.compiled"
  var templateDir = "src/main/templates"

  def main(args: Array[String]): Unit = {
    println("Business Rules Code Generator")

    if (args.length < 1)
      usage("Invalid Number of Arguments - must be at least 1")
    
    val filelist = new ArrayBuffer[String]()

    def GetOpt(list: List[String]): Unit =
      list match {
        case Nil => // Nothing to do
        case "-java" :: tail =>
          outputTarget = "Java"
          GetOpt(tail)
        case "-scala" :: tail =>
          outputTarget = "Scala"
          GetOpt(tail)
        case "-templateDir" :: value  :: tail =>
          templateDir = value
          GetOpt(tail)
        case "-groupfile" :: value :: tail =>
          templateGroupFile = value
          GetOpt(tail)
        case "-outputPath" :: value :: tail =>
          outputPath = value
          GetOpt(tail)
        case "-outputPackage" :: value :: tail =>
          packageName = value
          GetOpt(tail)
        case any :: tail  =>
          filelist.append(any)
          GetOpt(tail)
      }

    val groupFile = s"$templateDir/$templateGroupFile.stg"

    val template = new File(templateGroupFile)
    if (!template.exists())
      usage("Cannot find template group file: " + templateGroupFile)

    filelist.foreach {
      fileName =>
        val file = new File(fileName)

        if (!file.exists())
              usage(s"File not found for NRL input source - $fileName in path ${file.getAbsoluteFile}.")

        println(s"Processing input file <$fileName>.")

        generate(file)

        println(s"Processing input file <$fileName> is complete.")

    }
  }

  def generate(file: File): Unit = {

    val input = new ANTLRFileStream(file.getAbsolutePath)
    val lexer = new BusinessRulesLexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new BusinessRulesParser(tokens)

    parser.setBuildParseTree(true)

    parser.addParseListener(new JavaTargetListener(templateGroupFile))
    val t: FileBodyContext = parser.fileBody

    println("Parsing is complete")
  }

  def usage(msg: String): Unit = {
    System.err.println(s"Usage Error: $msg")

    System.err.println
      """
        |Usage: CodeGenerator [-java | -scala] [-groupfile] [-outputpath] (rulefile)+
      """.stripMargin

    System.exit(1)
  }
}




