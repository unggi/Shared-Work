package rules.runtime

import scala.collection.mutable.{HashMap, ListBuffer}

trait Rule {

  def buildRuleContext(document: DocumentSource): RuleContext


  def fire(scope: RuleScope, context: RuleContext): Boolean
}

trait RuleSet {
  def rules = new ListBuffer[Rule]()
}

trait RuleScope {
  def bindings: HashMap[String, Any]

  def enclosingScope: Option[RuleScope]

  def lookup(path: String): Option[Any] =
    bindings.get(path) match {
      case Some(value) => Some(value)
      case None =>
        enclosingScope match {
          case Some(scope) =>
            scope.lookup(path)
          case None => None
        }
    }

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

trait DocumentSource {
  def get[T](reference: Reference): T

}

class ExecutionEngine(rules: RuleSet) {
  def execute(docSource: DocumentSource): Boolean = {
    rules.rules.foreach { rule =>

    }

    true
  }
}

