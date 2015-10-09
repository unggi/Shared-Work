package rules.runtime

import scala.collection.mutable
import scala.collection.mutable.{HashMap, ListBuffer}

trait Rule extends RuleDependency {
  def buildRuleContext(document: Document): RuleContext

  def fire(scope: RuleScope, context: RuleContext): Boolean
}

trait RuleSet {
  def rules = new ListBuffer[Rule]()
}

class RuleScope() {

  val bindings = new HashMap[String, Any]()

  def lookup(path: String): Option[Any] =
    bindings.get(path)

  def set(path: String, value: Any): RuleScope = {
    bindings.put(path, value)
    this
  }
}

trait RuleDependency {
  def predecessors: List[Rule]

  def successors: List[Rule]
}

trait RuleContext {
  def map: Map[Reference, Any]
}

case class Reference(path: String)

trait Document {
  def get[T](reference: Reference): T
}

class ExecutionEngine(ruleSet: RuleSet) {

  val globalScope = new RuleScope()

  def execute(document: Document): Boolean = {

    ruleSet.rules.foreach {
      rule =>
        val context = rule.buildRuleContext(document)
        rule.fire(new RuleScope(), context)
    }

    true
  }

}

