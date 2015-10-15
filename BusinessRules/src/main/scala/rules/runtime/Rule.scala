package rules.runtime

import scala.collection.mutable.{HashMap, ListBuffer}

trait Rule extends Dependable {

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

trait Dependable {
  def predecessors: List[Rule]

  def successors: List[Rule]
}

trait RuleContext {
  def map: Map[Reference, Any]
}

case class Reference(val path: String)

trait Document {
  def get[T](reference: Reference): T
}


//
// An execution plan is an abstract interface to an a code generated sub-class.
// The sub-class implements an execute() method generated from the rule definitions.
//
// The execute() method simply applies rules in correct dependency order to the input document.
//
trait ExecutionPlan {
  def execute(document: Document): Unit
}





