package codegen

import java.io.File

import org.antlr.v4.runtime.tree.{ParseTree, ParseTreeWalker, TerminalNode}
import org.antlr.v4.runtime.{ANTLRFileStream, CommonTokenStream}
import rules.BusinessRulesParser._
import rules.{BusinessRulesBaseListener, BusinessRulesLexer, BusinessRulesParser}

import scala.collection.mutable.ArrayBuffer

object CodeGenerator {

  var outputTarget = "Scala"
  var templateGroupFile = "Scala"
  var outputPath = "gen/rules/compiled"
  var packageName = "rules.compiled"
  var templateDir = "src/main/templates"
  var outputClass = "RulePlanExample"

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
        case "-templateDir" :: value :: tail =>
          templateDir = value
          GetOpt(tail)
        case "-groupfile" :: value :: tail =>
          templateGroupFile = value
          GetOpt(tail)
        case "-outputPath" :: value :: tail =>
          outputPath = value
          GetOpt(tail)
        case "-outputClass" :: value :: tail =>
          outputClass = value
          GetOpt(tail)
        case "-outputPackage" :: value :: tail =>
          packageName = value
          GetOpt(tail)
        case any :: tail =>
          filelist.append(any)
          GetOpt(tail)
      }

    GetOpt(refArrayOps(args).toList)

    filelist.foreach {
      fileName =>
        val file = new File(fileName)

        if (!file.exists())
          usage(s"File not found for NRL input source - $fileName in path ${file.getAbsoluteFile}.")

        println(s"Processing input file <$fileName>.")

        generate(file, s"$templateDir/$templateGroupFile.stg")

        println(s"Processing input file <$fileName> is complete.")

    }
  }

  def generate(file: File, groupFile: String): Unit = {

    val template = new File(groupFile)
    if (!template.exists())
      usage(s"Cannot find template group file: $groupFile")

    val input = new ANTLRFileStream(file.getAbsolutePath)
    val lexer = new BusinessRulesLexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new BusinessRulesParser(tokens)

    parser.setBuildParseTree(true)

    val tree: FileBodyContext = parser.fileBody()

    println("TREE IS ============")


    def printTreeClasses(ctx: ParseTree, depth: Int): Unit = {

      for (i <- 0 until ctx.getChildCount) {
        val child = ctx.getChild(i)

        // If there are children then print an indent string using '-' character.
        val ch = if (child.getChildCount > 0) '-' else ' '
        for (j <- 0 until depth) print(ch)
        print("> ")

        // Find the child's name in the parent node - if it exists
        val parent = child.getParent
        val parentClass = parent.getClass

        val childClassName = child.getClass.getSimpleName
        val childFields = child.getClass.getDeclaredFields

        val parentNameField = parentClass.getDeclaredFields.find {
          fld =>
            fld.get(parent) match {
              case null => false
              case node =>
                node.equals(child)
            }
        }

        if (parentNameField.isDefined)
          print(parentNameField.get.getName + " = ")

        if (child.isInstanceOf[TerminalNode])
          print(child.getText)
        else
          print(childClassName)

        if (!child.isInstanceOf[TerminalNode] && childFields.size > 0)
          print(" [" + childFields.map(_.getName).mkString(" | ") + "]")

        println()

        printTreeClasses(child, depth + 2)

      }
    }
    printTreeClasses(tree, 1)

    val symbolTable = new SymbolTable()

    val validator: BusinessRulesBaseListener = new ValidationListener(symbolTable)

    val walker = new ParseTreeWalker()
    walker.walk(validator, tree)

    for ((e, v) <- symbolTable.map) {
      println(s"$e ==> $v")
    }

    val listener: BusinessRulesBaseListener =
      outputTarget.toLowerCase match {
        case "scala" =>
          new ScalaTargetListener(template.getAbsolutePath,
            packageName, outputClass, outputPath)
        case "java" =>
          new JavaTargetListener(template.getAbsolutePath)
      }

    walker.walk(listener, tree)

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




