grammar BusinessRules;


@header {
}

//
// Top Level File Processing
//

fileBody
		:	 modelFileReferences  declarations
		;

modelFileReferences
		:	(modelFileReference)+
		;

modelFileReference
		:	'Model' DoubleQuotedString
		;

declarations
		:	(declaration)*
		;

declaration
		:   definition
		|   validationRule
		;

validationRule  : 'Validation:' name=DoubleQuotedString context constraint ('report:' compoundReport)?;

definition  : 'Definition:' name=DoubleQuotedString 'Given:' multipleContextParameter 'Value:' value=predicate;

ruleSet  : 'Rule set' DoubleQuotedString ('applies to' modelReference 'where' constraint)?;

context  : 'Context:' modelReferenceParameter;

//multipleParameterContext  : 'Context:' multipleContextParameter;

multipleContextParameter  : modelReferenceParameter (',' modelReferenceParameter)*;

modelReferenceParameter : ref=modelReference '(' alias=DoubleQuotedString ')';

constraint      :   'Constraint:'
                    ('If' condBlock=logicalStatement 'then' thenBlock=logicalStatement ('else' elseBlock=logicalStatement)?
                |   logicalStatement
                )
                ;

binaryLogicalOperator: (and = 'and' | or = 'or' | implies = 'implies' | iff = 'if and only if');

logicalStatement:
                left=logicalStatement op=binaryLogicalOperator right=logicalStatement #BinaryLogicalOperatorStatement
                |   predicate                                           #LogicalPredicateStatement
                |   existsStatement                                     #LogicalExistsStatement
                |   notExistsStatement                                  #LogicalNotExistsStatement
                |   forallStatement                                     #LogicalForAllStatement
                ;

simpleOrComplexConstraint  : '(' constraint ')' | predicate;

predicate  :
         left=expression comparator right=expression            #BinaryPredicate
    |    expression 'is one of' listDefinition                  #IsOneOfPredicate
    |    expression 'is not one of' listDefinition              #IsNotOneOfPredicate
    |    modelReference 'is a kind of' ModelElementName         #IsKindOfPredicate
    |    expression                                             #UnaryExpressionPredicate
//    |   multipleExistsStatement
//    |   multipleNotExistsStatement
    ;

comparator  :
              IsEqualTo                                         #IsEqualToComparator
            | IsNotEqualTo                                      #IsNotEqualToComparator
            | IsGreaterThan                                     #IsGreaterThanComparator
            | IsGreaterThanOrEqualTo                            #IsGreaterThanOrEqualToComparator
            | IsLessThanOrEqualTo                               #IsLessThanOrEqualToComparator
            | IsLessThan                                        #IsLessThanComparator
    ;

listDefinition  : identifier (',' identifier)*
        ;

multipleExistsStatement  : 'following' ('is present' | 'are present') ':' modelReferenceList
       ;

multipleNotExistsStatement  : 'following' ('is not present' | 'are not present') ':' modelReferenceList
        ;

expression  :   left=expression op=('*' | '/' | '+' | '-'|'mod') right=expression       #BinaryExpression
            |   term                                                                    #UnaryExpression
            ;

term  :
        functionalExpression            #FunctionalExpressionTerm
    |   modelReference FragmentName     #DefinedTermReferenceTerm
    |   operatorInvocation              #OperatorInvocationTerm
    |   definitionApplication           #DefinitionApplicationTerm
    |   castExpression                  #CastExpressionTerm
    |   selectionExpression             #SelectionExpressionTerm
    |   '(' constraint ')'              #ConstraintTerm
    |   identifier                      #IdentifierTerm
    ;

identifier  :
    modelReference                      #ModelReferenceIdentifier
    | LiteralString                     #LiteralStringIdentifier
    | Number                            #NumberIdentifier
    | IntegerNumber                     #IntegerNumberIdentifier
    | BooleanLiteral                    #BooleanLiteralIdentifier
    | collectionIndex                   #CollectionIndexIdentifier
    | DoubleQuotedString                #DoubleQuotedStringIdentifier
    ;

functionalExpression  :
        op='sum of' ref=modelReference                                              #SumOfExpression
    |   op='number of' ref=modelReference                                           #NumberOfExpression
    |   op='number of unique' ref=modelReference '(' 'by' key=modelReference ')'    #NumberOfUniqueExpression
    ;

