grammar BusinessRules;

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
	//	|	globalVariableDeclaration
		;

//
// Rules and Properties
//
validationRule  : 'Validation Rule' DoubleQuotedString context constraint ('report:' compoundReport)?;

// validationRuleVariableDeclaration  : simpleVariableDeclaration ','?;

definition  : 'Definition of' name=DoubleQuotedString 'Given:' multipleContextParameter  constraint;

ruleSet  : 'Rule set' DoubleQuotedString ('applies to' modelReference 'where' constraint)?;

//globalVariableDeclaration  : simpleVariableDeclaration;

context  : 'Context:' modelReference;

multipleParameterContext  : 'Context:' multipleContextParameter;

multipleContextParameter  : modelReferenceWithAlias (',' modelReferenceWithAlias)*;

modelReferenceWithAlias : ref=modelReference '(' alias=DoubleQuotedString ')';

//
// Constraints
//
constraint      :
                    IF condBlock=logicalStatement THEN thenBlock=logicalStatement (ELSE  elseBlock=logicalStatement)?
                |   logicalStatement (op=('and' | 'or' | 'implies' | 'if and only if' ) logicalStatement)*
                ;

 logicalStatement:
                predicate
//                |   existsStatement
//                |   notExistsStatement
//                |   globalExistsStatement
//                |   forallStatement
//                |   globalVariableDeclaration
                ;

simpleOrComplexConstraint  : '(' constraint ')' | predicate;

//
// Variable Declaration
//
// simpleVariableDeclaration  : DoubleQuotedString ('is' | 'are' | 'represent' | 'represents') expression;

//
// Predicates
//
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
              IsEqualTo
            | IsNotEqualTo
            | IsGreaterThan
            | IsGreaterThanOrEqualTo
            | IsLessThanOrEqualTo
            | IsLessThan
    ;

listDefinition  : identifier (',' identifier)*
        ;

multipleExistsStatement  : 'following' ('is present' | 'are present') ':' modelReferenceList
       ;

multipleNotExistsStatement  : 'following' ('is not present' | 'are not present') ':' modelReferenceList
        ;

//
// Expressions
//
expression  :   left=expression op=('*' | '/' | '+' | '-'|'mod') right=expression      #BinaryExpression
            |   term                                                                #UnaryExpression
            ;

//
// Terms
//
term  :
        identifier
    |   functionalExpression
    |   modelReference FragmentName
    |   operatorInvocation
    |   definitionApplication
    |   castExpression
    |   selectionExpression
    |   '(' constraint ')'
    ;

identifier  : modelReference | LiteralString | Number | IntegerNumber | BooleanLiteral | collectionIndex
    ;

functionalExpression  :
        'sum of' modelReference
    |   'number of' modelReference
    |   'number of' 'unique' modelReference '(' 'by' modelReference ')'
    ;

operatorInvocation  : OperatorName (operatorParameterList)?;

operatorParameterList  : expression ( ('and' | 'from' | 'to' | 'with' | 'using') expression )*;

definitionApplication  : FragmentName operatorParameterList;

collectionIndex  : OrdinalNumber ('of')? modelReference;

castExpression  : modelReference ('as a' | 'as an') ModelElementName;

selectionExpression  : ('first' 'of'?)? modelReference 'where' simpleOrComplexConstraint;

//
// Model References and Paths
//
modelReference  : modelPath;

modelReferenceList  : (modelReference)+;

modelPath : (propertyOfModelPath | dottedModelPath );

dottedModelPath  : ModelElementName ('.' ModelElementName)* ;

propertyOfModelPath : ModelElementName ('of' ModelElementName)+;

//
// Reporting
//
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
// Lexical Rules
//

// Article stripping - has highest priority
THE                 : ([Tt][Hh][Ee]) -> skip;
AN                  : ([Aa][Nn]) -> skip;

THEN                : 'then';
IF                  : ([Ii][Ff]);
ELSE                :([Ee][Ll][Ss][Ee]);

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
Alpha	            :   [a-zA-Z];
Digit	            :   [0-9];
AlphaNumeric	    :  	Alpha | Digit;
Number	            :   ('-')? (Digit)+ '.' (Digit)*;
OrdinalNumber	    :   'first' | 'second' | 'third' | (Digit+ ('th' | 'st' | 'nd' | 'rd'));
IntegerNumber	    :   ('-')? (Digit)+;

OperatorName	    :   '[' [^\]\[\n\r] ']';

COMMENT             :   '--' .*? '\r'? '\n' -> skip;
LINE_COMMENT        :   '//' .*? '\r'? '\n' -> skip;
WS                  :   [ \t\r\n]+ -> skip;



//
// Quantifiers
//
//existsStatement :
//                    (enumerator ('of the')?)? modelReference ('has' | 'have' | 'is' | 'are') ('present' | simpleOrComplexConstraint)
//                |   enumerator ('has' | 'have' | 'is' | 'are') simpleOrComplexConstraint
//                ;
//
//enumerator  : ('at least' | 'at most' | 'exactly')? ('one' | 'two' | 'three' | 'four' | 'no' | 'none' | IntegerNumber);
//
//notExistsStatement  :   modelReference ('is not present' | 'are not present');
//
//globalExistsStatement   :    ('there is' | 'there are') ('no')? modelReference ('(' DoubleQuotedString ')')? ('where' simpleOrComplexConstraint)?;
//
//forallStatement :
//                    ('each' | 'in each' | 'all' | 'every') ('of the')?  modelReference ('has' | 'have' | 'is' | 'are')? simpleOrComplexConstraint
//                |   'for each' DoubleQuotedString 'in the collection of' modelReference ('has' | 'have' | 'is' | 'are' | ',')? simpleOrComplexConstraint
//                ;
//
