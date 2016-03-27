// Generated from /Users/unggi/GitHub/Shared-Work/BusinessRules/src/main/antlr/BusinessRules.g4 by ANTLR 4.5.1
package rules;

import codegen.symbols.Symbol;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Collections;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BusinessRulesParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45,
			T__45 = 46, T__46 = 47, T__47 = 48, T__48 = 49, T__49 = 50, T__50 = 51, T__51 = 52,
			T__52 = 53, T__53 = 54, T__54 = 55, T__55 = 56, T__56 = 57, T__57 = 58, T__58 = 59,
			T__59 = 60, T__60 = 61, T__61 = 62, T__62 = 63, T__63 = 64, T__64 = 65, T__65 = 66,
			T__66 = 67, T__67 = 68, T__68 = 69, T__69 = 70, T__70 = 71, T__71 = 72, THE = 73, AN = 74,
			FragmentName = 75, IsEqualTo = 76, IsNotEqualTo = 77, IsGreaterThan = 78, IsLessThanOrEqualTo = 79,
			IsGreaterThanOrEqualTo = 80, IsLessThan = 81, BooleanLiteral = 82, LiteralString = 83,
			DoubleQuotedString = 84, ModelElementName = 85, VariableName = 86, Number = 87,
			OrdinalNumber = 88, IntegerNumber = 89, OperatorName = 90, COMMENT = 91, LINE_COMMENT = 92,
			WS = 93;
	public static final int
		RULE_fileBody = 0, RULE_modelFileReferences = 1, RULE_modelFileReference = 2, 
		RULE_declarations = 3, RULE_declaration = 4, RULE_validationRule = 5,
			RULE_definition = 6, RULE_ruleSet = 7, RULE_context = 8, RULE_multipleContextParameter = 9,
			RULE_modelReferenceParameter = 10, RULE_constraint = 11, RULE_binaryLogicalOperator = 12,
			RULE_logicalStatement = 13, RULE_simpleOrComplexConstraint = 14, RULE_predicate = 15,
			RULE_comparator = 16, RULE_listDefinition = 17, RULE_multipleExistsStatement = 18,
			RULE_multipleNotExistsStatement = 19, RULE_expression = 20, RULE_term = 21,
			RULE_identifier = 22, RULE_functionalExpression = 23, RULE_operatorInvocation = 24,
			RULE_operatorParameterList = 25, RULE_definitionApplication = 26, RULE_collectionIndex = 27,
			RULE_castExpression = 28, RULE_selectionExpression = 29, RULE_modelReference = 30,
			RULE_modelReferenceList = 31, RULE_dottedModelPath = 32, RULE_propertyOfModelPath = 33,
			RULE_compoundReport = 34, RULE_simpleReport = 35, RULE_concatenatedReport = 36,
			RULE_conditionalReport = 37, RULE_simpleTerm = 38, RULE_collectionMemberConstraint = 39,
			RULE_existsStatement = 40, RULE_enumerator = 41, RULE_notExistsStatement = 42,
			RULE_forallStatement = 43;
	public static final String[] ruleNames = {
		"fileBody", "modelFileReferences", "modelFileReference", "declarations",
			"declaration", "validationRule", "definition", "ruleSet", "context", "multipleContextParameter",
			"modelReferenceParameter", "constraint", "binaryLogicalOperator", "logicalStatement",
			"simpleOrComplexConstraint", "predicate", "comparator", "listDefinition",
			"multipleExistsStatement", "multipleNotExistsStatement", "expression",
			"term", "identifier", "functionalExpression", "operatorInvocation", "operatorParameterList",
			"definitionApplication", "collectionIndex", "castExpression", "selectionExpression",
			"modelReference", "modelReferenceList", "dottedModelPath", "propertyOfModelPath",
			"compoundReport", "simpleReport", "concatenatedReport", "conditionalReport",
			"simpleTerm", "collectionMemberConstraint", "existsStatement", "enumerator",
			"notExistsStatement", "forallStatement"
	};

	private static final String[] _LITERAL_NAMES = {
			null, "'Model'", "'Validation:'", "'report:'", "'Definition:'", "'Given:'",
			"'Value:'", "'Rule set'", "'applies to'", "'where'", "'Context:'", "','",
			"'('", "')'", "'Constraint:'", "'If'", "'then'", "'else'", "'and'", "'or'",
			"'implies'", "'if and only if'", "'is one of'", "'is not one of'", "'is a kind of'",
		"'following'", "'is present'", "'are present'", "':'", "'is not present'", 
		"'are not present'", "'*'", "'/'", "'+'", "'-'", "'mod'", "'sum of'", 
		"'number of'", "'number of unique'", "'by'", "'from'", "'to'", "'with'", 
		"'using'", "'of'", "'as a'", "'as an'", "'first'", "'.'", "'report'", 
		"'if'", "';'", "'of the'", "'has'", "'have'", "'is'", "'are'", "'present'", 
		"'at least'", "'at most'", "'exactly'", "'one'", "'two'", "'three'", "'four'", 
		"'no'", "'none'", "'each'", "'in each'", "'all'", "'every'", "'for each'", 
		"'in the collection of'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null,
			null, "THE", "AN", "FragmentName", "IsEqualTo", "IsNotEqualTo", "IsGreaterThan",
			"IsLessThanOrEqualTo", "IsGreaterThanOrEqualTo", "IsLessThan", "BooleanLiteral",
			"LiteralString", "DoubleQuotedString", "ModelElementName", "VariableName",
			"Number", "OrdinalNumber", "IntegerNumber", "OperatorName", "COMMENT",
			"LINE_COMMENT", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "BusinessRules.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BusinessRulesParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FileBodyContext extends ParserRuleContext {
		public ModelFileReferencesContext modelFileReferences() {
			return getRuleContext(ModelFileReferencesContext.class,0);
		}
		public DeclarationsContext declarations() {
			return getRuleContext(DeclarationsContext.class,0);
		}
		public FileBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterFileBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitFileBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitFileBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileBodyContext fileBody() throws RecognitionException {
		FileBodyContext _localctx = new FileBodyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_fileBody);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(88);
			modelFileReferences();
				setState(89);
			declarations();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModelFileReferencesContext extends ParserRuleContext {
		public List<ModelFileReferenceContext> modelFileReference() {
			return getRuleContexts(ModelFileReferenceContext.class);
		}
		public ModelFileReferenceContext modelFileReference(int i) {
			return getRuleContext(ModelFileReferenceContext.class,i);
		}
		public ModelFileReferencesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modelFileReferences; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterModelFileReferences(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitModelFileReferences(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitModelFileReferences(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelFileReferencesContext modelFileReferences() throws RecognitionException {
		ModelFileReferencesContext _localctx = new ModelFileReferencesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_modelFileReferences);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
					setState(91);
				modelFileReference();
				}
				}
				setState(94); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModelFileReferenceContext extends ParserRuleContext {
		public TerminalNode DoubleQuotedString() { return getToken(BusinessRulesParser.DoubleQuotedString, 0); }
		public ModelFileReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modelFileReference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterModelFileReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitModelFileReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitModelFileReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelFileReferenceContext modelFileReference() throws RecognitionException {
		ModelFileReferenceContext _localctx = new ModelFileReferenceContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_modelFileReference);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(96);
			match(T__0);
				setState(97);
			match(DoubleQuotedString);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationsContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public DeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarations; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterDeclarations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitDeclarations(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitDeclarations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationsContext declarations() throws RecognitionException {
		DeclarationsContext _localctx = new DeclarationsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_declarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1 || _la==T__3) {
				{
				{
					setState(99);
				declaration();
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public DefinitionContext definition() {
			return getRuleContext(DefinitionContext.class,0);
		}
		public ValidationRuleContext validationRule() {
			return getRuleContext(ValidationRuleContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_declaration);
		try {
			setState(107);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
					setState(105);
				definition();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
					setState(106);
				validationRule();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValidationRuleContext extends ParserRuleContext {
		public Token name;
		public ContextContext context() {
			return getRuleContext(ContextContext.class,0);
		}
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public TerminalNode DoubleQuotedString() { return getToken(BusinessRulesParser.DoubleQuotedString, 0); }
		public CompoundReportContext compoundReport() {
			return getRuleContext(CompoundReportContext.class,0);
		}
		public ValidationRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_validationRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterValidationRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitValidationRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitValidationRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValidationRuleContext validationRule() throws RecognitionException {
		ValidationRuleContext _localctx = new ValidationRuleContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_validationRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(109);
			match(T__1);
				setState(110);
			((ValidationRuleContext)_localctx).name = match(DoubleQuotedString);
				setState(111);
			context();
				setState(112);
			constraint();
				setState(115);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
					setState(113);
				match(T__2);
					setState(114);
				compoundReport();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefinitionContext extends ParserRuleContext {
		public Token name;
		public MultipleContextParameterContext multipleContextParameter() {
			return getRuleContext(MultipleContextParameterContext.class,0);
		}
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class, 0);
		}
		public TerminalNode DoubleQuotedString() { return getToken(BusinessRulesParser.DoubleQuotedString, 0); }
		public DefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(117);
			match(T__3);
				setState(118);
			((DefinitionContext)_localctx).name = match(DoubleQuotedString);
				setState(119);
			match(T__4);
				setState(120);
			multipleContextParameter();
				setState(121);
				match(T__5);
				setState(122);
				predicate();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleSetContext extends ParserRuleContext {
		public TerminalNode DoubleQuotedString() { return getToken(BusinessRulesParser.DoubleQuotedString, 0); }
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public RuleSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterRuleSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitRuleSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitRuleSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleSetContext ruleSet() throws RecognitionException {
		RuleSetContext _localctx = new RuleSetContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ruleSet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(124);
				match(T__6);
				setState(125);
			match(DoubleQuotedString);
				setState(131);
			_la = _input.LA(1);
				if (_la == T__7) {
				{
					setState(126);
				match(T__7);
					setState(127);
					modelReference();
					setState(128);
					match(T__8);
					setState(129);
				constraint();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContextContext extends ParserRuleContext {
		public ModelReferenceParameterContext modelReferenceParameter() {
			return getRuleContext(ModelReferenceParameterContext.class,0);
		}
		public ContextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_context; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterContext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitContext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitContext(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContextContext context() throws RecognitionException {
		ContextContext _localctx = new ContextContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_context);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(133);
				match(T__9);
				setState(134);
			modelReferenceParameter();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleContextParameterContext extends ParserRuleContext {
		public List<ModelReferenceParameterContext> modelReferenceParameter() {
			return getRuleContexts(ModelReferenceParameterContext.class);
		}
		public ModelReferenceParameterContext modelReferenceParameter(int i) {
			return getRuleContext(ModelReferenceParameterContext.class,i);
		}
		public MultipleContextParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multipleContextParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterMultipleContextParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitMultipleContextParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitMultipleContextParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultipleContextParameterContext multipleContextParameter() throws RecognitionException {
		MultipleContextParameterContext _localctx = new MultipleContextParameterContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_multipleContextParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(136);
			modelReferenceParameter();
				setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
				while (_la == T__10) {
				{
				{
					setState(137);
					match(T__10);
					setState(138);
				modelReferenceParameter();
				}
				}
					setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModelReferenceParameterContext extends ParserRuleContext {
		public ModelReferenceContext ref;
		public Token alias;
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public TerminalNode DoubleQuotedString() { return getToken(BusinessRulesParser.DoubleQuotedString, 0); }
		public ModelReferenceParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modelReferenceParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterModelReferenceParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitModelReferenceParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitModelReferenceParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelReferenceParameterContext modelReferenceParameter() throws RecognitionException {
		ModelReferenceParameterContext _localctx = new ModelReferenceParameterContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_modelReferenceParameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(144);
				((ModelReferenceParameterContext) _localctx).ref = modelReference();
				setState(145);
			match(T__11);
				setState(146);
				((ModelReferenceParameterContext) _localctx).alias = match(DoubleQuotedString);
				setState(147);
				match(T__12);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintContext extends ParserRuleContext {
		public LogicalStatementContext condBlock;
		public LogicalStatementContext thenBlock;
		public LogicalStatementContext elseBlock;
		public List<LogicalStatementContext> logicalStatement() {
			return getRuleContexts(LogicalStatementContext.class);
		}
		public LogicalStatementContext logicalStatement(int i) {
			return getRuleContext(LogicalStatementContext.class,i);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_constraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(149);
				match(T__13);
				setState(159);
			switch (_input.LA(1)) {
				case T__14:
				{
					setState(150);
					match(T__14);
					setState(151);
					((ConstraintContext) _localctx).condBlock = logicalStatement(0);
					setState(152);
					match(T__15);
					setState(153);
				((ConstraintContext)_localctx).thenBlock = logicalStatement(0);
					setState(156);
				_la = _input.LA(1);
					if (_la == T__16) {
					{
						setState(154);
						match(T__16);
						setState(155);
					((ConstraintContext)_localctx).elseBlock = logicalStatement(0);
					}
				}

				}
				break;
				case T__11:
			case T__35:
				case T__36:
				case T__37:
				case T__46:
			case T__57:
			case T__58:
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case T__66:
			case T__67:
			case T__68:
				case T__69:
				case T__70:
			case FragmentName:
			case BooleanLiteral:
			case LiteralString:
			case DoubleQuotedString:
			case ModelElementName:
			case Number:
			case OrdinalNumber:
			case IntegerNumber:
			case OperatorName:
				{
					setState(158);
				logicalStatement(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryLogicalOperatorContext extends ParserRuleContext {
		public Token and;
		public Token or;
		public Token implies;
		public Token iff;
		public BinaryLogicalOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryLogicalOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterBinaryLogicalOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitBinaryLogicalOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitBinaryLogicalOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryLogicalOperatorContext binaryLogicalOperator() throws RecognitionException {
		BinaryLogicalOperatorContext _localctx = new BinaryLogicalOperatorContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_binaryLogicalOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(165);
			switch (_input.LA(1)) {
				case T__17:
				{
					setState(161);
					((BinaryLogicalOperatorContext) _localctx).and = match(T__17);
				}
				break;
				case T__18:
				{
					setState(162);
					((BinaryLogicalOperatorContext) _localctx).or = match(T__18);
				}
				break;
				case T__19:
				{
					setState(163);
					((BinaryLogicalOperatorContext) _localctx).implies = match(T__19);
				}
				break;
				case T__20:
				{
					setState(164);
					((BinaryLogicalOperatorContext) _localctx).iff = match(T__20);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalStatementContext extends ParserRuleContext {
		public LogicalStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalStatement; }
	 
		public LogicalStatementContext() { }
		public void copyFrom(LogicalStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LogicalPredicateStatementContext extends LogicalStatementContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public LogicalPredicateStatementContext(LogicalStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterLogicalPredicateStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitLogicalPredicateStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitLogicalPredicateStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalNotExistsStatementContext extends LogicalStatementContext {
		public NotExistsStatementContext notExistsStatement() {
			return getRuleContext(NotExistsStatementContext.class,0);
		}
		public LogicalNotExistsStatementContext(LogicalStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterLogicalNotExistsStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitLogicalNotExistsStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitLogicalNotExistsStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalExistsStatementContext extends LogicalStatementContext {
		public ExistsStatementContext existsStatement() {
			return getRuleContext(ExistsStatementContext.class,0);
		}
		public LogicalExistsStatementContext(LogicalStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterLogicalExistsStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitLogicalExistsStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitLogicalExistsStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalForAllStatementContext extends LogicalStatementContext {
		public ForallStatementContext forallStatement() {
			return getRuleContext(ForallStatementContext.class,0);
		}
		public LogicalForAllStatementContext(LogicalStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterLogicalForAllStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitLogicalForAllStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitLogicalForAllStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryLogicalOperatorStatementContext extends LogicalStatementContext {
		public LogicalStatementContext left;
		public BinaryLogicalOperatorContext op;
		public LogicalStatementContext right;
		public List<LogicalStatementContext> logicalStatement() {
			return getRuleContexts(LogicalStatementContext.class);
		}
		public LogicalStatementContext logicalStatement(int i) {
			return getRuleContext(LogicalStatementContext.class,i);
		}
		public BinaryLogicalOperatorContext binaryLogicalOperator() {
			return getRuleContext(BinaryLogicalOperatorContext.class,0);
		}
		public BinaryLogicalOperatorStatementContext(LogicalStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterBinaryLogicalOperatorStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitBinaryLogicalOperatorStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitBinaryLogicalOperatorStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalStatementContext logicalStatement() throws RecognitionException {
		return logicalStatement(0);
	}

	private LogicalStatementContext logicalStatement(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalStatementContext _localctx = new LogicalStatementContext(_ctx, _parentState);
		LogicalStatementContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_logicalStatement, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(172);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				_localctx = new LogicalPredicateStatementContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

					setState(168);
				predicate();
				}
				break;
			case 2:
				{
				_localctx = new LogicalExistsStatementContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
					setState(169);
				existsStatement();
				}
				break;
			case 3:
				{
				_localctx = new LogicalNotExistsStatementContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
					setState(170);
				notExistsStatement();
				}
				break;
			case 4:
				{
				_localctx = new LogicalForAllStatementContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
					setState(171);
				forallStatement();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
				setState(180);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BinaryLogicalOperatorStatementContext(new LogicalStatementContext(_parentctx, _parentState));
					((BinaryLogicalOperatorStatementContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_logicalStatement);
						setState(174);
					if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(175);
					((BinaryLogicalOperatorStatementContext)_localctx).op = binaryLogicalOperator();
						setState(176);
					((BinaryLogicalOperatorStatementContext)_localctx).right = logicalStatement(6);
					}
					} 
				}
				setState(182);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class SimpleOrComplexConstraintContext extends ParserRuleContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public SimpleOrComplexConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleOrComplexConstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterSimpleOrComplexConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitSimpleOrComplexConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitSimpleOrComplexConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleOrComplexConstraintContext simpleOrComplexConstraint() throws RecognitionException {
		SimpleOrComplexConstraintContext _localctx = new SimpleOrComplexConstraintContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_simpleOrComplexConstraint);
		try {
			setState(188);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
					setState(183);
				match(T__11);
					setState(184);
					constraint();
					setState(185);
					match(T__12);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
					setState(187);
				predicate();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateContext extends ParserRuleContext {
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
	 
		public PredicateContext() { }
		public void copyFrom(PredicateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IsKindOfPredicateContext extends PredicateContext {
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public TerminalNode ModelElementName() { return getToken(BusinessRulesParser.ModelElementName, 0); }
		public IsKindOfPredicateContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIsKindOfPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIsKindOfPredicate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIsKindOfPredicate(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsOneOfPredicateContext extends PredicateContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ListDefinitionContext listDefinition() {
			return getRuleContext(ListDefinitionContext.class,0);
		}
		public IsOneOfPredicateContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIsOneOfPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIsOneOfPredicate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIsOneOfPredicate(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryPredicateContext extends PredicateContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BinaryPredicateContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterBinaryPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitBinaryPredicate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitBinaryPredicate(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class UnaryExpressionPredicateContext extends PredicateContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public UnaryExpressionPredicateContext(PredicateContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof BusinessRulesListener)
				((BusinessRulesListener) listener).enterUnaryExpressionPredicate(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof BusinessRulesListener)
				((BusinessRulesListener) listener).exitUnaryExpressionPredicate(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof BusinessRulesVisitor)
				return ((BusinessRulesVisitor<? extends T>) visitor).visitUnaryExpressionPredicate(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsNotOneOfPredicateContext extends PredicateContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ListDefinitionContext listDefinition() {
			return getRuleContext(ListDefinitionContext.class,0);
		}
		public IsNotOneOfPredicateContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIsNotOneOfPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIsNotOneOfPredicate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIsNotOneOfPredicate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_predicate);
		try {
			setState(207);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new BinaryPredicateContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
					setState(190);
				((BinaryPredicateContext)_localctx).left = expression(0);
					setState(191);
				comparator();
					setState(192);
				((BinaryPredicateContext)_localctx).right = expression(0);
				}
				break;
			case 2:
				_localctx = new IsOneOfPredicateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
					setState(194);
					expression(0);
					setState(195);
					match(T__21);
					setState(196);
				listDefinition();
				}
				break;
			case 3:
				_localctx = new IsNotOneOfPredicateContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
					setState(198);
					expression(0);
					setState(199);
					match(T__22);
					setState(200);
				listDefinition();
				}
				break;
			case 4:
				_localctx = new IsKindOfPredicateContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
					setState(202);
					modelReference();
					setState(203);
					match(T__23);
					setState(204);
				match(ModelElementName);
				}
				break;
				case 5:
					_localctx = new UnaryExpressionPredicateContext(_localctx);
					enterOuterAlt(_localctx, 5);
				{
					setState(206);
					expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparatorContext extends ParserRuleContext {
		public ComparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparator; }
	 
		public ComparatorContext() { }
		public void copyFrom(ComparatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IsLessThanComparatorContext extends ComparatorContext {
		public TerminalNode IsLessThan() { return getToken(BusinessRulesParser.IsLessThan, 0); }
		public IsLessThanComparatorContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIsLessThanComparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIsLessThanComparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIsLessThanComparator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsEqualToComparatorContext extends ComparatorContext {
		public TerminalNode IsEqualTo() { return getToken(BusinessRulesParser.IsEqualTo, 0); }
		public IsEqualToComparatorContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIsEqualToComparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIsEqualToComparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIsEqualToComparator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsGreaterThanOrEqualToComparatorContext extends ComparatorContext {
		public TerminalNode IsGreaterThanOrEqualTo() { return getToken(BusinessRulesParser.IsGreaterThanOrEqualTo, 0); }
		public IsGreaterThanOrEqualToComparatorContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIsGreaterThanOrEqualToComparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIsGreaterThanOrEqualToComparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIsGreaterThanOrEqualToComparator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsLessThanOrEqualToComparatorContext extends ComparatorContext {
		public TerminalNode IsLessThanOrEqualTo() { return getToken(BusinessRulesParser.IsLessThanOrEqualTo, 0); }
		public IsLessThanOrEqualToComparatorContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIsLessThanOrEqualToComparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIsLessThanOrEqualToComparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIsLessThanOrEqualToComparator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsGreaterThanComparatorContext extends ComparatorContext {
		public TerminalNode IsGreaterThan() { return getToken(BusinessRulesParser.IsGreaterThan, 0); }
		public IsGreaterThanComparatorContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIsGreaterThanComparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIsGreaterThanComparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIsGreaterThanComparator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsNotEqualToComparatorContext extends ComparatorContext {
		public TerminalNode IsNotEqualTo() { return getToken(BusinessRulesParser.IsNotEqualTo, 0); }
		public IsNotEqualToComparatorContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIsNotEqualToComparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIsNotEqualToComparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIsNotEqualToComparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparatorContext comparator() throws RecognitionException {
		ComparatorContext _localctx = new ComparatorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_comparator);
		try {
			setState(215);
			switch (_input.LA(1)) {
			case IsEqualTo:
				_localctx = new IsEqualToComparatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
					setState(209);
				match(IsEqualTo);
				}
				break;
			case IsNotEqualTo:
				_localctx = new IsNotEqualToComparatorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
					setState(210);
				match(IsNotEqualTo);
				}
				break;
			case IsGreaterThan:
				_localctx = new IsGreaterThanComparatorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
					setState(211);
				match(IsGreaterThan);
				}
				break;
			case IsGreaterThanOrEqualTo:
				_localctx = new IsGreaterThanOrEqualToComparatorContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
					setState(212);
				match(IsGreaterThanOrEqualTo);
				}
				break;
			case IsLessThanOrEqualTo:
				_localctx = new IsLessThanOrEqualToComparatorContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
					setState(213);
				match(IsLessThanOrEqualTo);
				}
				break;
			case IsLessThan:
				_localctx = new IsLessThanComparatorContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
					setState(214);
				match(IsLessThan);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListDefinitionContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public ListDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterListDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitListDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitListDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListDefinitionContext listDefinition() throws RecognitionException {
		ListDefinitionContext _localctx = new ListDefinitionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_listDefinition);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(217);
			identifier();
				setState(222);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
						setState(218);
						match(T__10);
						setState(219);
					identifier();
					}
					} 
				}
				setState(224);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleExistsStatementContext extends ParserRuleContext {
		public ModelReferenceListContext modelReferenceList() {
			return getRuleContext(ModelReferenceListContext.class,0);
		}
		public MultipleExistsStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multipleExistsStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterMultipleExistsStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitMultipleExistsStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitMultipleExistsStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultipleExistsStatementContext multipleExistsStatement() throws RecognitionException {
		MultipleExistsStatementContext _localctx = new MultipleExistsStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_multipleExistsStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(225);
				match(T__24);
				setState(226);
			_la = _input.LA(1);
				if (!(_la == T__25 || _la == T__26)) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
				setState(227);
				match(T__27);
				setState(228);
			modelReferenceList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleNotExistsStatementContext extends ParserRuleContext {
		public ModelReferenceListContext modelReferenceList() {
			return getRuleContext(ModelReferenceListContext.class,0);
		}
		public MultipleNotExistsStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multipleNotExistsStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterMultipleNotExistsStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitMultipleNotExistsStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitMultipleNotExistsStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultipleNotExistsStatementContext multipleNotExistsStatement() throws RecognitionException {
		MultipleNotExistsStatementContext _localctx = new MultipleNotExistsStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_multipleNotExistsStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(230);
				match(T__24);
				setState(231);
			_la = _input.LA(1);
				if (!(_la == T__28 || _la == T__29)) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
				setState(232);
				match(T__27);
				setState(233);
			modelReferenceList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BinaryExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BinaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterBinaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitBinaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitBinaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryExpressionContext extends ExpressionContext {
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public UnaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new UnaryExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

				setState(236);
			term();
			}
			_ctx.stop = _input.LT(-1);
				setState(243);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
					((BinaryExpressionContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(238);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(239);
					((BinaryExpressionContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
						if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34))) != 0))) {
						((BinaryExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					} else {
						consume();
					}
						setState(240);
					((BinaryExpressionContext)_localctx).right = expression(3);
					}
					} 
				}
				setState(245);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	 
		public TermContext() { }
		public void copyFrom(TermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ConstraintTermContext extends TermContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public ConstraintTermContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterConstraintTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitConstraintTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitConstraintTerm(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionalExpressionTermContext extends TermContext {
		public FunctionalExpressionContext functionalExpression() {
			return getRuleContext(FunctionalExpressionContext.class,0);
		}
		public FunctionalExpressionTermContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterFunctionalExpressionTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitFunctionalExpressionTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitFunctionalExpressionTerm(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SelectionExpressionTermContext extends TermContext {
		public SelectionExpressionContext selectionExpression() {
			return getRuleContext(SelectionExpressionContext.class,0);
		}
		public SelectionExpressionTermContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterSelectionExpressionTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitSelectionExpressionTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitSelectionExpressionTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class DefinedTermReferenceTermContext extends TermContext {
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class, 0);
		}

		public TerminalNode FragmentName() {
			return getToken(BusinessRulesParser.FragmentName, 0);
		}

		public DefinedTermReferenceTermContext(TermContext ctx) {
			copyFrom(ctx);
		}
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof BusinessRulesListener)
				((BusinessRulesListener) listener).enterDefinedTermReferenceTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof BusinessRulesListener)
				((BusinessRulesListener) listener).exitDefinedTermReferenceTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof BusinessRulesVisitor)
				return ((BusinessRulesVisitor<? extends T>) visitor).visitDefinedTermReferenceTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class IdentifierTermContext extends TermContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class, 0);
		}

		public IdentifierTermContext(TermContext ctx) {
			copyFrom(ctx);
		}
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof BusinessRulesListener) ((BusinessRulesListener) listener).enterIdentifierTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof BusinessRulesListener) ((BusinessRulesListener) listener).exitIdentifierTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof BusinessRulesVisitor)
				return ((BusinessRulesVisitor<? extends T>) visitor).visitIdentifierTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class DefinitionApplicationTermContext extends TermContext {
		public DefinitionApplicationContext definitionApplication() {
			return getRuleContext(DefinitionApplicationContext.class,0);
		}

		public DefinitionApplicationTermContext(TermContext ctx) {
			copyFrom(ctx);
		}
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof BusinessRulesListener)
				((BusinessRulesListener) listener).enterDefinitionApplicationTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof BusinessRulesListener)
				((BusinessRulesListener) listener).exitDefinitionApplicationTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof BusinessRulesVisitor)
				return ((BusinessRulesVisitor<? extends T>) visitor).visitDefinitionApplicationTerm(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CastExpressionTermContext extends TermContext {
		public CastExpressionContext castExpression() {
			return getRuleContext(CastExpressionContext.class,0);
		}
		public CastExpressionTermContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterCastExpressionTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitCastExpressionTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitCastExpressionTerm(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorInvocationTermContext extends TermContext {
		public OperatorInvocationContext operatorInvocation() {
			return getRuleContext(OperatorInvocationContext.class,0);
		}
		public OperatorInvocationTermContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterOperatorInvocationTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitOperatorInvocationTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitOperatorInvocationTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_term);
		try {
			setState(259);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new IdentifierTermContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
					setState(246);
				identifier();
				}
				break;
			case 2:
				_localctx = new FunctionalExpressionTermContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
					setState(247);
				functionalExpression();
				}
				break;
			case 3:
				_localctx = new DefinedTermReferenceTermContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
					setState(248);
				modelReference();
					setState(249);
				match(FragmentName);
				}
				break;
			case 4:
				_localctx = new OperatorInvocationTermContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
					setState(251);
				operatorInvocation();
				}
				break;
			case 5:
				_localctx = new DefinitionApplicationTermContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
					setState(252);
				definitionApplication();
				}
				break;
			case 6:
				_localctx = new CastExpressionTermContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
					setState(253);
				castExpression();
				}
				break;
			case 7:
				_localctx = new SelectionExpressionTermContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
					setState(254);
				selectionExpression();
				}
				break;
			case 8:
				_localctx = new ConstraintTermContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
					setState(255);
				match(T__11);
					setState(256);
					constraint();
					setState(257);
					match(T__12);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
	 
		public IdentifierContext() { }
		public void copyFrom(IdentifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BooleanLiteralIdentifierContext extends IdentifierContext {
		public TerminalNode BooleanLiteral() { return getToken(BusinessRulesParser.BooleanLiteral, 0); }
		public BooleanLiteralIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterBooleanLiteralIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitBooleanLiteralIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitBooleanLiteralIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DoubleQuotedStringIdentifierContext extends IdentifierContext {
		public TerminalNode DoubleQuotedString() { return getToken(BusinessRulesParser.DoubleQuotedString, 0); }
		public DoubleQuotedStringIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterDoubleQuotedStringIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitDoubleQuotedStringIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitDoubleQuotedStringIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ModelReferenceIdentifierContext extends IdentifierContext {
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public ModelReferenceIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterModelReferenceIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitModelReferenceIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitModelReferenceIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumberIdentifierContext extends IdentifierContext {
		public TerminalNode Number() { return getToken(BusinessRulesParser.Number, 0); }
		public NumberIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterNumberIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitNumberIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitNumberIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CollectionIndexIdentifierContext extends IdentifierContext {
		public CollectionIndexContext collectionIndex() {
			return getRuleContext(CollectionIndexContext.class,0);
		}
		public CollectionIndexIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterCollectionIndexIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitCollectionIndexIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitCollectionIndexIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerNumberIdentifierContext extends IdentifierContext {
		public TerminalNode IntegerNumber() { return getToken(BusinessRulesParser.IntegerNumber, 0); }
		public IntegerNumberIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterIntegerNumberIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitIntegerNumberIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitIntegerNumberIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralStringIdentifierContext extends IdentifierContext {
		public TerminalNode LiteralString() { return getToken(BusinessRulesParser.LiteralString, 0); }
		public LiteralStringIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterLiteralStringIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitLiteralStringIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitLiteralStringIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_identifier);
		try {
			setState(268);
			switch (_input.LA(1)) {
			case ModelElementName:
				_localctx = new ModelReferenceIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
					setState(261);
				modelReference();
				}
				break;
			case LiteralString:
				_localctx = new LiteralStringIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
					setState(262);
				match(LiteralString);
				}
				break;
			case Number:
				_localctx = new NumberIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
					setState(263);
				match(Number);
				}
				break;
			case IntegerNumber:
				_localctx = new IntegerNumberIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
					setState(264);
				match(IntegerNumber);
				}
				break;
			case BooleanLiteral:
				_localctx = new BooleanLiteralIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
					setState(265);
				match(BooleanLiteral);
				}
				break;
			case OrdinalNumber:
				_localctx = new CollectionIndexIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
					setState(266);
				collectionIndex();
				}
				break;
			case DoubleQuotedString:
				_localctx = new DoubleQuotedStringIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
					setState(267);
				match(DoubleQuotedString);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionalExpressionContext extends ParserRuleContext {
		public FunctionalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionalExpression; }
	 
		public FunctionalExpressionContext() { }
		public void copyFrom(FunctionalExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NumberOfUniqueExpressionContext extends FunctionalExpressionContext {
		public Token op;
		public ModelReferenceContext ref;
		public ModelReferenceContext key;
		public List<ModelReferenceContext> modelReference() {
			return getRuleContexts(ModelReferenceContext.class);
		}
		public ModelReferenceContext modelReference(int i) {
			return getRuleContext(ModelReferenceContext.class,i);
		}
		public NumberOfUniqueExpressionContext(FunctionalExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterNumberOfUniqueExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitNumberOfUniqueExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitNumberOfUniqueExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SumOfExpressionContext extends FunctionalExpressionContext {
		public Token op;
		public ModelReferenceContext ref;
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public SumOfExpressionContext(FunctionalExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterSumOfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitSumOfExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitSumOfExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumberOfExpressionContext extends FunctionalExpressionContext {
		public Token op;
		public ModelReferenceContext ref;
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public NumberOfExpressionContext(FunctionalExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterNumberOfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitNumberOfExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitNumberOfExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionalExpressionContext functionalExpression() throws RecognitionException {
		FunctionalExpressionContext _localctx = new FunctionalExpressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_functionalExpression);
		try {
			setState(281);
			switch (_input.LA(1)) {
				case T__35:
				_localctx = new SumOfExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
					setState(270);
					((SumOfExpressionContext) _localctx).op = match(T__35);
					setState(271);
				((SumOfExpressionContext)_localctx).ref = modelReference();
				}
				break;
				case T__36:
				_localctx = new NumberOfExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
					setState(272);
					((NumberOfExpressionContext) _localctx).op = match(T__36);
					setState(273);
				((NumberOfExpressionContext)_localctx).ref = modelReference();
				}
				break;
				case T__37:
				_localctx = new NumberOfUniqueExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
					setState(274);
					((NumberOfUniqueExpressionContext) _localctx).op = match(T__37);
					setState(275);
					((NumberOfUniqueExpressionContext) _localctx).ref = modelReference();
					setState(276);
					match(T__11);
					setState(277);
					match(T__38);
				setState(278);
					((NumberOfUniqueExpressionContext) _localctx).key = modelReference();
				setState(279);
					match(T__12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorInvocationContext extends ParserRuleContext {
		public TerminalNode OperatorName() { return getToken(BusinessRulesParser.OperatorName, 0); }
		public OperatorParameterListContext operatorParameterList() {
			return getRuleContext(OperatorParameterListContext.class,0);
		}
		public OperatorInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorInvocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterOperatorInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitOperatorInvocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitOperatorInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorInvocationContext operatorInvocation() throws RecognitionException {
		OperatorInvocationContext _localctx = new OperatorInvocationContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_operatorInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(283);
				match(OperatorName);
				setState(285);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
					setState(284);
				operatorParameterList();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorParameterListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OperatorParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorParameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterOperatorParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitOperatorParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitOperatorParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorParameterListContext operatorParameterList() throws RecognitionException {
		OperatorParameterListContext _localctx = new OperatorParameterListContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_operatorParameterList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(287);
			expression(0);
				setState(292);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
						setState(288);
					_la = _input.LA(1);
						if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42))) != 0))) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
						setState(289);
					expression(0);
					}
					} 
				}
				setState(294);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefinitionApplicationContext extends ParserRuleContext {
		public TerminalNode FragmentName() { return getToken(BusinessRulesParser.FragmentName, 0); }
		public OperatorParameterListContext operatorParameterList() {
			return getRuleContext(OperatorParameterListContext.class,0);
		}
		public DefinitionApplicationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definitionApplication; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterDefinitionApplication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitDefinitionApplication(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitDefinitionApplication(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefinitionApplicationContext definitionApplication() throws RecognitionException {
		DefinitionApplicationContext _localctx = new DefinitionApplicationContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_definitionApplication);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(295);
			match(FragmentName);
				setState(296);
			operatorParameterList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CollectionIndexContext extends ParserRuleContext {
		public TerminalNode OrdinalNumber() { return getToken(BusinessRulesParser.OrdinalNumber, 0); }
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public CollectionIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collectionIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterCollectionIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitCollectionIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitCollectionIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CollectionIndexContext collectionIndex() throws RecognitionException {
		CollectionIndexContext _localctx = new CollectionIndexContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_collectionIndex);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(298);
				match(OrdinalNumber);
				setState(300);
			_la = _input.LA(1);
				if (_la == T__43) {
				{
					setState(299);
					match(T__43);
				}
			}

				setState(302);
			modelReference();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CastExpressionContext extends ParserRuleContext {
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public TerminalNode ModelElementName() { return getToken(BusinessRulesParser.ModelElementName, 0); }
		public CastExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_castExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterCastExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitCastExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitCastExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CastExpressionContext castExpression() throws RecognitionException {
		CastExpressionContext _localctx = new CastExpressionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_castExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(304);
			modelReference();
				setState(305);
			_la = _input.LA(1);
				if (!(_la == T__44 || _la == T__45)) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
				setState(306);
			match(ModelElementName);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectionExpressionContext extends ParserRuleContext {
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public SimpleOrComplexConstraintContext simpleOrComplexConstraint() {
			return getRuleContext(SimpleOrComplexConstraintContext.class,0);
		}
		public SelectionExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectionExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterSelectionExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitSelectionExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitSelectionExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectionExpressionContext selectionExpression() throws RecognitionException {
		SelectionExpressionContext _localctx = new SelectionExpressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_selectionExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(312);
			_la = _input.LA(1);
				if (_la == T__46) {
				{
					setState(308);
					match(T__46);
					setState(310);
				_la = _input.LA(1);
					if (_la == T__43) {
					{
						setState(309);
						match(T__43);
					}
				}

				}
			}

				setState(314);
				modelReference();
				setState(315);
				match(T__8);
				setState(316);
			simpleOrComplexConstraint();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModelReferenceContext extends ParserRuleContext {
		public Symbol symbol;
		public List<TerminalNode> path;
		public PropertyOfModelPathContext propPath;
		public DottedModelPathContext dotPath;
		public PropertyOfModelPathContext propertyOfModelPath() {
			return getRuleContext(PropertyOfModelPathContext.class,0);
		}
		public DottedModelPathContext dottedModelPath() {
			return getRuleContext(DottedModelPathContext.class,0);
		}
		public ModelReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modelReference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterModelReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitModelReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitModelReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelReferenceContext modelReference() throws RecognitionException {
		ModelReferenceContext _localctx = new ModelReferenceContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_modelReference);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(320);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
					setState(318);
				((ModelReferenceContext)_localctx).propPath = propertyOfModelPath();
				}
				break;
			case 2:
				{
					setState(319);
				((ModelReferenceContext)_localctx).dotPath = dottedModelPath();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModelReferenceListContext extends ParserRuleContext {
		public List<ModelReferenceContext> modelReference() {
			return getRuleContexts(ModelReferenceContext.class);
		}
		public ModelReferenceContext modelReference(int i) {
			return getRuleContext(ModelReferenceContext.class,i);
		}
		public ModelReferenceListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modelReferenceList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterModelReferenceList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitModelReferenceList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitModelReferenceList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelReferenceListContext modelReferenceList() throws RecognitionException {
		ModelReferenceListContext _localctx = new ModelReferenceListContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_modelReferenceList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(323);
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
					setState(322);
				modelReference();
				}
				}
				setState(325); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ModelElementName );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DottedModelPathContext extends ParserRuleContext {
		public List<TerminalNode> ModelElementName() { return getTokens(BusinessRulesParser.ModelElementName); }
		public TerminalNode ModelElementName(int i) {
			return getToken(BusinessRulesParser.ModelElementName, i);
		}
		public DottedModelPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dottedModelPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterDottedModelPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitDottedModelPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitDottedModelPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DottedModelPathContext dottedModelPath() throws RecognitionException {
		DottedModelPathContext _localctx = new DottedModelPathContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_dottedModelPath);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(327);
			match(ModelElementName);
				setState(332);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
						setState(328);
						match(T__47);
						setState(329);
					match(ModelElementName);
					}
					} 
				}
				setState(334);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			}
				((ModelReferenceContext) getInvokingContext(30)).path = _localctx.getTokens(ModelElementName);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyOfModelPathContext extends ParserRuleContext {
		public List<TerminalNode> ModelElementName() { return getTokens(BusinessRulesParser.ModelElementName); }
		public TerminalNode ModelElementName(int i) {
			return getToken(BusinessRulesParser.ModelElementName, i);
		}
		public PropertyOfModelPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyOfModelPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterPropertyOfModelPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitPropertyOfModelPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitPropertyOfModelPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyOfModelPathContext propertyOfModelPath() throws RecognitionException {
		PropertyOfModelPathContext _localctx = new PropertyOfModelPathContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_propertyOfModelPath);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(337);
			match(ModelElementName);
				setState(340);
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
						setState(338);
						match(T__43);
						setState(339);
					match(ModelElementName);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(342); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );

				((ModelReferenceContext) getInvokingContext(30)).path = _localctx.getTokens(ModelElementName);
				Collections.reverse(((ModelReferenceContext) getInvokingContext(30)).path);

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundReportContext extends ParserRuleContext {
		public List<SimpleReportContext> simpleReport() {
			return getRuleContexts(SimpleReportContext.class);
		}
		public SimpleReportContext simpleReport(int i) {
			return getRuleContext(SimpleReportContext.class,i);
		}
		public CompoundReportContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundReport; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterCompoundReport(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitCompoundReport(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitCompoundReport(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompoundReportContext compoundReport() throws RecognitionException {
		CompoundReportContext _localctx = new CompoundReportContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_compoundReport);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(346);
			simpleReport();
				setState(353);
			_errHandler.sync(this);
			_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__10) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__48) | (1L << T__49))) != 0) || ((((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & ((1L << (BooleanLiteral - 82)) | (1L << (LiteralString - 82)) | (1L << (DoubleQuotedString - 82)) | (1L << (ModelElementName - 82)) | (1L << (Number - 82)) | (1L << (OrdinalNumber - 82)) | (1L << (IntegerNumber - 82)) | (1L << (OperatorName - 82)))) != 0)) {
				{
				{
					setState(348);
				_la = _input.LA(1);
					if (_la == T__10) {
					{
						setState(347);
						match(T__10);
					}
				}

					setState(350);
				simpleReport();
				}
				}
					setState(355);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleReportContext extends ParserRuleContext {
		public ConcatenatedReportContext concatenatedReport() {
			return getRuleContext(ConcatenatedReportContext.class,0);
		}
		public ConditionalReportContext conditionalReport() {
			return getRuleContext(ConditionalReportContext.class,0);
		}
		public SimpleReportContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleReport; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterSimpleReport(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitSimpleReport(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitSimpleReport(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleReportContext simpleReport() throws RecognitionException {
		SimpleReportContext _localctx = new SimpleReportContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_simpleReport);
		try {
			setState(358);
			switch (_input.LA(1)) {
			case T__35:
				case T__36:
				case T__37:
				case T__48:
			case BooleanLiteral:
			case LiteralString:
			case DoubleQuotedString:
			case ModelElementName:
			case Number:
			case OrdinalNumber:
			case IntegerNumber:
			case OperatorName:
				enterOuterAlt(_localctx, 1);
				{
					setState(356);
				concatenatedReport();
				}
				break;
				case T__49:
				enterOuterAlt(_localctx, 2);
				{
					setState(357);
				conditionalReport();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConcatenatedReportContext extends ParserRuleContext {
		public List<SimpleTermContext> simpleTerm() {
			return getRuleContexts(SimpleTermContext.class);
		}
		public SimpleTermContext simpleTerm(int i) {
			return getRuleContext(SimpleTermContext.class,i);
		}
		public ConcatenatedReportContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_concatenatedReport; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterConcatenatedReport(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitConcatenatedReport(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitConcatenatedReport(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConcatenatedReportContext concatenatedReport() throws RecognitionException {
		ConcatenatedReportContext _localctx = new ConcatenatedReportContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_concatenatedReport);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(361);
			_la = _input.LA(1);
				if (_la == T__48) {
				{
					setState(360);
					match(T__48);
				}
			}

				setState(363);
			simpleTerm();
				setState(370);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
						setState(365);
					_la = _input.LA(1);
						if (_la == T__32) {
						{
							setState(364);
							match(T__32);
						}
					}

						setState(367);
					simpleTerm();
					}
					} 
				}
				setState(372);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalReportContext extends ParserRuleContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public List<CompoundReportContext> compoundReport() {
			return getRuleContexts(CompoundReportContext.class);
		}
		public CompoundReportContext compoundReport(int i) {
			return getRuleContext(CompoundReportContext.class,i);
		}
		public ConditionalReportContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalReport; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterConditionalReport(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitConditionalReport(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitConditionalReport(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalReportContext conditionalReport() throws RecognitionException {
		ConditionalReportContext _localctx = new ConditionalReportContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_conditionalReport);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(373);
				match(T__49);
				setState(374);
				constraint();
				setState(375);
				match(T__15);
				setState(376);
			compoundReport();
				setState(379);
			_la = _input.LA(1);
				if (_la == T__16) {
				{
					setState(377);
					match(T__16);
					setState(378);
				compoundReport();
				}
			}

				setState(381);
				match(T__50);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleTermContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public FunctionalExpressionContext functionalExpression() {
			return getRuleContext(FunctionalExpressionContext.class,0);
		}
		public OperatorInvocationContext operatorInvocation() {
			return getRuleContext(OperatorInvocationContext.class,0);
		}
		public SimpleTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterSimpleTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitSimpleTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitSimpleTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleTermContext simpleTerm() throws RecognitionException {
		SimpleTermContext _localctx = new SimpleTermContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_simpleTerm);
		try {
			setState(386);
			switch (_input.LA(1)) {
			case BooleanLiteral:
			case LiteralString:
			case DoubleQuotedString:
			case ModelElementName:
			case Number:
			case OrdinalNumber:
			case IntegerNumber:
				enterOuterAlt(_localctx, 1);
				{
					setState(383);
				identifier();
				}
				break;
			case T__35:
				case T__36:
				case T__37:
				enterOuterAlt(_localctx, 2);
				{
					setState(384);
				functionalExpression();
				}
				break;
			case OperatorName:
				enterOuterAlt(_localctx, 3);
				{
					setState(385);
				operatorInvocation();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CollectionMemberConstraintContext extends ParserRuleContext {
		public ModelReferenceContext reference;
		public SimpleOrComplexConstraintContext simpleOrComplexConstraint() {
			return getRuleContext(SimpleOrComplexConstraintContext.class,0);
		}
		public CollectionMemberConstraintContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public CollectionMemberConstraintContext(ParserRuleContext parent, int invokingState, ModelReferenceContext reference) {
			super(parent, invokingState);
			this.reference = reference;
		}
		@Override public int getRuleIndex() { return RULE_collectionMemberConstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterCollectionMemberConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitCollectionMemberConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitCollectionMemberConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CollectionMemberConstraintContext collectionMemberConstraint(ModelReferenceContext reference) throws RecognitionException {
		CollectionMemberConstraintContext _localctx = new CollectionMemberConstraintContext(_ctx, getState(), reference);
		enterRule(_localctx, 78, RULE_collectionMemberConstraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(388);
			simpleOrComplexConstraint();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExistsStatementContext extends ParserRuleContext {
		public ExistsStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_existsStatement; }
	 
		public ExistsStatementContext() { }
		public void copyFrom(ExistsStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ConstrainedCollectionMembershipContext extends ExistsStatementContext {
		public ModelReferenceContext ref;
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public CollectionMemberConstraintContext collectionMemberConstraint() {
			return getRuleContext(CollectionMemberConstraintContext.class,0);
		}
		public EnumeratorContext enumerator() {
			return getRuleContext(EnumeratorContext.class,0);
		}
		public ConstrainedCollectionMembershipContext(ExistsStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterConstrainedCollectionMembership(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitConstrainedCollectionMembership(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitConstrainedCollectionMembership(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SimpleExistsContext extends ExistsStatementContext {
		public EnumeratorContext enumerator() {
			return getRuleContext(EnumeratorContext.class,0);
		}
		public SimpleOrComplexConstraintContext simpleOrComplexConstraint() {
			return getRuleContext(SimpleOrComplexConstraintContext.class,0);
		}
		public SimpleExistsContext(ExistsStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterSimpleExists(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitSimpleExists(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitSimpleExists(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExistsStatementContext existsStatement() throws RecognitionException {
		ExistsStatementContext _localctx = new ExistsStatementContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_existsStatement);
		int _la;
		try {
			setState(406);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				_localctx = new ConstrainedCollectionMembershipContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
					setState(394);
				_la = _input.LA(1);
					if (((((_la - 58)) & ~0x3f) == 0 && ((1L << (_la - 58)) & ((1L << (T__57 - 58)) | (1L << (T__58 - 58)) | (1L << (T__59 - 58)) | (1L << (T__60 - 58)) | (1L << (T__61 - 58)) | (1L << (T__62 - 58)) | (1L << (T__63 - 58)) | (1L << (T__64 - 58)) | (1L << (T__65 - 58)) | (1L << (IntegerNumber - 58)))) != 0)) {
					{
						setState(390);
						enumerator();
						setState(392);
					_la = _input.LA(1);
						if (_la == T__51) {
						{
							setState(391);
							match(T__51);
						}
					}

					}
				}

					setState(396);
				((ConstrainedCollectionMembershipContext)_localctx).ref = modelReference();
					setState(397);
				_la = _input.LA(1);
					if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55))) != 0))) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
					setState(400);
				switch (_input.LA(1)) {
					case T__56:
					{
						setState(398);
						match(T__56);
					}
					break;
					case T__11:
				case T__35:
					case T__36:
					case T__37:
					case T__46:
				case FragmentName:
				case BooleanLiteral:
				case LiteralString:
				case DoubleQuotedString:
				case ModelElementName:
				case Number:
				case OrdinalNumber:
				case IntegerNumber:
				case OperatorName:
					{
						setState(399);
					collectionMemberConstraint(((ConstrainedCollectionMembershipContext)_localctx).ref);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 2:
				_localctx = new SimpleExistsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
					setState(402);
				enumerator();
					setState(403);
				_la = _input.LA(1);
					if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55))) != 0))) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
					setState(404);
				simpleOrComplexConstraint();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumeratorContext extends ParserRuleContext {
		public Token at_least;
		public Token at_most;
		public Token exactly;
		public Token one;
		public Token two;
		public Token three;
		public Token four;
		public Token no;
		public Token none;
		public Token integer;
		public TerminalNode IntegerNumber() { return getToken(BusinessRulesParser.IntegerNumber, 0); }
		public EnumeratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumerator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterEnumerator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitEnumerator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitEnumerator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumeratorContext enumerator() throws RecognitionException {
		EnumeratorContext _localctx = new EnumeratorContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_enumerator);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(411);
			switch (_input.LA(1)) {
				case T__57:
				{
					setState(408);
					((EnumeratorContext) _localctx).at_least = match(T__57);
				}
				break;
				case T__58:
				{
					setState(409);
					((EnumeratorContext) _localctx).at_most = match(T__58);
				}
				break;
				case T__59:
				{
					setState(410);
					((EnumeratorContext) _localctx).exactly = match(T__59);
				}
				break;
			case T__60:
			case T__61:
			case T__62:
			case T__63:
				case T__64:
				case T__65:
			case IntegerNumber:
				break;
			default:
				throw new NoViableAltException(this);
			}
				setState(420);
			switch (_input.LA(1)) {
			case T__60:
				{
					setState(413);
					((EnumeratorContext) _localctx).one = match(T__60);
				}
				break;
			case T__61:
				{
					setState(414);
					((EnumeratorContext) _localctx).two = match(T__61);
				}
				break;
			case T__62:
				{
					setState(415);
					((EnumeratorContext) _localctx).three = match(T__62);
				}
				break;
			case T__63:
				{
					setState(416);
					((EnumeratorContext) _localctx).four = match(T__63);
				}
				break;
				case T__64:
				{
					setState(417);
					((EnumeratorContext) _localctx).no = match(T__64);
				}
				break;
				case T__65: {
					setState(418);
					((EnumeratorContext) _localctx).none = match(T__65);
				}
				break;
				case IntegerNumber: {
					setState(419);
				((EnumeratorContext)_localctx).integer = match(IntegerNumber);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotExistsStatementContext extends ParserRuleContext {
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public NotExistsStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notExistsStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterNotExistsStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitNotExistsStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitNotExistsStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotExistsStatementContext notExistsStatement() throws RecognitionException {
		NotExistsStatementContext _localctx = new NotExistsStatementContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_notExistsStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(422);
			modelReference();
				setState(423);
			_la = _input.LA(1);
				if (!(_la == T__28 || _la == T__29)) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForallStatementContext extends ParserRuleContext {
		public ModelReferenceContext modelReference() {
			return getRuleContext(ModelReferenceContext.class,0);
		}
		public SimpleOrComplexConstraintContext simpleOrComplexConstraint() {
			return getRuleContext(SimpleOrComplexConstraintContext.class,0);
		}
		public TerminalNode DoubleQuotedString() { return getToken(BusinessRulesParser.DoubleQuotedString, 0); }
		public ForallStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forallStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).enterForallStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BusinessRulesListener ) ((BusinessRulesListener)listener).exitForallStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BusinessRulesVisitor ) return ((BusinessRulesVisitor<? extends T>)visitor).visitForallStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForallStatementContext forallStatement() throws RecognitionException {
		ForallStatementContext _localctx = new ForallStatementContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_forallStatement);
		int _la;
		try {
			setState(444);
			switch (_input.LA(1)) {
			case T__66:
			case T__67:
				case T__68:
				case T__69:
				enterOuterAlt(_localctx, 1);
				{
					setState(425);
				_la = _input.LA(1);
					if (!(((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (T__66 - 67)) | (1L << (T__67 - 67)) | (1L << (T__68 - 67)) | (1L << (T__69 - 67)))) != 0))) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
					setState(427);
				_la = _input.LA(1);
					if (_la == T__51) {
					{
						setState(426);
						match(T__51);
					}
				}

					setState(429);
					modelReference();
					setState(431);
				_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55))) != 0)) {
					{
						setState(430);
					_la = _input.LA(1);
						if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55))) != 0))) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
				}

					setState(433);
				simpleOrComplexConstraint();
				}
				break;
				case T__70:
				enterOuterAlt(_localctx, 2);
				{
					setState(435);
					match(T__70);
					setState(436);
					match(DoubleQuotedString);
					setState(437);
					match(T__71);
					setState(438);
					modelReference();
				setState(440);
				_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__10) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55))) != 0)) {
					{
						setState(439);
					_la = _input.LA(1);
						if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__10) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55))) != 0))) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
				}

					setState(442);
				simpleOrComplexConstraint();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
			case 13:
			return logicalStatement_sempred((LogicalStatementContext)_localctx, predIndex);
			case 20:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean logicalStatement_sempred(LogicalStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
			"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3_\u01c1\4\2\t\2\4" +
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
					",\t,\4-\t-\3\2\3\2\3\2\3\3\6\3_\n\3\r\3\16\3`\3\4\3\4\3\4\3\5\7\5g\n\5" +
					"\f\5\16\5j\13\5\3\6\3\6\5\6n\n\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7v\n\7\3\b" +
					"\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0086\n\t\3\n" +
					"\3\n\3\n\3\13\3\13\3\13\7\13\u008e\n\13\f\13\16\13\u0091\13\13\3\f\3\f" +
					"\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u009f\n\r\3\r\5\r\u00a2\n" +
					"\r\3\16\3\16\3\16\3\16\5\16\u00a8\n\16\3\17\3\17\3\17\3\17\3\17\5\17\u00af" +
					"\n\17\3\17\3\17\3\17\3\17\7\17\u00b5\n\17\f\17\16\17\u00b8\13\17\3\20" +
					"\3\20\3\20\3\20\3\20\5\20\u00bf\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21" +
					"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00d2\n\21\3\22" +
					"\3\22\3\22\3\22\3\22\3\22\5\22\u00da\n\22\3\23\3\23\3\23\7\23\u00df\n" +
					"\23\f\23\16\23\u00e2\13\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25" +
					"\3\25\3\26\3\26\3\26\3\26\3\26\3\26\7\26\u00f4\n\26\f\26\16\26\u00f7\13" +
					"\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5" +
					"\27\u0106\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u010f\n\30\3\31" +
					"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u011c\n\31\3\32" +
					"\3\32\5\32\u0120\n\32\3\33\3\33\3\33\7\33\u0125\n\33\f\33\16\33\u0128" +
					"\13\33\3\34\3\34\3\34\3\35\3\35\5\35\u012f\n\35\3\35\3\35\3\36\3\36\3" +
					"\36\3\36\3\37\3\37\5\37\u0139\n\37\5\37\u013b\n\37\3\37\3\37\3\37\3\37" +
					"\3 \3 \5 \u0143\n \3!\6!\u0146\n!\r!\16!\u0147\3\"\3\"\3\"\7\"\u014d\n" +
					"\"\f\"\16\"\u0150\13\"\3\"\3\"\3#\3#\3#\6#\u0157\n#\r#\16#\u0158\3#\3" +
					"#\3$\3$\5$\u015f\n$\3$\7$\u0162\n$\f$\16$\u0165\13$\3%\3%\5%\u0169\n%" +
					"\3&\5&\u016c\n&\3&\3&\5&\u0170\n&\3&\7&\u0173\n&\f&\16&\u0176\13&\3\'" +
					"\3\'\3\'\3\'\3\'\3\'\5\'\u017e\n\'\3\'\3\'\3(\3(\3(\5(\u0185\n(\3)\3)" +
					"\3*\3*\5*\u018b\n*\5*\u018d\n*\3*\3*\3*\3*\5*\u0193\n*\3*\3*\3*\3*\5*" +
					"\u0199\n*\3+\3+\3+\5+\u019e\n+\3+\3+\3+\3+\3+\3+\3+\5+\u01a7\n+\3,\3," +
					"\3,\3-\3-\5-\u01ae\n-\3-\3-\5-\u01b2\n-\3-\3-\3-\3-\3-\3-\3-\5-\u01bb" +
					"\n-\3-\3-\5-\u01bf\n-\3-\2\4\34*.\2\4\6\b\n\f\16\20\22\24\26\30\32\34" +
					"\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVX\2\n\3\2\34\35\3\2\37 \3\2!%" +
					"\4\2\24\24*-\3\2/\60\3\2\67:\3\2EH\4\2\r\r\67:\u01e1\2Z\3\2\2\2\4^\3\2" +
					"\2\2\6b\3\2\2\2\bh\3\2\2\2\nm\3\2\2\2\fo\3\2\2\2\16w\3\2\2\2\20~\3\2\2" +
					"\2\22\u0087\3\2\2\2\24\u008a\3\2\2\2\26\u0092\3\2\2\2\30\u0097\3\2\2\2" +
					"\32\u00a7\3\2\2\2\34\u00ae\3\2\2\2\36\u00be\3\2\2\2 \u00d1\3\2\2\2\"\u00d9" +
					"\3\2\2\2$\u00db\3\2\2\2&\u00e3\3\2\2\2(\u00e8\3\2\2\2*\u00ed\3\2\2\2," +
					"\u0105\3\2\2\2.\u010e\3\2\2\2\60\u011b\3\2\2\2\62\u011d\3\2\2\2\64\u0121" +
					"\3\2\2\2\66\u0129\3\2\2\28\u012c\3\2\2\2:\u0132\3\2\2\2<\u013a\3\2\2\2" +
					">\u0142\3\2\2\2@\u0145\3\2\2\2B\u0149\3\2\2\2D\u0153\3\2\2\2F\u015c\3" +
					"\2\2\2H\u0168\3\2\2\2J\u016b\3\2\2\2L\u0177\3\2\2\2N\u0184\3\2\2\2P\u0186" +
					"\3\2\2\2R\u0198\3\2\2\2T\u019d\3\2\2\2V\u01a8\3\2\2\2X\u01be\3\2\2\2Z" +
					"[\5\4\3\2[\\\5\b\5\2\\\3\3\2\2\2]_\5\6\4\2^]\3\2\2\2_`\3\2\2\2`^\3\2\2" +
					"\2`a\3\2\2\2a\5\3\2\2\2bc\7\3\2\2cd\7V\2\2d\7\3\2\2\2eg\5\n\6\2fe\3\2" +
					"\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2i\t\3\2\2\2jh\3\2\2\2kn\5\16\b\2ln\5" +
					"\f\7\2mk\3\2\2\2ml\3\2\2\2n\13\3\2\2\2op\7\4\2\2pq\7V\2\2qr\5\22\n\2r" +
					"u\5\30\r\2st\7\5\2\2tv\5F$\2us\3\2\2\2uv\3\2\2\2v\r\3\2\2\2wx\7\6\2\2" +
					"xy\7V\2\2yz\7\7\2\2z{\5\24\13\2{|\7\b\2\2|}\5 \21\2}\17\3\2\2\2~\177\7" +
					"\t\2\2\177\u0085\7V\2\2\u0080\u0081\7\n\2\2\u0081\u0082\5> \2\u0082\u0083" +
					"\7\13\2\2\u0083\u0084\5\30\r\2\u0084\u0086\3\2\2\2\u0085\u0080\3\2\2\2" +
					"\u0085\u0086\3\2\2\2\u0086\21\3\2\2\2\u0087\u0088\7\f\2\2\u0088\u0089" +
					"\5\26\f\2\u0089\23\3\2\2\2\u008a\u008f\5\26\f\2\u008b\u008c\7\r\2\2\u008c" +
					"\u008e\5\26\f\2\u008d\u008b\3\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3" +
					"\2\2\2\u008f\u0090\3\2\2\2\u0090\25\3\2\2\2\u0091\u008f\3\2\2\2\u0092" +
					"\u0093\5> \2\u0093\u0094\7\16\2\2\u0094\u0095\7V\2\2\u0095\u0096\7\17" +
					"\2\2\u0096\27\3\2\2\2\u0097\u00a1\7\20\2\2\u0098\u0099\7\21\2\2\u0099" +
					"\u009a\5\34\17\2\u009a\u009b\7\22\2\2\u009b\u009e\5\34\17\2\u009c\u009d" +
					"\7\23\2\2\u009d\u009f\5\34\17\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2" +
					"\2\u009f\u00a2\3\2\2\2\u00a0\u00a2\5\34\17\2\u00a1\u0098\3\2\2\2\u00a1" +
					"\u00a0\3\2\2\2\u00a2\31\3\2\2\2\u00a3\u00a8\7\24\2\2\u00a4\u00a8\7\25" +
					"\2\2\u00a5\u00a8\7\26\2\2\u00a6\u00a8\7\27\2\2\u00a7\u00a3\3\2\2\2\u00a7" +
					"\u00a4\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a6\3\2\2\2\u00a8\33\3\2\2" +
					"\2\u00a9\u00aa\b\17\1\2\u00aa\u00af\5 \21\2\u00ab\u00af\5R*\2\u00ac\u00af" +
					"\5V,\2\u00ad\u00af\5X-\2\u00ae\u00a9\3\2\2\2\u00ae\u00ab\3\2\2\2\u00ae" +
					"\u00ac\3\2\2\2\u00ae\u00ad\3\2\2\2\u00af\u00b6\3\2\2\2\u00b0\u00b1\f\7" +
					"\2\2\u00b1\u00b2\5\32\16\2\u00b2\u00b3\5\34\17\b\u00b3\u00b5\3\2\2\2\u00b4" +
					"\u00b0\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6\u00b7\3\2" +
					"\2\2\u00b7\35\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00ba\7\16\2\2\u00ba\u00bb" +
					"\5\30\r\2\u00bb\u00bc\7\17\2\2\u00bc\u00bf\3\2\2\2\u00bd\u00bf\5 \21\2" +
					"\u00be\u00b9\3\2\2\2\u00be\u00bd\3\2\2\2\u00bf\37\3\2\2\2\u00c0\u00c1" +
					"\5*\26\2\u00c1\u00c2\5\"\22\2\u00c2\u00c3\5*\26\2\u00c3\u00d2\3\2\2\2" +
					"\u00c4\u00c5\5*\26\2\u00c5\u00c6\7\30\2\2\u00c6\u00c7\5$\23\2\u00c7\u00d2" +
					"\3\2\2\2\u00c8\u00c9\5*\26\2\u00c9\u00ca\7\31\2\2\u00ca\u00cb\5$\23\2" +
					"\u00cb\u00d2\3\2\2\2\u00cc\u00cd\5> \2\u00cd\u00ce\7\32\2\2\u00ce\u00cf" +
					"\7W\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00d2\5*\26\2\u00d1\u00c0\3\2\2\2\u00d1" +
					"\u00c4\3\2\2\2\u00d1\u00c8\3\2\2\2\u00d1\u00cc\3\2\2\2\u00d1\u00d0\3\2" +
					"\2\2\u00d2!\3\2\2\2\u00d3\u00da\7N\2\2\u00d4\u00da\7O\2\2\u00d5\u00da" +
					"\7P\2\2\u00d6\u00da\7R\2\2\u00d7\u00da\7Q\2\2\u00d8\u00da\7S\2\2\u00d9" +
					"\u00d3\3\2\2\2\u00d9\u00d4\3\2\2\2\u00d9\u00d5\3\2\2\2\u00d9\u00d6\3\2" +
					"\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00d8\3\2\2\2\u00da#\3\2\2\2\u00db\u00e0" +
					"\5.\30\2\u00dc\u00dd\7\r\2\2\u00dd\u00df\5.\30\2\u00de\u00dc\3\2\2\2\u00df" +
					"\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1%\3\2\2\2" +
					"\u00e2\u00e0\3\2\2\2\u00e3\u00e4\7\33\2\2\u00e4\u00e5\t\2\2\2\u00e5\u00e6" +
					"\7\36\2\2\u00e6\u00e7\5@!\2\u00e7\'\3\2\2\2\u00e8\u00e9\7\33\2\2\u00e9" +
					"\u00ea\t\3\2\2\u00ea\u00eb\7\36\2\2\u00eb\u00ec\5@!\2\u00ec)\3\2\2\2\u00ed" +
					"\u00ee\b\26\1\2\u00ee\u00ef\5,\27\2\u00ef\u00f5\3\2\2\2\u00f0\u00f1\f" +
					"\4\2\2\u00f1\u00f2\t\4\2\2\u00f2\u00f4\5*\26\5\u00f3\u00f0\3\2\2\2\u00f4" +
					"\u00f7\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6+\3\2\2\2" +
					"\u00f7\u00f5\3\2\2\2\u00f8\u0106\5.\30\2\u00f9\u0106\5\60\31\2\u00fa\u00fb" +
					"\5> \2\u00fb\u00fc\7M\2\2\u00fc\u0106\3\2\2\2\u00fd\u0106\5\62\32\2\u00fe" +
					"\u0106\5\66\34\2\u00ff\u0106\5:\36\2\u0100\u0106\5<\37\2\u0101\u0102\7" +
					"\16\2\2\u0102\u0103\5\30\r\2\u0103\u0104\7\17\2\2\u0104\u0106\3\2\2\2" +
					"\u0105\u00f8\3\2\2\2\u0105\u00f9\3\2\2\2\u0105\u00fa\3\2\2\2\u0105\u00fd" +
					"\3\2\2\2\u0105\u00fe\3\2\2\2\u0105\u00ff\3\2\2\2\u0105\u0100\3\2\2\2\u0105" +
					"\u0101\3\2\2\2\u0106-\3\2\2\2\u0107\u010f\5> \2\u0108\u010f\7U\2\2\u0109" +
					"\u010f\7Y\2\2\u010a\u010f\7[\2\2\u010b\u010f\7T\2\2\u010c\u010f\58\35" +
					"\2\u010d\u010f\7V\2\2\u010e\u0107\3\2\2\2\u010e\u0108\3\2\2\2\u010e\u0109" +
					"\3\2\2\2\u010e\u010a\3\2\2\2\u010e\u010b\3\2\2\2\u010e\u010c\3\2\2\2\u010e" +
					"\u010d\3\2\2\2\u010f/\3\2\2\2\u0110\u0111\7&\2\2\u0111\u011c\5> \2\u0112" +
					"\u0113\7\'\2\2\u0113\u011c\5> \2\u0114\u0115\7(\2\2\u0115\u0116\5> \2" +
					"\u0116\u0117\7\16\2\2\u0117\u0118\7)\2\2\u0118\u0119\5> \2\u0119\u011a" +
					"\7\17\2\2\u011a\u011c\3\2\2\2\u011b\u0110\3\2\2\2\u011b\u0112\3\2\2\2" +
					"\u011b\u0114\3\2\2\2\u011c\61\3\2\2\2\u011d\u011f\7\\\2\2\u011e\u0120" +
					"\5\64\33\2\u011f\u011e\3\2\2\2\u011f\u0120\3\2\2\2\u0120\63\3\2\2\2\u0121" +
					"\u0126\5*\26\2\u0122\u0123\t\5\2\2\u0123\u0125\5*\26\2\u0124\u0122\3\2" +
					"\2\2\u0125\u0128\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127" +
					"\65\3\2\2\2\u0128\u0126\3\2\2\2\u0129\u012a\7M\2\2\u012a\u012b\5\64\33" +
					"\2\u012b\67\3\2\2\2\u012c\u012e\7Z\2\2\u012d\u012f\7.\2\2\u012e\u012d" +
					"\3\2\2\2\u012e\u012f\3\2\2\2\u012f\u0130\3\2\2\2\u0130\u0131\5> \2\u0131" +
					"9\3\2\2\2\u0132\u0133\5> \2\u0133\u0134\t\6\2\2\u0134\u0135\7W\2\2\u0135" +
					";\3\2\2\2\u0136\u0138\7\61\2\2\u0137\u0139\7.\2\2\u0138\u0137\3\2\2\2" +
					"\u0138\u0139\3\2\2\2\u0139\u013b\3\2\2\2\u013a\u0136\3\2\2\2\u013a\u013b" +
					"\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013d\5> \2\u013d\u013e\7\13\2\2\u013e" +
					"\u013f\5\36\20\2\u013f=\3\2\2\2\u0140\u0143\5D#\2\u0141\u0143\5B\"\2\u0142" +
					"\u0140\3\2\2\2\u0142\u0141\3\2\2\2\u0143?\3\2\2\2\u0144\u0146\5> \2\u0145" +
					"\u0144\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u0145\3\2\2\2\u0147\u0148\3\2" +
					"\2\2\u0148A\3\2\2\2\u0149\u014e\7W\2\2\u014a\u014b\7\62\2\2\u014b\u014d" +
					"\7W\2\2\u014c\u014a\3\2\2\2\u014d\u0150\3\2\2\2\u014e\u014c\3\2\2\2\u014e" +
					"\u014f\3\2\2\2\u014f\u0151\3\2\2\2\u0150\u014e\3\2\2\2\u0151\u0152\b\"" +
					"\1\2\u0152C\3\2\2\2\u0153\u0156\7W\2\2\u0154\u0155\7.\2\2\u0155\u0157" +
					"\7W\2\2\u0156\u0154\3\2\2\2\u0157\u0158\3\2\2\2\u0158\u0156\3\2\2\2\u0158" +
					"\u0159\3\2\2\2\u0159\u015a\3\2\2\2\u015a\u015b\b#\1\2\u015bE\3\2\2\2\u015c" +
					"\u0163\5H%\2\u015d\u015f\7\r\2\2\u015e\u015d\3\2\2\2\u015e\u015f\3\2\2" +
					"\2\u015f\u0160\3\2\2\2\u0160\u0162\5H%\2\u0161\u015e\3\2\2\2\u0162\u0165" +
					"\3\2\2\2\u0163\u0161\3\2\2\2\u0163\u0164\3\2\2\2\u0164G\3\2\2\2\u0165" +
					"\u0163\3\2\2\2\u0166\u0169\5J&\2\u0167\u0169\5L\'\2\u0168\u0166\3\2\2" +
					"\2\u0168\u0167\3\2\2\2\u0169I\3\2\2\2\u016a\u016c\7\63\2\2\u016b\u016a" +
					"\3\2\2\2\u016b\u016c\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u0174\5N(\2\u016e" +
					"\u0170\7#\2\2\u016f\u016e\3\2\2\2\u016f\u0170\3\2\2\2\u0170\u0171\3\2" +
					"\2\2\u0171\u0173\5N(\2\u0172\u016f\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172" +
					"\3\2\2\2\u0174\u0175\3\2\2\2\u0175K\3\2\2\2\u0176\u0174\3\2\2\2\u0177" +
					"\u0178\7\64\2\2\u0178\u0179\5\30\r\2\u0179\u017a\7\22\2\2\u017a\u017d" +
					"\5F$\2\u017b\u017c\7\23\2\2\u017c\u017e\5F$\2\u017d\u017b\3\2\2\2\u017d" +
					"\u017e\3\2\2\2\u017e\u017f\3\2\2\2\u017f\u0180\7\65\2\2\u0180M\3\2\2\2" +
					"\u0181\u0185\5.\30\2\u0182\u0185\5\60\31\2\u0183\u0185\5\62\32\2\u0184" +
					"\u0181\3\2\2\2\u0184\u0182\3\2\2\2\u0184\u0183\3\2\2\2\u0185O\3\2\2\2" +
					"\u0186\u0187\5\36\20\2\u0187Q\3\2\2\2\u0188\u018a\5T+\2\u0189\u018b\7" +
					"\66\2\2\u018a\u0189\3\2\2\2\u018a\u018b\3\2\2\2\u018b\u018d\3\2\2\2\u018c" +
					"\u0188\3\2\2\2\u018c\u018d\3\2\2\2\u018d\u018e\3\2\2\2\u018e\u018f\5>" +
					" \2\u018f\u0192\t\7\2\2\u0190\u0193\7;\2\2\u0191\u0193\5P)\2\u0192\u0190" +
					"\3\2\2\2\u0192\u0191\3\2\2\2\u0193\u0199\3\2\2\2\u0194\u0195\5T+\2\u0195" +
					"\u0196\t\7\2\2\u0196\u0197\5\36\20\2\u0197\u0199\3\2\2\2\u0198\u018c\3" +
					"\2\2\2\u0198\u0194\3\2\2\2\u0199S\3\2\2\2\u019a\u019e\7<\2\2\u019b\u019e" +
					"\7=\2\2\u019c\u019e\7>\2\2\u019d\u019a\3\2\2\2\u019d\u019b\3\2\2\2\u019d" +
					"\u019c\3\2\2\2\u019d\u019e\3\2\2\2\u019e\u01a6\3\2\2\2\u019f\u01a7\7?" +
					"\2\2\u01a0\u01a7\7@\2\2\u01a1\u01a7\7A\2\2\u01a2\u01a7\7B\2\2\u01a3\u01a7" +
					"\7C\2\2\u01a4\u01a7\7D\2\2\u01a5\u01a7\7[\2\2\u01a6\u019f\3\2\2\2\u01a6" +
					"\u01a0\3\2\2\2\u01a6\u01a1\3\2\2\2\u01a6\u01a2\3\2\2\2\u01a6\u01a3\3\2" +
					"\2\2\u01a6\u01a4\3\2\2\2\u01a6\u01a5\3\2\2\2\u01a7U\3\2\2\2\u01a8\u01a9" +
					"\5> \2\u01a9\u01aa\t\3\2\2\u01aaW\3\2\2\2\u01ab\u01ad\t\b\2\2\u01ac\u01ae" +
					"\7\66\2\2\u01ad\u01ac\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01af\3\2\2\2" +
					"\u01af\u01b1\5> \2\u01b0\u01b2\t\7\2\2\u01b1\u01b0\3\2\2\2\u01b1\u01b2" +
					"\3\2\2\2\u01b2\u01b3\3\2\2\2\u01b3\u01b4\5\36\20\2\u01b4\u01bf\3\2\2\2" +
					"\u01b5\u01b6\7I\2\2\u01b6\u01b7\7V\2\2\u01b7\u01b8\7J\2\2\u01b8\u01ba" +
					"\5> \2\u01b9\u01bb\t\t\2\2\u01ba\u01b9\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb" +
					"\u01bc\3\2\2\2\u01bc\u01bd\5\36\20\2\u01bd\u01bf\3\2\2\2\u01be\u01ab\3" +
					"\2\2\2\u01be\u01b5\3\2\2\2\u01bfY\3\2\2\2\60`hmu\u0085\u008f\u009e\u00a1" +
					"\u00a7\u00ae\u00b6\u00be\u00d1\u00d9\u00e0\u00f5\u0105\u010e\u011b\u011f" +
					"\u0126\u012e\u0138\u013a\u0142\u0147\u014e\u0158\u015e\u0163\u0168\u016b" +
					"\u016f\u0174\u017d\u0184\u018a\u018c\u0192\u0198\u019d\u01a6\u01ad\u01b1" +
					"\u01ba\u01be";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}