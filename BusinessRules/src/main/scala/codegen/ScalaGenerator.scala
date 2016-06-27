package codegen

import java.io.{File, FileOutputStream, PrintWriter}
import java.rmi.UnexpectedException

import codegen.symbols.{Parameter, ParameterReference, Symbol}
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import rules.BusinessRulesParser
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

    emit(apply(definitions)(genDefinition(_)))

    // Collect Validation Rules
    val rules = declarations.flatMap {
      case decl: DeclarationContext if decl.validationRule() != null => Some(decl.validationRule())
      case otherwise => None
    }

    emit(apply(rules)(genRule(_)))
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
       |    def evaluate(${genParameterDeclarations(defn.parameterDeclarations)}): Boolean =
       |        ${genPredicate(defn.value)}
    """
  }

  def genRule(rule: ValidationRuleContext) = template {
    s"""
       |class ${id(rule.name.getText)} (${rule.name.getText}) extends ValidationRule {
       |  def evaluate(${genParameterDeclaration(rule.context.parameterDeclaration())}): Boolean =
       |    ${genConstraint(rule.constraint)}
       |
     """

  }

  def genParameterDeclarations(params: ParameterDeclarationsContext): String =
    apply(params, classOf[ParameterDeclarationContext], genParameterDeclaration)


  def genParameterDeclaration(param: ParameterDeclarationContext) = alternates(param.ref.symbol) {
    case prm: Parameter =>
      s"${unquote(param.alias.getText)}: ${prm.classifier}"
    case otherwise =>
      throw new IllegalArgumentException(s"Expected a Parameter symbol but found ${otherwise}")
  }


  def genModelReference(ref: ModelReferenceContext): String =
    ref.symbol match {
//      case Parameter(name, classifer) =>
//        s"${name}.${pathComponents(ref).tail.mkString(".")}"
      case ParameterReference(param, components) =>
        System.err.println(s"""PARAM REFERENCE ${components.mkString(".")}""")
        s" REF ${param.asComponents.mkString(".")}"
      //case ParameterReference(param, components) => s" REF ${param.name}.${param.asComponents.tail.mkString(".")}"
      case otherwise =>
        throw new UnexpectedException(s"Symbol Type ${otherwise} is not handled.")
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

  def genIsOneOfPredicate(pred: IsOneOfPredicateContext) = tbd(pred)

  def genIsNotOneOfPredicate(pred: IsNotOneOfPredicateContext) = tbd(pred)

  def genIsKindOfPredicate(pred: IsKindOfPredicateContext) = tbd(pred)

  def genUnaryExpressionPredicate(pred: UnaryExpressionPredicateContext) =
    required(pred.expression, genExpression)

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
    case t: FunctionalExpressionTermContext => genFunctionalExpressionTerm(t)
    case t: DefinedTermReferenceTermContext => genModelReference(t.modelReference()) + t.FragmentName().getText
    case t: OperatorInvocationTermContext => tbd(t)
    case t: DefinitionApplicationTermContext => tbd(t)
    case t: CastExpressionTermContext => tbd(t)
    case t: SelectionExpressionTermContext => tbd(t)
    case t: ConstraintTermContext => tbd(t)
    case t: IdentifierTermContext => genIdentifierTerm(t)
    case other => s"Unknown term type ${other}"
  }

  def genIdentifierTerm(id: IdentifierTermContext) = template {
    genIdentifier(id.identifier())
  }

  def genFunctionalExpressionTerm(fexpr: FunctionalExpressionTermContext) =
    required(fexpr.functionalExpression, genFunctionalExpression)

  def genNumberOfExpression(expr: NumberOfExpressionContext) =
    required(expr.ref, genModelReference)

  def genFunctionalExpression(ctx: FunctionalExpressionContext) = alternates(ctx) {
    case expr: SumOfExpressionContext => tbd(expr)
    case expr: NumberOfExpressionContext => genNumberOfExpression(expr)
    case expr: NumberOfUniqueExpressionContext => tbd(expr)
    case other => s"Unknown functional expression type ${other}"
  }

  def genIdentifier(identifier: IdentifierContext) = alternates(identifier) {
    case id: ModelReferenceIdentifierContext => genModelReference(id.modelReference())
    case id: LiteralStringIdentifierContext => id.LiteralString.getSymbol.getText
    case id: NumberIdentifierContext => id.Number.getText
    case id: IntegerNumberIdentifierContext => id.IntegerNumber.getSymbol.getText
    case id: BooleanLiteralIdentifierContext => id.BooleanLiteral.getSymbol.getText
    //case id: CollectionIndexContext => genModelReference(id.modelReference())
    case id: DoubleQuotedStringIdentifierContext => id.getText
  }

  def genConstraint(ctx: ConstraintContext) = template {
    if (ctx.ifConstraint != null)
      genIfConstraint(ctx.ifConstraint)
    else
      genLogicalStatement(ctx.logicalStatement)
  }

  def genIfConstraint(ctx: IfConstraintContext): String =
    required(ctx.condBlock, genLogicalStatement) + required(ctx.thenBlock, genLogicalStatement) + optional(ctx.elseBlock, genLogicalStatement)

  val EMPTY = "<<EMPTY>>"

  def genBinaryLogicalOperator(ctx: BinaryLogicalOperatorContext) = template {
    if (ctx.and != null) " && "
    else if (ctx.or != null) " || "
    else if (ctx.implies != null) "=>"
    else if (ctx.iff != null) "iff"
    else s"Invalid Binrary Logical Operator: text is ${ctx.getText}"
  }

  def genBinaryLogicalOperatorStatement(stmt: BinaryLogicalOperatorStatementContext): String =
    required(stmt.left, genLogicalStatement) + required(stmt.op, genBinaryLogicalOperator) + required(stmt.right, genLogicalStatement)

  def genLogicalPredicateStatement(stmt: LogicalPredicateStatementContext): String =
    required(stmt.predicate, genPredicate)

  def genExistsStatement(ctx: ExistsStatementContext) = tbd(ctx)

  def genNotExistsStatement(ctx: NotExistsStatementContext) = tbd(ctx)


  def genLogicalExistsStatement(stmt: LogicalExistsStatementContext): String =
    required(stmt.existsStatement, genExistsStatement)

  def genLogicalNotExistsStatement(stmt: LogicalNotExistsStatementContext) =
    required(stmt.notExistsStatement, genNotExistsStatement)


  def genForAllStatement(ctx: ForallStatementContext) = tbd(ctx)

  def genLogicalForAllStatement(stmt: LogicalForAllStatementContext) =
    required(stmt.forallStatement, genForAllStatement)


  def genLogicalStatement(ctx: LogicalStatementContext) = alternates(ctx) {
    case stmt: BinaryLogicalOperatorStatementContext => genBinaryLogicalOperatorStatement(stmt)
    case stmt: LogicalPredicateStatementContext => genLogicalPredicateStatement(stmt)
    case stmt: LogicalExistsStatementContext => genLogicalExistsStatement(stmt)
    case stmt: LogicalNotExistsStatementContext => genLogicalNotExistsStatement(stmt)
    case stmt: LogicalForAllStatementContext => genLogicalForAllStatement(stmt)
    case other => s"Unknown logical statement type $other"
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

