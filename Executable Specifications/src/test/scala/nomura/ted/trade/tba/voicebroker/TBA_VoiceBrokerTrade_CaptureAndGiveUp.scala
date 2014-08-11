package nomura.ted.trade.tba.voicebroker

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import nomura.ted.trade.tba.voicebroker.capture._
import nomura.uml.LifeCycleEvents.{QueryState, QueryStateReply}
import nomura.uml._
import org.scalatest._

abstract class ProductRef(ESMID: String)

case class PoolID(ESMID: String, poolNumber: String, cusip: String) extends ProductRef(ESMID)

abstract class Party(RDMID: String)

case class Broker(RDMID: String) extends Party(RDMID)

case class Trader(RDMID: String) extends Party(RDMID)

case class Customer(RDMID: String) extends Party(RDMID)


trait StateTester {
  self: MustMatchers =>

  implicit class StateTesterHelper[T <: StateMachine](val machine: TestActorRef[T]) {

    def mustBeInState(stateName: String) {
      machine.underlyingActor.currentState.name mustEqual stateName
    }

    def mustBeInTerminalState() {
      machine.underlyingActor.currentState.name mustEqual "[*]"
    }
  }

}


class TestTBAVoiceBrokerTradeModel extends TestKit(ActorSystem("TED-World")) with WordSpecLike with
MustMatchers with BeforeAndAfterAll with StateTester {

  override def afterAll {
    TestKit.shutdownActorSystem(StateMachineActorSystem.system)
  }

  "RTTM Workflow" when {

    // Create a properly configured workflow

    val workflow = new TBAVoiceBrokerTradeWorkflow("TBA Voice Broker Trade Capture Workflow")

    // With sub-workflows
    val broker = TestActorRef(new BrokerTradeBookingWorkflow(workflow))
    val trader = TestActorRef(new TraderTradeCaptureWorkflow(workflow))
    val rttm = TestActorRef(new RTTMTradeMatchingWorkflow(workflow))

    // each performing a role in this workflow
    workflow.register("Trader", trader)
    workflow.register("Broker", broker)
    workflow.register("RTTM", rttm)

    "Validation of RTTM Standalone Workflow" in {
      workflow.start()
      rttm mustBeInState "Matching Trades"
      workflow.send("RTTM", IncomingFirmTrade("345"))
      rttm mustBeInState "Matching Trades"
      workflow.send("RTTM", IncomingBrokerTrade("345"))
      rttm mustBeInTerminalState()
    }
  }

  "Broker Trade Workflow" when {

    // Create a properly configured workflow
    val workflow = new TBAVoiceBrokerTradeWorkflow("TBA Voice Broker Trade Capture Workflow")

    // With sub-workflows
    val broker = TestActorRef(new BrokerTradeBookingWorkflow(workflow))
    val trader = TestActorRef(new TraderTradeCaptureWorkflow(workflow))
    val rttm = TestActorRef(new RTTMTradeMatchingWorkflow(workflow))

    // each performing a role in this workflow
    workflow.register("Trader", trader)
    workflow.register("Broker", broker)
    workflow.register("RTTM", rttm)

    "Broker is in wait state <Verbal Execution of Trade>  after initialization" in {
      workflow.start()
      broker mustBeInState "Verbal Execution of Trade"
    }

    "Broker gets a NewBrokerTrade after verbal execution -> terminal state ([*])" in {
      val newTradeEvent = NewBrokerTrade("123", "123456789", "372897", "FN", "DW", "JS")
      workflow.send("Broker", newTradeEvent)
      broker mustBeInState "[*]"
    }

    "Trader must have received a new broker trader message -> Trader Reviews Trade" in {
      trader mustBeInTerminalState()
    }

    "Trader confirms the trade and workflow is terminated" in {
      workflow.send("Trader", ConfirmBrokerTrade("123"))
      trader mustBeInTerminalState()
    }

    "RRTM Workflow Depends on trade messages" in {

      val reply = (rttm ! QueryState()).asInstanceOf[QueryStateReply]

      rttm mustBeInTerminalState()

    }
  }
}





