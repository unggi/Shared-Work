// Generated from /Users/unggi/Development/Shared-Work/BusinessRules/src/main/antlr/BusinessRules.g4 by ANTLR 4.5.1
package rules;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BusinessRulesParser}.
 */
public interface BusinessRulesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#fileBody}.
	 * @param ctx the parse tree
	 */
	void enterFileBody(BusinessRulesParser.FileBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#fileBody}.
	 * @param ctx the parse tree
	 */
	void exitFileBody(BusinessRulesParser.FileBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#modelFileReferences}.
	 * @param ctx the parse tree
	 */
	void enterModelFileReferences(BusinessRulesParser.ModelFileReferencesContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#modelFileReferences}.
	 * @param ctx the parse tree
	 */
	void exitModelFileReferences(BusinessRulesParser.ModelFileReferencesContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#modelFileReference}.
	 * @param ctx the parse tree
	 */
	void enterModelFileReference(BusinessRulesParser.ModelFileReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#modelFileReference}.
	 * @param ctx the parse tree
	 */
	void exitModelFileReference(BusinessRulesParser.ModelFileReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#declarations}.
	 * @param ctx the parse tree
	 */
	void enterDeclarations(BusinessRulesParser.DeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#declarations}.
	 * @param ctx the parse tree
	 */
	void exitDeclarations(BusinessRulesParser.DeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(BusinessRulesParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(BusinessRulesParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#validationRule}.
	 * @param ctx the parse tree
	 */
	void enterValidationRule(BusinessRulesParser.ValidationRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#validationRule}.
	 * @param ctx the parse tree
	 */
	void exitValidationRule(BusinessRulesParser.ValidationRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterDefinition(BusinessRulesParser.DefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitDefinition(BusinessRulesParser.DefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#ruleSet}.
	 * @param ctx the parse tree
	 */
	void enterRuleSet(BusinessRulesParser.RuleSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#ruleSet}.
	 * @param ctx the parse tree
	 */
	void exitRuleSet(BusinessRulesParser.RuleSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#context}.
	 * @param ctx the parse tree
	 */
	void enterContext(BusinessRulesParser.ContextContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#context}.
	 * @param ctx the parse tree
	 */
	void exitContext(BusinessRulesParser.ContextContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#multipleContextParameter}.
	 * @param ctx the parse tree
	 */
	void enterMultipleContextParameter(BusinessRulesParser.MultipleContextParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#multipleContextParameter}.
	 * @param ctx the parse tree
	 */
	void exitMultipleContextParameter(BusinessRulesParser.MultipleContextParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#modelReferenceParameter}.
	 * @param ctx the parse tree
	 */
	void enterModelReferenceParameter(BusinessRulesParser.ModelReferenceParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#modelReferenceParameter}.
	 * @param ctx the parse tree
	 */
	void exitModelReferenceParameter(BusinessRulesParser.ModelReferenceParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterConstraint(BusinessRulesParser.ConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitConstraint(BusinessRulesParser.ConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#binaryLogicalOperator}.
	 * @param ctx the parse tree
	 */
	void enterBinaryLogicalOperator(BusinessRulesParser.BinaryLogicalOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#binaryLogicalOperator}.
	 * @param ctx the parse tree
	 */
	void exitBinaryLogicalOperator(BusinessRulesParser.BinaryLogicalOperatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicalPredicateStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void enterLogicalPredicateStatement(BusinessRulesParser.LogicalPredicateStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalPredicateStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void exitLogicalPredicateStatement(BusinessRulesParser.LogicalPredicateStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicalNotExistsStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void enterLogicalNotExistsStatement(BusinessRulesParser.LogicalNotExistsStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalNotExistsStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void exitLogicalNotExistsStatement(BusinessRulesParser.LogicalNotExistsStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicalExistsStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExistsStatement(BusinessRulesParser.LogicalExistsStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalExistsStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExistsStatement(BusinessRulesParser.LogicalExistsStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicalForAllStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void enterLogicalForAllStatement(BusinessRulesParser.LogicalForAllStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalForAllStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void exitLogicalForAllStatement(BusinessRulesParser.LogicalForAllStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryLogicalOperatorStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void enterBinaryLogicalOperatorStatement(BusinessRulesParser.BinaryLogicalOperatorStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryLogicalOperatorStatement}
	 * labeled alternative in {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void exitBinaryLogicalOperatorStatement(BusinessRulesParser.BinaryLogicalOperatorStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#simpleOrComplexConstraint}.
	 * @param ctx the parse tree
	 */
	void enterSimpleOrComplexConstraint(BusinessRulesParser.SimpleOrComplexConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#simpleOrComplexConstraint}.
	 * @param ctx the parse tree
	 */
	void exitSimpleOrComplexConstraint(BusinessRulesParser.SimpleOrComplexConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterBinaryPredicate(BusinessRulesParser.BinaryPredicateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitBinaryPredicate(BusinessRulesParser.BinaryPredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsOneOfPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterIsOneOfPredicate(BusinessRulesParser.IsOneOfPredicateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsOneOfPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitIsOneOfPredicate(BusinessRulesParser.IsOneOfPredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsNotOneOfPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterIsNotOneOfPredicate(BusinessRulesParser.IsNotOneOfPredicateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsNotOneOfPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitIsNotOneOfPredicate(BusinessRulesParser.IsNotOneOfPredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsKindOfPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterIsKindOfPredicate(BusinessRulesParser.IsKindOfPredicateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsKindOfPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitIsKindOfPredicate(BusinessRulesParser.IsKindOfPredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryExpressionPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpressionPredicate(BusinessRulesParser.UnaryExpressionPredicateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryExpressionPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpressionPredicate(BusinessRulesParser.UnaryExpressionPredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterIsEqualToComparator(BusinessRulesParser.IsEqualToComparatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitIsEqualToComparator(BusinessRulesParser.IsEqualToComparatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsNotEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterIsNotEqualToComparator(BusinessRulesParser.IsNotEqualToComparatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsNotEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitIsNotEqualToComparator(BusinessRulesParser.IsNotEqualToComparatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsGreaterThanComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterIsGreaterThanComparator(BusinessRulesParser.IsGreaterThanComparatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsGreaterThanComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitIsGreaterThanComparator(BusinessRulesParser.IsGreaterThanComparatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsGreaterThanOrEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterIsGreaterThanOrEqualToComparator(BusinessRulesParser.IsGreaterThanOrEqualToComparatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsGreaterThanOrEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitIsGreaterThanOrEqualToComparator(BusinessRulesParser.IsGreaterThanOrEqualToComparatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsLessThanOrEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterIsLessThanOrEqualToComparator(BusinessRulesParser.IsLessThanOrEqualToComparatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsLessThanOrEqualToComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitIsLessThanOrEqualToComparator(BusinessRulesParser.IsLessThanOrEqualToComparatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsLessThanComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterIsLessThanComparator(BusinessRulesParser.IsLessThanComparatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsLessThanComparator}
	 * labeled alternative in {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitIsLessThanComparator(BusinessRulesParser.IsLessThanComparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#listDefinition}.
	 * @param ctx the parse tree
	 */
	void enterListDefinition(BusinessRulesParser.ListDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#listDefinition}.
	 * @param ctx the parse tree
	 */
	void exitListDefinition(BusinessRulesParser.ListDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#multipleExistsStatement}.
	 * @param ctx the parse tree
	 */
	void enterMultipleExistsStatement(BusinessRulesParser.MultipleExistsStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#multipleExistsStatement}.
	 * @param ctx the parse tree
	 */
	void exitMultipleExistsStatement(BusinessRulesParser.MultipleExistsStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#multipleNotExistsStatement}.
	 * @param ctx the parse tree
	 */
	void enterMultipleNotExistsStatement(BusinessRulesParser.MultipleNotExistsStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#multipleNotExistsStatement}.
	 * @param ctx the parse tree
	 */
	void exitMultipleNotExistsStatement(BusinessRulesParser.MultipleNotExistsStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExpression}
	 * labeled alternative in {@link BusinessRulesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpression(BusinessRulesParser.BinaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExpression}
	 * labeled alternative in {@link BusinessRulesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpression(BusinessRulesParser.BinaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link BusinessRulesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(BusinessRulesParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link BusinessRulesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(BusinessRulesParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionalExpressionTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterFunctionalExpressionTerm(BusinessRulesParser.FunctionalExpressionTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionalExpressionTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitFunctionalExpressionTerm(BusinessRulesParser.FunctionalExpressionTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefinedTermReferenceTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterDefinedTermReferenceTerm(BusinessRulesParser.DefinedTermReferenceTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefinedTermReferenceTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitDefinedTermReferenceTerm(BusinessRulesParser.DefinedTermReferenceTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OperatorInvocationTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterOperatorInvocationTerm(BusinessRulesParser.OperatorInvocationTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OperatorInvocationTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitOperatorInvocationTerm(BusinessRulesParser.OperatorInvocationTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefinitionApplicationTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterDefinitionApplicationTerm(BusinessRulesParser.DefinitionApplicationTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefinitionApplicationTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitDefinitionApplicationTerm(BusinessRulesParser.DefinitionApplicationTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CastExpressionTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterCastExpressionTerm(BusinessRulesParser.CastExpressionTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CastExpressionTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitCastExpressionTerm(BusinessRulesParser.CastExpressionTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SelectionExpressionTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterSelectionExpressionTerm(BusinessRulesParser.SelectionExpressionTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SelectionExpressionTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitSelectionExpressionTerm(BusinessRulesParser.SelectionExpressionTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstraintTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterConstraintTerm(BusinessRulesParser.ConstraintTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstraintTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitConstraintTerm(BusinessRulesParser.ConstraintTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierTerm(BusinessRulesParser.IdentifierTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierTerm}
	 * labeled alternative in {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierTerm(BusinessRulesParser.IdentifierTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ModelReferenceIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterModelReferenceIdentifier(BusinessRulesParser.ModelReferenceIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ModelReferenceIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitModelReferenceIdentifier(BusinessRulesParser.ModelReferenceIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralStringIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterLiteralStringIdentifier(BusinessRulesParser.LiteralStringIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralStringIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitLiteralStringIdentifier(BusinessRulesParser.LiteralStringIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterNumberIdentifier(BusinessRulesParser.NumberIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitNumberIdentifier(BusinessRulesParser.NumberIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntegerNumberIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIntegerNumberIdentifier(BusinessRulesParser.IntegerNumberIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerNumberIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIntegerNumberIdentifier(BusinessRulesParser.IntegerNumberIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanLiteralIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteralIdentifier(BusinessRulesParser.BooleanLiteralIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanLiteralIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteralIdentifier(BusinessRulesParser.BooleanLiteralIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CollectionIndexIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterCollectionIndexIdentifier(BusinessRulesParser.CollectionIndexIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CollectionIndexIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitCollectionIndexIdentifier(BusinessRulesParser.CollectionIndexIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DoubleQuotedStringIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterDoubleQuotedStringIdentifier(BusinessRulesParser.DoubleQuotedStringIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DoubleQuotedStringIdentifier}
	 * labeled alternative in {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitDoubleQuotedStringIdentifier(BusinessRulesParser.DoubleQuotedStringIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SumOfExpression}
	 * labeled alternative in {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterSumOfExpression(BusinessRulesParser.SumOfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SumOfExpression}
	 * labeled alternative in {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitSumOfExpression(BusinessRulesParser.SumOfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberOfExpression}
	 * labeled alternative in {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterNumberOfExpression(BusinessRulesParser.NumberOfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberOfExpression}
	 * labeled alternative in {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitNumberOfExpression(BusinessRulesParser.NumberOfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberOfUniqueExpression}
	 * labeled alternative in {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterNumberOfUniqueExpression(BusinessRulesParser.NumberOfUniqueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberOfUniqueExpression}
	 * labeled alternative in {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitNumberOfUniqueExpression(BusinessRulesParser.NumberOfUniqueExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#operatorInvocation}.
	 * @param ctx the parse tree
	 */
	void enterOperatorInvocation(BusinessRulesParser.OperatorInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#operatorInvocation}.
	 * @param ctx the parse tree
	 */
	void exitOperatorInvocation(BusinessRulesParser.OperatorInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#operatorParameterList}.
	 * @param ctx the parse tree
	 */
	void enterOperatorParameterList(BusinessRulesParser.OperatorParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#operatorParameterList}.
	 * @param ctx the parse tree
	 */
	void exitOperatorParameterList(BusinessRulesParser.OperatorParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#definitionApplication}.
	 * @param ctx the parse tree
	 */
	void enterDefinitionApplication(BusinessRulesParser.DefinitionApplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#definitionApplication}.
	 * @param ctx the parse tree
	 */
	void exitDefinitionApplication(BusinessRulesParser.DefinitionApplicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#collectionIndex}.
	 * @param ctx the parse tree
	 */
	void enterCollectionIndex(BusinessRulesParser.CollectionIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#collectionIndex}.
	 * @param ctx the parse tree
	 */
	void exitCollectionIndex(BusinessRulesParser.CollectionIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(BusinessRulesParser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(BusinessRulesParser.CastExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#selectionExpression}.
	 * @param ctx the parse tree
	 */
	void enterSelectionExpression(BusinessRulesParser.SelectionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#selectionExpression}.
	 * @param ctx the parse tree
	 */
	void exitSelectionExpression(BusinessRulesParser.SelectionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#modelReference}.
	 * @param ctx the parse tree
	 */
	void enterModelReference(BusinessRulesParser.ModelReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#modelReference}.
	 * @param ctx the parse tree
	 */
	void exitModelReference(BusinessRulesParser.ModelReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#modelReferenceList}.
	 * @param ctx the parse tree
	 */
	void enterModelReferenceList(BusinessRulesParser.ModelReferenceListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#modelReferenceList}.
	 * @param ctx the parse tree
	 */
	void exitModelReferenceList(BusinessRulesParser.ModelReferenceListContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#dottedModelPath}.
	 * @param ctx the parse tree
	 */
	void enterDottedModelPath(BusinessRulesParser.DottedModelPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#dottedModelPath}.
	 * @param ctx the parse tree
	 */
	void exitDottedModelPath(BusinessRulesParser.DottedModelPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#propertyOfModelPath}.
	 * @param ctx the parse tree
	 */
	void enterPropertyOfModelPath(BusinessRulesParser.PropertyOfModelPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#propertyOfModelPath}.
	 * @param ctx the parse tree
	 */
	void exitPropertyOfModelPath(BusinessRulesParser.PropertyOfModelPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#compoundReport}.
	 * @param ctx the parse tree
	 */
	void enterCompoundReport(BusinessRulesParser.CompoundReportContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#compoundReport}.
	 * @param ctx the parse tree
	 */
	void exitCompoundReport(BusinessRulesParser.CompoundReportContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#simpleReport}.
	 * @param ctx the parse tree
	 */
	void enterSimpleReport(BusinessRulesParser.SimpleReportContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#simpleReport}.
	 * @param ctx the parse tree
	 */
	void exitSimpleReport(BusinessRulesParser.SimpleReportContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#concatenatedReport}.
	 * @param ctx the parse tree
	 */
	void enterConcatenatedReport(BusinessRulesParser.ConcatenatedReportContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#concatenatedReport}.
	 * @param ctx the parse tree
	 */
	void exitConcatenatedReport(BusinessRulesParser.ConcatenatedReportContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#conditionalReport}.
	 * @param ctx the parse tree
	 */
	void enterConditionalReport(BusinessRulesParser.ConditionalReportContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#conditionalReport}.
	 * @param ctx the parse tree
	 */
	void exitConditionalReport(BusinessRulesParser.ConditionalReportContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#simpleTerm}.
	 * @param ctx the parse tree
	 */
	void enterSimpleTerm(BusinessRulesParser.SimpleTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#simpleTerm}.
	 * @param ctx the parse tree
	 */
	void exitSimpleTerm(BusinessRulesParser.SimpleTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#collectionMemberConstraint}.
	 * @param ctx the parse tree
	 */
	void enterCollectionMemberConstraint(BusinessRulesParser.CollectionMemberConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#collectionMemberConstraint}.
	 * @param ctx the parse tree
	 */
	void exitCollectionMemberConstraint(BusinessRulesParser.CollectionMemberConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstrainedCollectionMembership}
	 * labeled alternative in {@link BusinessRulesParser#existsStatement}.
	 * @param ctx the parse tree
	 */
	void enterConstrainedCollectionMembership(BusinessRulesParser.ConstrainedCollectionMembershipContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstrainedCollectionMembership}
	 * labeled alternative in {@link BusinessRulesParser#existsStatement}.
	 * @param ctx the parse tree
	 */
	void exitConstrainedCollectionMembership(BusinessRulesParser.ConstrainedCollectionMembershipContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SimpleExists}
	 * labeled alternative in {@link BusinessRulesParser#existsStatement}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExists(BusinessRulesParser.SimpleExistsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SimpleExists}
	 * labeled alternative in {@link BusinessRulesParser#existsStatement}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExists(BusinessRulesParser.SimpleExistsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#enumerator}.
	 * @param ctx the parse tree
	 */
	void enterEnumerator(BusinessRulesParser.EnumeratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#enumerator}.
	 * @param ctx the parse tree
	 */
	void exitEnumerator(BusinessRulesParser.EnumeratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#notExistsStatement}.
	 * @param ctx the parse tree
	 */
	void enterNotExistsStatement(BusinessRulesParser.NotExistsStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#notExistsStatement}.
	 * @param ctx the parse tree
	 */
	void exitNotExistsStatement(BusinessRulesParser.NotExistsStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#forallStatement}.
	 * @param ctx the parse tree
	 */
	void enterForallStatement(BusinessRulesParser.ForallStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#forallStatement}.
	 * @param ctx the parse tree
	 */
	void exitForallStatement(BusinessRulesParser.ForallStatementContext ctx);
}