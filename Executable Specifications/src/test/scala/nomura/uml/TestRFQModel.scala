package nomura.uml

import org.scalatest._
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.event.Logging
import akka.testkit.{TestKit, TestActorRef}
import scala.Some
import nomura.uml.LifeCycleEvents.Completed


case class Quote(price: Double);

case class Accept()

case class Reject(reason: String)

case class Cancel()

case class Submit(buy: Boolean, price: Double, cusip: String, settleDate: String)

case class Counter(price: Double)

class RFQ extends StateMachine("RFQ") {

  var customerBuys = false
  var price = 0.0
  var cusip = ""
  var settleDate = ""

  model("RFQ") {

    state("Customer Initiating RFQ") {
      event: Completed =>

    }

    state("Submit RFQ") {
      event: Submit =>

        customerBuys = event.buy
        price = event.price
        cusip = event.cusip
        settleDate = event.settleDate

    }

    state("Waiting on Trader Response") {
      event: Completed =>

    }

    state("Waiting on Customer Response") {
      event: Quote =>

        price = event.price
    }

    state("Waiting on Customer Confirm") {
      event: Accept =>

    }

    state("Trade is Done") {
      event: Accept =>
    }

    state("Rejected") {
      event: Reject =>
    }

    state("Cancelled") {
      event: Cancel =>
    }

    state("Update Quote with Counter Offer") {
      event: Counter =>

        price = event.price
    }


    flow from start to "Customer Initiating RFQ"
    transition from "Customer Initiating RFQ" to "Submit RFQ" on Submit
    transition from "Customer Initiating RFQ" to "Cancelled" on Cancel
    transition from "Waiting on Trader Response" to "Waiting on Customer Response" on Quote
    transition from "Waiting on Trader Response" to "Rejected" on Reject
    transition from "Waiting on Trader Response" to "Waiting on Customer Confirm" on Accept
    transition from "Waiting on Customer Confirm" to "Trade is Done" on Accept
    transition from "Waiting on Customer Response" to "Trade is Done" on Accept
    transition from "Waiting on Customer Confirm" to "Cancelled" on Cancel
    transition from "Trade is Done" to terminal on Completed
    transition from "Cancelled" to terminal on Completed
    transition from "Rejected" to terminal on Completed
    transition from "Waiting on Customer Response" to "Cancelled" on Cancel
    transition from "Waiting on Customer Response" to "Update Quote with Counter Offer" on Counter
    transition from "Submit RFQ" to "Waiting on Trader Response" on Completed
    transition from "Update Quote with Counter Offer" to "Waiting on Trader Response" on Completed

  }
}





class TestRFQModel extends TestKit(ActorSystem("Testing"))
with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(StateMachineActorSystem.system)
  }

  "An RFQ" when {

    val actorRef = TestActorRef[RFQ]
    val actor = actorRef.underlyingActor

    """should move to "Customer Initiating RFQ" state on Completed""" in {
      actorRef ! Completed()
      actor.currentState.name mustEqual "Customer Initiating RFQ"
    }

    "should move to 'Waiting on Trader Response; on Submit()" in {
      actorRef ! Submit(true, 99.0, "318387930", "04/30/2014")
      assert(actor.currentState.name === "Waiting on Trader Response")
    }

    "should move to 'Waiting on Customer Response' on Quote()" in {
      actorRef ! Quote(100.0)
      assert(actor.currentState.name === "Waiting on Customer Response")
    }

    "should move to 'Waiting on Trader Response' on Counter()" in {
      actorRef ! Counter(99.5)
      assert(actor.currentState.name === "Waiting on Trader Response")
    }

    "should have a price of 99.5" in {
      assert(actor.price === 99.5)
    }

    "should move to 'Waiting on Customer Confirm' on Accept()" in {
      actorRef ! Accept()
      assert(actor.currentState.name === "Waiting on Customer Confirm")
    }

    "should move to 'Trade is Done' on Accept()" in {
      actorRef ! Accept()
      assert(actor.currentState.name === "[*]")
    }
  }
}



