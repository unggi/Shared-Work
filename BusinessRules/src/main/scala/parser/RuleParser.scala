package parser

import java.io.File

import codegen.JavaTargetListener
import org.antlr.v4.runtime.{CommonTokenStream, ANTLRFileStream}
import rules.BusinessRulesParser.FileBodyContext
import rules.{BusinessRulesParser, BusinessRulesLexer}

/**
 * Created by usoemard on 9/21/2015.
 */
class RuleParser() {
  def process(fileName: String): Unit = {
    println(s"Processing input file <$fileName>")
    val file = new File(fileName)
    //if (!file.exists())
    //  usage(s"File not found for NRL input source - ${file.getAbsoluteFile}")

    val input = new ANTLRFileStream(file.getAbsolutePath)

    val lexer = new BusinessRulesLexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new BusinessRulesParser(tokens)

    parser.setBuildParseTree(true)
    val t: FileBodyContext = parser.fileBody

    println("Parsing is complete")
  }

}
