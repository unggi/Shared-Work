package codegen

import java.io.{File, FileOutputStream, PrintWriter}

import codegen.symbols.ParameterReference
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import rules.BusinessRulesParser._
import codegen.symbols.Symbol

import scala.collection._


//
// Generate code from Scala strings and interpolated variables.
//

class ScalaGenerator(fileBodyContext: FileBodyContext, packageName: String, className: String, outputPath: File) {

  import scala.collection.JavaConversions._
  import StringFormatter._

  val verbose = false

  require(outputPath.isDirectory, s"Parameter outputPath does not contain a directory: outputPath = <$outputPath>")

  val ostrm = new FileOutputStream(outputPath.getCanonicalPath + File.separator + className + ".scala")
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


  def template(text: => String): String =
    if (verbose) {
      val top = Thread.currentThread.getStackTrace
      "\n>>> " + top(2) + "[\n" + text.stripMargin('|').trim + "\n]<<< " + top(2)
    }
    else
      text.stripMargin('|')

  def alternates(rule: ParserRuleContext)(matcher: (ParserRuleContext) => String): String =
    matcher(rule)

  def genScalaClass(): Unit = {

    genScalaClassHeader()
    genScalaClassBody()
    genScalaClassFooter()

    pw.flush()
    ostrm.close()


  }

  def genScalaClassHeader() = emit {
    s"""package $packageName
        |
       |class ${id(className)} {
        |
       |"""
  }

  def genScalaClassBody() = emit {
    // Collect Definitions
    val definitions =
      query(fileBodyContext.declarations()) {
        case decl: DeclarationContext => Option(decl.definition())
        case otherwise => None
      }

    definitions.foreach(genDefinition(_))

    apply(fileBodyContext.declarations, classOf[DeclarationContext]) {
      case decl: DeclarationContext => genDefinition(decl.definition)
      case otherwise => ""
    }

    // Collect Validation Rules

  }

  def genScalaClassFooter() = emit {
    s"""
       |}
       |
       |
    """
  }

  def genDefinition(defn: DefinitionContext) = template {
    s"""
       |class ${id(defn.name.getText)}(${defn.name.getText}) extends DefinedTerm {
       |    def evaluate(${genInputParameterList(defn.multipleContextParameter)}): Boolean = {
       |        ${genPredicate(defn.value)}
       |}
    """
  }

  def genInputParameterList(params: MultipleContextParameterContext): String = template {
    apply(params, classOf[ModelReferenceParameterContext]) {
      ref =>
        genModelReferenceParameter(ref)
    }
  }

  def genModelReferenceParameter(param: ModelReferenceParameterContext) = template {
    s"${unquote(param.alias.getText)}: ${genModelReference(param.ref)}"
  }

  def genModelReference(ref: ModelReferenceContext): String =
    ref.symbol match {
      case s: ParameterReference => s"${s.components.head}"
      case s: Symbol => s.name
    }

  def genPredicate(value: PredicateContext) = alternates(value) {
    case pred: BinaryPredicateContext =>
      genExpression(pred.left) + genComparator(pred.comparator()) + genExpression(pred.right)
    case pred: IsOneOfPredicateContext => genIsOneOfPredicate(pred)
    case pred: IsNotOneOfPredicateContext => genIsNotOneOfPredicate(pred)
    case pred: IsKindOfPredicateContext => genIsKindOfPredicate(pred)
    case pred: UnaryExpressionPredicateContext => genUnaryExpressionPredicate(pred)
    case other => s"Unknown predicate type ${other}"
  }


  def genIsOneOfPredicate(pred: IsOneOfPredicateContext) = template {
    "1"
  }

  def genIsNotOneOfPredicate(pred: IsNotOneOfPredicateContext) = template {
    "2"
  }

  def genIsKindOfPredicate(pred: IsKindOfPredicateContext) = template {
    "3"
  }

  def genUnaryExpressionPredicate(pred: UnaryExpressionPredicateContext) = template {
    "4"
  }

  def genExpression(expr: ExpressionContext): String = alternates(expr) {
    case binary: BinaryExpressionContext => genExpression(binary.left) + binary.op.getText + genExpression(binary.right)
    case unary: UnaryExpressionContext => genTerm(unary.term())
    case other => s"Unknown expression type ${other}"

  }

  def genComparator(expr: ComparatorContext) = template {
    ""
  }

  def genTerm(term: TermContext): String = alternates(term) {
    case t: FunctionalExpressionTermContext => genFunctionalExpression(t)
    case t: DefinedTermReferenceTermContext => ""
    case t: OperatorInvocationTermContext => ""
    case t: DefinitionApplicationTermContext => ""
    case t: CastExpressionTermContext => ""
    case t: SelectionExpressionTermContext => ""
    case t: ConstraintTermContext => ""
    case t: IdentifierTermContext => genIdentifierTerm(t)
    case other => s"Unknown term type ${other}"
  }

  def genIdentifierTerm(id: IdentifierTermContext): String =
    genIdentifier(id.identifier())


  def genFunctionalExpression(fexpr: FunctionalExpressionTermContext): String = template {
    "f"
  }

  def genIdentifier(identifier: IdentifierContext) = alternates(identifier) {
    case id: ModelReferenceIdentifierContext => genModelReference(id.modelReference())
    case id: LiteralStringIdentifierContext => id.LiteralString.getSymbol.getText
    case id: NumberIdentifierContext => id.Number.getText
    case id: IntegerNumberIdentifierContext => id.IntegerNumber.getSymbol.getText
    case id: BooleanLiteralIdentifierContext => id.BooleanLiteral.getSymbol.getText
    case id: CollectionIndexContext => genModelReference(id.modelReference())
    case id: DoubleQuotedStringIdentifierContext => ""
  }

  def find[T <: ParserRuleContext](ctx: ParserRuleContext, cls: Class[T]): immutable.List[T] =
    ctx.getRuleContexts(cls).toList

  def query[T <: ParserRuleContext](ctx: ParserRuleContext)(mapper: (ParserRuleContext) => Option[T]): immutable.List[T] =
    find(ctx, classOf[ParserRuleContext]).flatMap(mapper(_))

  def apply[P <: ParserRuleContext, C <: ParserRuleContext](ctx: P, cls: Class[C])(body: (C) => String): String =
    ctx.getRuleContexts(cls).toList.foldLeft("")(_ + body(_))

}
