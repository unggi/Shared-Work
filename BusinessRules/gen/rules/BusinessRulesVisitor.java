// Generated from /Users/unggi/Development/Shared-Work/BusinessRules/src/main/antlr/BusinessRules.g4 by ANTLR 4.5.1
package rules;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BusinessRulesParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BusinessRulesVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#fileBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFileBody(BusinessRulesParser.FileBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#modelFileReferences}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelFileReferences(BusinessRulesParser.ModelFileReferencesContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#modelFileReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelFileReference(BusinessRulesParser.ModelFileReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#declarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarations(BusinessRulesParser.DeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(BusinessRulesParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#validationRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValidationRule(BusinessRulesParser.ValidationRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinition(BusinessRulesParser.DefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#ruleSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleSet(BusinessRulesParser.RuleSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#context}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContext(BusinessRulesParser.ContextContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#multipleParameterContext}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultipleParameterContext(BusinessRulesParser.MultipleParameterContextContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#multipleContextParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultipleContextParameter(BusinessRulesParser.MultipleContextParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#modelReferenceWithAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelReferenceWithAlias(BusinessRulesParser.ModelReferenceWithAliasContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#constraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraint(BusinessRulesParser.ConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#binaryLogicalOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryLogicalOperator(BusinessRulesParser.BinaryLogicalOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalPredicateStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalPredicateStatement(BusinessRulesParser.LogicalPredicateStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalNotExistsStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalNotExistsStatement(BusinessRulesParser.LogicalNotExistsStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalExistsStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExistsStatement(BusinessRulesParser.LogicalExistsStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalForAllStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalForAllStatement(BusinessRulesParser.LogicalForAllStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryLogicalOperatorStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryLogicalOperatorStatement(BusinessRulesParser.BinaryLogicalOperatorStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#simpleOrComplexConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleOrComplexConstraint(BusinessRulesParser.SimpleOrComplexConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryPredicate(BusinessRulesParser.BinaryPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsOneOfPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsOneOfPredicate(BusinessRulesParser.IsOneOfPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsNotOneOfPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNotOneOfPredicate(BusinessRulesParser.IsNotOneOfPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsKindOfPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsKindOfPredicate(BusinessRulesParser.IsKindOfPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsEqualToComparator(BusinessRulesParser.IsEqualToComparatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsNotEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNotEqualToComparator(BusinessRulesParser.IsNotEqualToComparatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsGreaterThanComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsGreaterThanComparator(BusinessRulesParser.IsGreaterThanComparatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsGreaterThanOrEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsGreaterThanOrEqualToComparator(BusinessRulesParser.IsGreaterThanOrEqualToComparatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsLessThanOrEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsLessThanOrEqualToComparator(BusinessRulesParser.IsLessThanOrEqualToComparatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsLessThanComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsLessThanComparator(BusinessRulesParser.IsLessThanComparatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#listDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListDefinition(BusinessRulesParser.ListDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#multipleExistsStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultipleExistsStatement(BusinessRulesParser.MultipleExistsStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#multipleNotExistsStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultipleNotExistsStatement(BusinessRulesParser.MultipleNotExistsStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExpression}
	 * labeled alternative in {@link BusinessRulesParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpression(BusinessRulesParser.BinaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link BusinessRulesParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(BusinessRulesParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierTerm(BusinessRulesParser.IdentifierTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionalExpressionTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionalExpressionTerm(BusinessRulesParser.FunctionalExpressionTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ModelReferenceTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelReferenceTerm(BusinessRulesParser.ModelReferenceTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OperatorInvocationTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorInvocationTerm(BusinessRulesParser.OperatorInvocationTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DefinitionApplicatoinTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinitionApplicatoinTerm(BusinessRulesParser.DefinitionApplicatoinTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CastExpressionTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpressionTerm(BusinessRulesParser.CastExpressionTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SelectionExpressionTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectionExpressionTerm(BusinessRulesParser.SelectionExpressionTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstraintTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintTerm(BusinessRulesParser.ConstraintTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ModelReferenceIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelReferenceIdentifier(BusinessRulesParser.ModelReferenceIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralStringIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralStringIdentifier(BusinessRulesParser.LiteralStringIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberIdentifier(BusinessRulesParser.NumberIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntegerNumberIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerNumberIdentifier(BusinessRulesParser.IntegerNumberIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanLiteralIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteralIdentifier(BusinessRulesParser.BooleanLiteralIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CollectionIndexIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollectionIndexIdentifier(BusinessRulesParser.CollectionIndexIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DoubleQuotedStringIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleQuotedStringIdentifier(BusinessRulesParser.DoubleQuotedStringIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SumOfExpression}
	 * labeled alternative in {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumOfExpression(BusinessRulesParser.SumOfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberOfExpression}
	 * labeled alternative in {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberOfExpression(BusinessRulesParser.NumberOfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberOfUniqueExpression}
	 * labeled alternative in {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberOfUniqueExpression(BusinessRulesParser.NumberOfUniqueExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#operatorInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorInvocation(BusinessRulesParser.OperatorInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#operatorParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorParameterList(BusinessRulesParser.OperatorParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#definitionApplication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinitionApplication(BusinessRulesParser.DefinitionApplicationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#collectionIndex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollectionIndex(BusinessRulesParser.CollectionIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(BusinessRulesParser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#selectionExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectionExpression(BusinessRulesParser.SelectionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#modelReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelReference(BusinessRulesParser.ModelReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#modelReferenceList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelReferenceList(BusinessRulesParser.ModelReferenceListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#dottedModelPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDottedModelPath(BusinessRulesParser.DottedModelPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#propertyOfModelPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyOfModelPath(BusinessRulesParser.PropertyOfModelPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#compoundReport}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundReport(BusinessRulesParser.CompoundReportContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#simpleReport}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleReport(BusinessRulesParser.SimpleReportContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#concatenatedReport}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcatenatedReport(BusinessRulesParser.ConcatenatedReportContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#conditionalReport}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalReport(BusinessRulesParser.ConditionalReportContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#simpleTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTerm(BusinessRulesParser.SimpleTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ModelReferenceExists}
	 * labeled alternative in {@link BusinessRulesParser#existsStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelReferenceExists(BusinessRulesParser.ModelReferenceExistsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SimpleExists}
	 * labeled alternative in {@link BusinessRulesParser#existsStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExists(BusinessRulesParser.SimpleExistsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#enumerator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumerator(BusinessRulesParser.EnumeratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#notExistsStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExistsStatement(BusinessRulesParser.NotExistsStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#forallStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForallStatement(BusinessRulesParser.ForallStatementContext ctx);
}