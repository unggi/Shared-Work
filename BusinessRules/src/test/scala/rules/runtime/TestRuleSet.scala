package rules.runtime

import java.util.Date

import org.scalatest.FlatSpec

import scala.collection.mutable


// The data model
case class Trade(entity: String, counterParty: String, currency: String, notional: Double, instrument: Instrument)

class Instrument(val ID: String, val name: String, val legs : Array[Leg])

case class InterestRateSwap(override val ID: String, override val name: String, override val legs: Array[Leg]) extends Instrument(ID, name, legs)

case class Leg(val cashflowType: String, val issueDate: Date, val maturityDate: Date, val cashflowFrequency: Integer)


// Rule: An interest rate swap must have exactly two legs.
object Rule1 {

  def evaluate(instrument: Instrument): Boolean = {
    instrument.legs.length == 2
  }

}

// Rule: An interest rate swap has to have a fixed leg and a floating leg.
object Rule2 {

  def evaluate(swap: Instrument): Boolean = {
    swap.legs.count {p => p.cashflowType.equals("fixed")} == 1 &&
    swap.legs.count {p => p.cashflowType.equals("float")} == 1
  }

}


class TestRuleSet extends FlatSpec {

  // A sample Trade
  val trade =
    Trade("NFPS", "Morgan Stanley", "GBP", 1000000.0,
      InterestRateSwap("0101", "Interest Rate Swap", Array[Leg](
        Leg("fixed", new Date(2015, 12, 1), new Date(2025, 12, 1), 2),
        Leg("float", new Date(2015, 12, 1), new Date(2025, 12, 1), 4)))
    )

  "A Trade" should "pass all constraints checks" in {
    println("Testing Constraints")

    assert(Rule1.evaluate(trade.instrument))
    assert(Rule2.evaluate(trade.instrument))
  }


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
