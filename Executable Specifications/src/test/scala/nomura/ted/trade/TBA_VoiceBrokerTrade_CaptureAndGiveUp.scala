package nomura.ted.trade

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{TestActorRef, TestKit}
import nomura.uml.LifeCycleEvents.Completed
import nomura.uml._
import org.scalatest._

import scala.collection.immutable.HashMap
import scala.collection.mutable

abstract class ProductRef(ESMID: String)

case class PoolID(ESMID: String, poolNumber: String, cusip: String) extends ProductRef(ESMID)

abstract class Party(RDMID: String)

case class Broker(RDMID: String) extends Party(RDMID)

case class Trader(RDMID: String) extends Party(RDMID)

case class Customer(RDMID: String) extends Party(RDMID)

case class NewBrokerTrade(brokerTradeID: String,
                          cusip: String,
                          poolNumber: String,
                          issuer: String,
                          broker: Broker,
                          trader: Trader)

case class AllegedTrade(brokerTradeID: String)

case class ConfirmBrokerTrade(brokerTradeID: String)

case class RejectBrokerTrade(brokerTradeID: String)

case class RTTMTradeMatchReport(brokerTradeID: String)

case class TradeExecutionReport(brokerTradeID: String)

case class IncomingBrokerTrade()

case class IncomingFirmTrade()

case class TradeMatchFound()

case class NoTradeMatch()


trait Workflow {

  def validParticipants: Set[String]

  def name: String

  val switchboard = new mutable.HashMap[String, ActorRef]()

  def send(participant: String, message: Any): Boolean =
    switchboard.get(participant) match {
      case Some(a) =>
        a ! message
        true
      case None =>
        false
    }

  def register(participant: String, actorRef: ActorRef) {
    require(validParticipants.contains(participant), s"Participant [$participant] is not a valid participant. Should be one of ${validParticipants}")
    switchboard.put(participant, actorRef)
  }

  def start(): Unit

}

trait WorkflowParticipant {
  def workflow: Workflow
}


class RTTMTradeMatchingWorkflow(val workflow: Workflow) extends StateMachine("RTTM Trade Matching Workflow") with WorkflowParticipant {

  model("RTTM Trade Matching") {

    state("Matching Trades") {
      event: Completed =>
    }

    state("Trade is Matched") {
      event: RTTMTradeMatchReport =>
    }

    state("Match Incoming Broker Trade") {
      event: IncomingBrokerTrade =>
    }

    state("Match Incoming Firm Trade") {
      event: IncomingFirmTrade =>
    }

    state("Trade is Matched") {
      event: TradeMatchFound =>
    }

    state("No Match") {
      event: NoTradeMatch =>
    }

    flow from start to "Matching Trades"
    transition from "Matching Trades" to "Match Incoming Broker Trade" on IncomingBrokerTrade
    transition from "Matching Trades" to "Match Incoming Firm Trade" on IncomingFirmTrade
    transition from "Match Incoming Broker Trade" to "Trade is Matched" on TradeMatchFound
    transition from "Match Incoming Firm Trade" to "Trade is Matched" on TradeMatchFound
    transition from "Match Incoming Broker Trade" to "No Match" on NoTradeMatch
    transition from "Match Incoming Firm Trade" to "No Match" on NoTradeMatch
    flow from "No Match" to "Matching Trades"
    flow from "Trade is Matched" to terminal
  }
}

class TraderTradeCaptureWorkflow(val workflow: Workflow) extends StateMachine("Trade Capture Workflow") with WorkflowParticipant {
  model("Trade Capture") {

    state("Trader Reviews Trade") {
      event: NewBrokerTrade =>

    }

    state("Trader Accepts") {
      event: ConfirmBrokerTrade =>

    }

    state("Trader Rejects") {
      event: RejectBrokerTrade =>
    }
    transition from start to "Trader Reviews Trade" on NewBrokerTrade
    transition from "Trader Reviews Trade" to "Trader Accepts" on ConfirmBrokerTrade
    transition from "Trader Reviews Trade" to "Trader Rejects" on RejectBrokerTrade
    flow from "Trader Accepts" to terminal
  }

}

class BrokerTradeBookingWorkflow(val workflow: Workflow) extends StateMachine("Broker Trade Booking Workflow") with WorkflowParticipant {

  model("Broker Trade Booking Workflow") {

    state("Verbal Execution of Trade") {
      event: Completed =>
    }

    state("Enter Broker Trade") {
      event: NewBrokerTrade =>
        // Book the trade here
        // then send myself a message that a new trade message
        sendSelf(event)

    }

    state("Post Trade Notification") {
      event: NewBrokerTrade =>
        // Tell the trader via the post trade feed that there is a new trade
        workflow.send("Trader", event)
        // Tell myself to proceed with the New Broker Trade
        sendSelf(event)
    }

    state("Broker Report to RTTM") {
      event: NewBrokerTrade =>
        workflow.send("RTTM", IncomingBrokerTrade())
    }

    flow from start to "Verbal Execution of Trade"
    transition from "Verbal Execution of Trade" to "Enter Broker Trade" on NewBrokerTrade
    transition from "Enter Broker Trade" to "Post Trade Notification" on NewBrokerTrade
    transition from "Post Trade Notification" to "Broker Report to RTTM" on NewBrokerTrade
    flow from "Broker Report to RTTM" to terminal
  }
}

class TBAVoiceBrokerTradeWorkflow(val name: String) extends Workflow {

  def validParticipants: Set[String] = Set("Trader", "Broker", "RTTM")

  def start(): Unit = {
    send("Broker", Completed())
  }

}

class TestTBAVoiceBrokerTradeModel extends TestKit(ActorSystem("TED-World")) with WordSpecLike with
MustMatchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(StateMachineActorSystem.system)
  }

  "Broker Trade Workflow Successful" when {

    val workflow = new TBAVoiceBrokerTradeWorkflow("TBA Voice Broker Trade Capture Workflow")

    val broker = TestActorRef(new BrokerTradeBookingWorkflow(workflow))
    val trader = TestActorRef(new TraderTradeCaptureWorkflow(workflow))
    val rttm = TestActorRef(new RTTMTradeMatchingWorkflow(workflow))

    workflow.register("Trader", trader)
    workflow.register("Broker", broker)
    workflow.register("RTTM", rttm)

    "Broker is in wait on verbal execution of trade after initialization" in {
      workflow.start()
      broker.underlyingActor.currentState.name mustEqual "Verbal Execution of Trade"
    }

    "Broker gets a NewBrokerTrade after verbal execution -> terminal state ([*])" in {
      val newTradeEvent = NewBrokerTrade("123", "123456789", "372897", "FN", Broker("DW"), Trader("JS"))
      workflow.send("Broker", newTradeEvent)
      broker.underlyingActor.currentState.name mustEqual "[*]"
    }

    "Trader must have received a new broker trader message -> Trader Reviews Trade" in {
      trader.underlyingActor.currentState.name mustEqual "Trader Reviews Trade"
    }


    "Trader confirms the trade and workflow is terminated" in {
      workflow.send("Trader", ConfirmBrokerTrade("123"))
      trader.underlyingActor.currentState.name mustEqual "[*]"
    }
  }
}





