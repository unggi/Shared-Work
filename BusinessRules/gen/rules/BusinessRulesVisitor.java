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
	 * Visit a parse tree produced by {@link BusinessRulesParser#logicalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalStatement(BusinessRulesParser.LogicalStatementContext ctx);
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
	 * Visit a parse tree produced by the {@code UnaryExpressionPredicate}
	 * labeled alternative in {@link BusinessRulesParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpressionPredicate(BusinessRulesParser.UnaryExpressionPredicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparator(BusinessRulesParser.ComparatorContext ctx);
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
	 * Visit a parse tree produced by {@link BusinessRulesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(BusinessRulesParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(BusinessRulesParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link BusinessRulesParser#functionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionalExpression(BusinessRulesParser.FunctionalExpressionContext ctx);
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
	 * Visit a parse tree produced by {@link BusinessRulesParser#modelPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelPath(BusinessRulesParser.ModelPathContext ctx);
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
}