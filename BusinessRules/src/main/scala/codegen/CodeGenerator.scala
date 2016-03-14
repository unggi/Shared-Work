package codegen

import java.io.{File, PrintWriter, StringWriter}

import codegen.symbols.{CollectionMemberScope, SymbolTableBuilder}
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
          usage(s"File not found for NRL input source: $fileName in path ${file.getAbsoluteFile}.")

        println(s"Processing input file <$fileName>.")

        generate(file, s"$templateDir/$templateGroupFile.stg")

        println(s"Processing input file <$fileName> is complete.")
    }
  }

  def generate(file: File, groupFile: String): Unit = {

    val outputDir = new File(outputPath)
    if (!outputDir.exists && !outputDir.isDirectory)
      outputDir.mkdirs()

    val template = new File(groupFile)
    if (!template.exists())
      usage(s"Cannot find template group file: $groupFile")

    val input = new ANTLRFileStream(file.getAbsolutePath)
    val lexer = new BusinessRulesLexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new BusinessRulesParser(tokens)

    parser.setBuildParseTree(true)

    // parser.addParseListener(validator)
    val tree: FileBodyContext = parser.fileBody()

    val generator: BusinessRulesBaseListener =
      outputTarget.toLowerCase match {
        case "scala" =>
          new ScalaTargetListener(template.getAbsolutePath,
            packageName, outputClass, outputPath)
        case "java" =>
          new JavaTargetListener(template.getAbsolutePath)
      }

    val walker = new ParseTreeWalker()

    val builder = new SymbolTableBuilder(false)

    val declarationPhase = new DeclarationPhase(builder)
    walker.walk(declarationPhase, tree)

    val resolver = new ResolutionPhase(builder.symbolTable, declarationPhase.nodeScopes)
    walker.walk(resolver, tree)

    walker.walk(generator, tree)

    println("Parsing is complete")

    printTreeClasses(tree, 1, declarationPhase.nodeScopes)

    builder.symbolTable.print()

    //  dependencyAnalyzer.graph.render(new AutoIndentWriter(new PrintWriter(System.err)))
  }

  def printTreeClasses(ctx: ParseTree, depth: Int, scopes: ParseTreeScopeAnnotations): Unit = {

    for (i <- 0 until ctx.getChildCount) {
      val child = ctx.getChild(i)

      val sw = new StringWriter()
      val pw = new PrintWriter(sw)

      // If there are children then print an indent string using '-' character.
      val ch = if (child.getChildCount > 0) '-' else ' '
      for (j <- 0 until depth) pw.print(ch)
      pw.print("> ")

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
        pw.print(parentNameField.get.getName + " = ")

      if (child.isInstanceOf[TerminalNode])
        pw.print(child.getText)
      else
        pw.print(childClassName)

      if (!child.isInstanceOf[TerminalNode] && childFields.nonEmpty)
        pw.print(" [" + childFields.map(_.getName).mkString(" | ") + "]")

      pw.flush()

      val len = sw.getBuffer.length()
      val space = " " * (110 - len)

      scopes.get(child) match {
        case Some(scope: CollectionMemberScope) =>
          pw.print(s"${space}Collection ${scope.collectionSymbol}" /*+ scope.map.keys.mkString("[", " ", "]")*/)
        case Some(other) =>
          pw.print(s"$space${other.getClass.getSimpleName}" /*+ scope.map.keys.mkString("[", " ", "]")*/)
        case None =>
      }

      pw.flush()
      println(sw.getBuffer)
      printTreeClasses(child, depth + 2, scopes)
    }
  }

  def usage(msg: String): Unit = {
    System.err.println(s"Usage Error: $msg")
    System.err.println()
    """
      |Usage: CodeGenerator [-java | -scala] [-groupfile] [-outputpath] (rulefile)+
    """.stripMargin

    System.exit(1)
  }
}




