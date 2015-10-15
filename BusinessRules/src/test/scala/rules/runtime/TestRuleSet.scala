package rules.runtime

import org.scalatest.FlatSpec

import scala.collection.mutable


// The data model
case class Trade(val entity: String, val currency: String)

// A rule
class NFPSTradeDefinition()  {
  def fire(trade: Trade): Unit = {

  }
}


class TestRuleSet extends FlatSpec {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new mutable.Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new mutable.Stack[String]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
  }
}
