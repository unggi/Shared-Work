package codegen

import java.io.{File, FileOutputStream, PrintWriter}
import java.rmi.UnexpectedException

import codegen.symbols.{Parameter, ParameterReference, Symbol}
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import rules.BusinessRulesParser._

import scala.collection._


//
// Generate code from Scala strings and interpolated variables.
//

class ScalaGenerator(fileBodyContext: FileBodyContext, packageName: String, className: String, outputPath: File) {

  import scala.collection.JavaConversions._
  import StringFormatter._
  import TreeUtilities._

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

  def genScalaClassBody() = {

    val declarations: List[DeclarationContext] = fileBodyContext.declarations().declaration().toList

    // Collect Definitions
    val definitions = declarations.flatMap {
      case decl: DeclarationContext if decl.definition != null => Some(decl.definition)
      case otherwise => None
    }

    emit (apply(definitions)(genDefinition(_)))


    // Collect Validation Rules
    val rules = declarations.flatMap {
      case decl: DeclarationContext if decl.validationRule() != null => Some(decl.validationRule())
      case otherwise => None
    }

    emit (apply(rules)(genRule(_)))
  }

  def genScalaClassFooter() = emit {
    s"""
       |}
       |
       |
    """
  }

  def genDefinition(defn: DefinitionContext) = template {
    require(defn != null, "Definition is null")
    require(defn.name != null)
    require(defn.parameterDeclarations() != null)
    require(defn.value != null)
    s"""
       |class ${id(defn.name.getText)}(${defn.name.getText}) extends DefinedTerm {
       |    def evaluate(${genParameterDeclarations(defn.parameterDeclarations)}): Boolean =
       |        ${genPredicate(defn.value)}
    """
  }

  def genRule(rule: ValidationRuleContext) = template {
    s"""
       |class ${id(rule.name.getText)} ($rule.name.getText}) extends ValidationRule {
       |  def evaluate(${genParameterDeclaration(rule.context.parameterDeclaration())}: Boolean =
       |    ${genConstraint(rule.constraint)}
       |
     """

  }

  def genParameterDeclarations(params: ParameterDeclarationsContext): String = template {
    apply(params, classOf[ParameterDeclarationContext]) {
      genParameterDeclaration(_)
    }
  }

  def genParameterDeclaration(param: ParameterDeclarationContext) = template {
    require(param != null)
    require(param.modelReference != null)
    require(param.ref.symbol != null)
    val classifier = param.ref.symbol match {
      case prm: Parameter => prm.classifier
      case otherwise => throw new IllegalArgumentException(s"Expected a Parameter symbol but found ${otherwise}")
    }
    s"${unquote(param.alias.getText)}: $classifier"
  }

  def genConstraint(constraintContext: ConstraintContext) = template {
    "Constraint Body"
  }

  def genModelReference(ref: ModelReferenceContext): String =
    ref.symbol match {
      case Parameter(name, classifer) => s"${name}.${pathComponents(ref).tail.mkString(".")}"
      case otherwise => throw new UnexpectedException(s"Symbol Type ${otherwise} is not handled.")
    }

  def genPredicate(value: PredicateContext) = alternates(value) {
    case pred: BinaryPredicateContext =>
      s"${genExpression(pred.left)} ${genComparator(pred.comparator())} ${genExpression(pred.right)}"
    case pred: IsOneOfPredicateContext => genIsOneOfPredicate(pred)
    case pred: IsNotOneOfPredicateContext => genIsNotOneOfPredicate(pred)
    case pred: IsKindOfPredicateContext => genIsKindOfPredicate(pred)
    case pred: UnaryExpressionPredicateContext => genUnaryExpressionPredicate(pred)
    case other => s"Unknown predicate type ${other}"
  }

  def genIsOneOfPredicate(pred: IsOneOfPredicateContext) = template {
    throw new NotImplementedError()
    "Not Complete - IsOneOfPredicate"
  }

  def genIsNotOneOfPredicate(pred: IsNotOneOfPredicateContext) = template {
    throw new NotImplementedError()
    "2"
  }

  def genIsKindOfPredicate(pred: IsKindOfPredicateContext) = template {
    throw new NotImplementedError()
    "3"
  }

  def genUnaryExpressionPredicate(pred: UnaryExpressionPredicateContext) = template {
    throw new NotImplementedError()
    "4"
  }

  def genExpression(expr: ExpressionContext): String = alternates(expr) {
    case binary: BinaryExpressionContext => genExpression(binary.left) + binary.op.getText + genExpression(binary.right)
    case unary: UnaryExpressionContext => genTerm(unary.term())
    case other => s"Unknown expression type ${other}"
  }

  def genComparator(expr: ComparatorContext) = alternates(expr) {
    case c: IsEqualToComparatorContext => " == "
    case c: IsNotEqualToComparatorContext => "!="
    case c: IsGreaterThanComparatorContext => ">"
    case c: IsGreaterThanOrEqualToComparatorContext => ">="
    case c: IsLessThanOrEqualToComparatorContext => "<="
    case c: IsLessThanComparatorContext => "<"
    case otherwise => s"Unknown Comparator ${expr}"
  }

  def genTerm(term: TermContext): String = alternates(term) {
    case t: FunctionalExpressionTermContext => genFunctionalExpression(t)
    case t: DefinedTermReferenceTermContext => genModelReference(t.modelReference()) + t.FragmentName().getText
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
    case id: DoubleQuotedStringIdentifierContext => id.getText
  }


}