operatorInvocation  : OperatorName (operatorParameterList)?;

operatorParameterList  : expression ( ('and' | 'from' | 'to' | 'with' | 'using') expression )*;

definitionApplication  : FragmentName operatorParameterList;

collectionIndex  : OrdinalNumber ('of')? modelReference;

castExpression  : modelReference ('as a' | 'as an') ModelElementName;

selectionExpression  : ('first' 'of'?)? modelReference 'where' simpleOrComplexConstraint;

modelReference:
    (propPath = propertyOfModelPath | dotPath = dottedModelPath );

modelReferenceList  : (modelReference)+;

dottedModelPath  : root=ModelElementName ('.' ModelElementName)*;

propertyOfModelPath : ModelElementName ('of' ModelElementName)* 'of' root=ModelElementName;

compoundReport  : simpleReport ( (',')? simpleReport )*;

simpleReport  :
        concatenatedReport
    |   conditionalReport
    ;

concatenatedReport  : ('report')? simpleTerm ( ('+')? simpleTerm )*;

conditionalReport  : 'if' constraint 'then' compoundReport ('else' compoundReport)? ';';

simpleTerm  :
        identifier
    |   functionalExpression
    |   operatorInvocation
    ;


//
// Quantifiers
//
collectionMemberConstraint[ModelReferenceContext reference] : simpleOrComplexConstraint;

existsStatement :
                    (enumerator ('of the')?)? ref=modelReference
                    ('has' | 'have' | 'is' | 'are') ('present' | collectionMemberConstraint[$ref.ctx]) #ConstrainedCollectionMembership
                |   enumerator ('has' | 'have' | 'is' | 'are') simpleOrComplexConstraint     #SimpleExists
                ;

enumerator  : (at_least = 'at least' | at_most = 'at most' | exactly ='exactly')? (one = 'one' | two='two' | three='three' | four='four' | no='no' | none='none' | integer = IntegerNumber);

notExistsStatement  :   modelReference ('is not present' | 'are not present');

// globalExistsStatement   :    ('there is' | 'there are') ('no')? modelReference ('(' DoubleQuotedString ')')? ('where' simpleOrComplexConstraint)?;

forallStatement :
                    ('each' | 'in each' | 'all' | 'every') ('of the')?  modelReference ('has' | 'have' | 'is' | 'are')? simpleOrComplexConstraint
                |   'for each' DoubleQuotedString 'in the collection of' modelReference ('has' | 'have' | 'is' | 'are' | ',')? simpleOrComplexConstraint
                ;

//
// Lexical Rules
//

// Article stripping - has highest priority
THE                 : ([Tt][Hh][Ee]) -> skip;
AN                  : ([Aa][Nn]) -> skip;

FragmentName	    :   '<<' .*? '>>';
IsEqualTo           :    ('=' | 'is equal to');
IsNotEqualTo        :   ('<>' | 'is not equal to');
IsGreaterThan       : ('>' | 'is greater than' | 'is after');
IsLessThanOrEqualTo : ( '<=' | 'is less than or equal to' );
IsGreaterThanOrEqualTo: ( '>=' | 'is greater than or equal to');
IsLessThan          : ( '<' | 'is less than' | 'is before');

BooleanLiteral	    : 	'true' | 'false';
LiteralString	    :  	'\'' (ESC | .)*? '\'' ;
DoubleQuotedString  :   '"' (ESC | .)*? '"';
fragment ESC        :   '\\' [btnr"\\];
ModelElementName    :   Alpha (AlphaNumeric | '_' | '-')*;
VariableName	    : 	ModelElementName;
fragment Alpha	    :   [a-zA-Z];
fragment Digit	    :   [0-9];
fragment AlphaNumeric	    :  	Alpha | Digit;
Number	            :   ('-')? (Digit)+ '.' (Digit)*;
OrdinalNumber	    :   'first' | 'second' | 'third' | (Digit+ ('th' | 'st' | 'nd' | 'rd'));
IntegerNumber	    :   ('-')? (Digit)+;

OperatorName	    :   '[' [^\]\[\n\r] ']';

COMMENT             :   '--' .*? '\r'? '\n' -> skip;
LINE_COMMENT        :   '//' .*? '\r'? '\n' -> skip;
WS                  :   [ \t\r\n]+ -> skip;


