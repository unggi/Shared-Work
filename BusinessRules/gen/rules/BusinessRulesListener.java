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
	 * Enter a parse tree produced by {@link BusinessRulesParser#multipleParameterContext}.
	 * @param ctx the parse tree
	 */
	void enterMultipleParameterContext(BusinessRulesParser.MultipleParameterContextContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#multipleParameterContext}.
	 * @param ctx the parse tree
	 */
	void exitMultipleParameterContext(BusinessRulesParser.MultipleParameterContextContext ctx);
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
	 * Enter a parse tree produced by {@link BusinessRulesParser#modelReferenceWithAlias}.
	 * @param ctx the parse tree
	 */
	void enterModelReferenceWithAlias(BusinessRulesParser.ModelReferenceWithAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#modelReferenceWithAlias}.
	 * @param ctx the parse tree
	 */
	void exitModelReferenceWithAlias(BusinessRulesParser.ModelReferenceWithAliasContext ctx);
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
	 * Enter a parse tree produced by {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void enterLogicalStatement(BusinessRulesParser.LogicalStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 */
	void exitLogicalStatement(BusinessRulesParser.LogicalStatementContext ctx);
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
	 * Enter a parse tree produced by {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(BusinessRulesParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(BusinessRulesParser.PredicateContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#binaryPredicate}.
	 * @param ctx the parse tree
	 */
	void enterBinaryPredicate(BusinessRulesParser.BinaryPredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#binaryPredicate}.
	 * @param ctx the parse tree
	 */
	void exitBinaryPredicate(BusinessRulesParser.BinaryPredicateContext ctx);
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
	 * Enter a parse tree produced by {@link BusinessRulesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(BusinessRulesParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(BusinessRulesParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(BusinessRulesParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(BusinessRulesParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(BusinessRulesParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(BusinessRulesParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionalExpression(BusinessRulesParser.FunctionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionalExpression(BusinessRulesParser.FunctionalExpressionContext ctx);
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
	 * Enter a parse tree produced by {@link BusinessRulesParser#modelPath}.
	 * @param ctx the parse tree
	 */
	void enterModelPath(BusinessRulesParser.ModelPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link BusinessRulesParser#modelPath}.
	 * @param ctx the parse tree
	 */
	void exitModelPath(BusinessRulesParser.ModelPathContext ctx);
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
}