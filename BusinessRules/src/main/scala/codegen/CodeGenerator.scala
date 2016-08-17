package codegen

import java.io.{File, PrintStream, PrintWriter, StringWriter}

import codegen.symbols.SymbolTableBuilder
import org.antlr.v4.runtime.tree.{ParseTree, ParseTreeWalker}
import org.antlr.v4.runtime.{ANTLRFileStream, CommonTokenStream}
import rules.BusinessRulesParser._
import rules.{BusinessRulesLexer, BusinessRulesParser}

import scala.collection.mutable.ArrayBuffer

object CodeGenerator {

  var outputTarget = "Scala"
  var templateGroupFile = "Scala"
  var outputPath = "target/generated/rules/compiled"
  var packageName = "rules.compiled"
  var templateDir = "src/main/templates"
  var outputClass = "RulePlanExample"
  var generateCode = true

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
        case "-noCodeGeneration" :: tail =>
          generateCode = false
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

    // Parse Tree is build here starting at entry point called "fileBody" in the grammar.
    val tree: FileBodyContext = parser.fileBody()

    // A tree walker will be reused several times to walk multiple phases.
    val walker = new ParseTreeWalker()

    // Gather the models referenced in the input NRL
    val modelPhase = new ModelBuilderPhase()
    walker.walk(modelPhase, tree)

    // Build the symbol table with every declaration of a symbol.
    // The builder is passed to the Declaration Phase
    val builder = new SymbolTableBuilder(modelPhase.model, false)

    // The Declaration Phase gathers all symbol declarations
    val declarationPhase = new DeclarationPhase(builder)
    walker.walk(declarationPhase, tree)

    // The Resolution Phase resolves all model references in the source rules.
    val resolver = new ResolutionPhase(builder.symbolTable, declarationPhase.annotator)
    walker.walk(resolver, tree)

    // Print the whole parse tree for diagnostics
    printParseTree(tree, declarationPhase.annotator, System.err)

    // Print the symbol table for diagnostics
    builder.symbolTable.print()

    // Finally, create a generator and generate the source code for this input source.
    val generator = new ScalaGenerator(tree, "rules.compiled", "GeneratedClass", outputDir)
    generator.genScalaClass()

  }

  def usage(msg: String): Unit = {
    System.err.println(s"Usage Error: $msg")
    System.err.println()
    """
      |Usage: CodeGenerator [-java | -scala] [-groupfile] [-outputpath] (rulefile)+
    """.stripMargin

    System.exit(1)
  }

  def printParseTree(tree: ParseTree, annotations: ParseTreeScopeAnnotations, ostrm: PrintStream): Unit = {
    val sw = new StringWriter()
    val strm = new PrintWriter(sw)
    val printer = new TreePrinter(0, annotations, strm)
    new ParseTreeWalker().walk(printer, tree)
    strm.flush()
    ostrm.println()
    ostrm.println(sw.getBuffer)
    ostrm.println("=" * 80)
    ostrm.flush()
  }

}




