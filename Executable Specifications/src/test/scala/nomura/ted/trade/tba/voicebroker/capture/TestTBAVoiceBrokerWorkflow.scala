package nomura.ted.trade.tba.voicebroker.capture


import _root_.nomura.ted.trade.dba.voicebroker.capture.OrchestrationWorkflow
import nomura.uml.StateMachine
import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest._

abstract class ProductRef(ESMID: String)

case class PoolID(ESMID: String, poolNumber: String, cusip: String) extends ProductRef(ESMID)

abstract class Party(RDMID: String)

case class Broker(RDMID: String) extends Party(RDMID)

case class Trader(RDMID: String) extends Party(RDMID)

case class Customer(RDMID: String) extends Party(RDMID)

trait StateTestMatcher {
  self: MustMatchers =>

  implicit class StateTestHelper[T <: StateMachine](actor: TestActorRef[T]) {
    def mustBeInState(stateName: String) {
      actor.underlyingActor.currentState.name mustEqual stateName
    }

    def mustBeInTerminalState(): Unit = {
      actor.underlyingActor.currentState.name mustEqual "[*]"
    }
  }

}

class TestTBAVoiceBrokerTradeModel extends TestKit(ActorSystem("TED-World")) with WordSpecLike with
MustMatchers with BeforeAndAfterAll with StateTestMatcher {

  override def afterAll {

    TestKit.shutdownActorSystem(StateMachineActorSystem.system)
    TraceFacility.printAll

  }

  "Broker Trade Workflow Successful" when {

    val workflow = new OrchestrationWorkflow("TBA Voice Broker Trade Capture Workflow")

    val broker = TestActorRef(new BrokerWorkflow(workflow))
    val trader = TestActorRef(new TraderWorkflow(workflow))
    val rttm = TestActorRef(new RTTMWorkflow(workflow))

    workflow.register("Trader", trader)
    workflow.register("Broker", broker)
    workflow.register("RTTM", rttm)

    val trade = new BrokerTrade()

    "Broker is in wait on verbal execution of trade after initialization" in {
      workflow.start()
      broker mustBeInState "Verbal Execution of Trade"
    }

    "Broker gets a NewBrokerTrade after verbal execution -> terminal state ([*])" in {
      val newTradeEvent = NewBrokerTrade("123", "123456789", "372897", "FN", "DW", "JS")
      workflow.send("Broker", newTradeEvent)
      broker mustBeInTerminalState
    }

    "Trader must have received a new broker trader message -> Trader Reviews Trade" in {
      trader mustBeInState "Trader Reviews Trade"
    }

    "Trader confirms the trade and workflow is terminated" in {
      workflow.send("Trader", ConfirmBrokerTrade("123"))
      trader mustBeInTerminalState
    }

    "RTTM must be in a terminal state too" in {
      rttm mustBeInTerminalState
    }
  }
}






