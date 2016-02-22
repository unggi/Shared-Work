package codegen

import codegen.symbols.SymbolTableBuilder
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser.{ContextContext, ModelReferenceContext, ValidationRuleContext}

class DependencyAnalyzer(symbolTable: SymbolTableBuilder) extends BusinessRulesBaseListener {

  import DependencyGraph._
  import StringFormatter._

  val graph = new DependencyGraph()

  class Input(override val name: String, val alias: String, val ctx: ContextContext) extends InputNode(name) {
    override def toString: String = s"""Input($name, "${unquote(alias)}", ${ctx.hashCode})"""
  }

  class Rule(override val name: String, val validationRuleContext: ValidationRuleContext) extends RuleNode(name)

  class Variable(override val name: String) extends VariableNode(name) {

  }

  class Output(override val name: String) extends OutputNode(name) {

  }

  override def enterContext(ctx: ContextContext): Unit = {
    super.enterContext(ctx)
  }


  var activeConstraint: Option[Rule] = None

  /**
   *
   * When a validation rule is found, we traverse it's children.
   *
   * The context is an input node.
   * A model reference is a dependency on the input node or to a variable previously defined.
   *
   **/
  override def enterValidationRule(ctx: ValidationRuleContext): Unit = {

    //    val rule = new Rule(ctx.name.getText, ctx)
    //    graph.addRules(rule)
    //
    //    val context = ctx.context()
    //    val alias = context.modelReferenceWithAlias.alias.getText
    //    val path = toDotPath(context.modelReferenceWithAlias.modelReference())
    //
    //    System.err.println(s"Path is $path")
    //
    //    activeConstraint = Some(rule)
    //
    //    val input = new Input(path, alias, context)
    //    graph.addInputs(input)

  }

  override def enterModelReference(ctx: ModelReferenceContext): Unit = {
    activeConstraint match {
      case Some(rule) =>
        System.err.println(s"MODEL REFERENCE - " + toDotPath(ctx))
        val reference = graph.findNode(toDotPath(ctx))
      //    graph.addDependency(reference.get -> rule)


      case None =>
        // Should not happen - the reference is not within a rule context
        System.err.println(s"A model reference has been found outside of a rule context: Ref = ${ctx.getText}")
    }
  }

  override def exitValidationRule(ctx: ValidationRuleContext): Unit = {
    super.exitValidationRule(ctx)
    activeConstraint = None
  }


}
