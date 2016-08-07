package codegen

import java.io.{File, FileOutputStream, PrintWriter}
import java.rmi.UnexpectedException

import codegen.symbols._
import rules.BusinessRulesParser._

//
// Generate code from Scala strings and interpolated variables.
//

class ScalaGenerator(fileBodyContext: FileBodyContext, packageName: String, className: String, outputPath: File)
  extends TemplateSupport(outputPath, className, ".scala") {

  import scala.collection.JavaConversions._
  import StringFormatter._
  import TreeUtilities._


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
       |"""
  }

  def genDefinition(defn: DefinitionContext) = template {
    s"""
       |  class ${id(defn.name.getText)}(${defn.name.getText}) extends DefinedTerm {
       |    def evaluate(${genParameterDeclarations(defn.parameterDeclarations)}): Boolean =
       |        ${genPredicate(defn.value)}
    """
  }

  def genRule(rule: ValidationRuleContext) = template {
    s"""
       |  class ${id(rule.name.getText)} (${rule.name.getText}) extends ValidationRule {
       |    def evaluate(${genParameterDeclaration(rule.context.parameterDeclaration())}): Boolean =
       |      ${genConstraint(rule.constraint)}
       |
     """
  }

  def genParameterDeclarations(params: ParameterDeclarationsContext): String =
    apply(params, classOf[ParameterDeclarationContext], genParameterDeclaration)

  def genParameterDeclaration(param: ParameterDeclarationContext) = alternates(param.symbol) {
    case prm: Parameter =>
      s"${unquote(param.alias.getText)}: ${prm.classifier.name}"
    case otherwise =>
      throw new IllegalArgumentException(s"Expected a Parameter symbol but found ${otherwise}")
  }

  def genModelReference(ref: ModelReferenceContext): String =
    ref.symbol match {
      case ParameterReference(param, components) =>
        s"${components.mkString(".")}"
      case ref: CollectionIndexReference =>
        s"""${ref.asComponents.mkString(".")}"""
      case otherwise =>
        failure(s"Symbol Type ${otherwise} is not handled.")
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

  def genIsKindOfPredicate(pred: IsKindOfPredicateContext) = template {
    s"""${genModelReference(pred.ref)}.isInstanceOf[${pred.classifier.getText}]"""
  }

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
    case t: DefinedTermReferenceTermContext => id(t.FragmentName().getText.stripPrefix("<<").stripSuffix(">>")) + "(" + genModelReference(t.modelReference()) + ")"
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
    required(expr.ref, genModelReference) + ".count"

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

  def genIfConstraint(ctx: IfConstraintContext) = template {
    s"""if (${required(ctx.condBlock, genLogicalStatement)}) {
            ${required(ctx.thenBlock, genLogicalStatement)}
        }""" +
      optional(ctx.elseBlock, {
        a: LogicalStatementContext =>
          s"""else {
          ${genLogicalStatement(a)}
          }
            """
      })
  }

  val EMPTY = "<<EMPTY>>"

  def genBinaryLogicalOperator(ctx: BinaryLogicalOperatorContext) = template {
    if (ctx.and != null) " && "
    else if (ctx.or != null) " || "
    else if (ctx.implies != null) "=>"
    else if (ctx.iff != null) "iff"
    else s"Invalid Binary Logical Operator: text is ${ctx.getText}"
  }

  def genBinaryLogicalOperatorStatement(stmt: BinaryLogicalOperatorStatementContext): String =
    required(stmt.left, genLogicalStatement) + required(stmt.op, genBinaryLogicalOperator) + required(stmt.right, genLogicalStatement)

  def genLogicalPredicateStatement(stmt: LogicalPredicateStatementContext): String =
    required(stmt.predicate, genPredicate)

  def genNotExistsStatement(ctx: NotExistsStatementContext) = tbd(ctx)

  def genLogicalExistsStatement(stmt: LogicalExistsStatementContext): String = alternates(stmt.existsStatement) {
    case e: ConstrainedCollectionMembershipContext => genConstrainedCollectionMembership(e)
    case e: SimpleExistsContext => tbd(e)
    case otherwise => System.err.println(s"Invalid type in match => $otherwise")
      ""
  }

  def genConstrainedCollectionMembership(stmt: ConstrainedCollectionMembershipContext) = template {
    val collection = stmt.collectionRef.symbol

    s"""${genModelReference(stmt.collectionRef)}.count(${genCollectionMemberConstraint(stmt.collectionMemberConstraint())})"""
  }

  def genCollectionMemberConstraint(cons: CollectionMemberConstraintContext) =
    required(cons.simpleOrComplexConstraint(), genSimpleOrComplexConstraint)

  def genSimpleOrComplexConstraint(constraint: SimpleOrComplexConstraintContext) = template {
    if (constraint.constraint != null)
      genConstraint(constraint.constraint())
    else
      genPredicate(constraint.predicate())
  }

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
}

