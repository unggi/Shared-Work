package nomura.ted.trade.tba.voicebroker.capture


import _root_.nomura.testutils.StateTestMatcher
import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import nomura.uml.{StateMachineActorSystem, TraceFacility, WorkflowParticipant}
import org.scalatest._


class TestTBAVoiceBrokerTradeModel extends TestKit(ActorSystem("TED-World")) with WordSpecLike with
MustMatchers with BeforeAndAfterAll with StateTestMatcher {

  override def afterAll {

    TestKit.shutdownActorSystem(StateMachineActorSystem.system)
    TraceFacility.printAll

  }

  "Broker Trade Capture is Successful" when {

    val workflow = new OrchestrationWorkflow {
      def name: String = "TBA Voice Broker Trade Capture Workflow"

      def participants = new collection.mutable.HashMap[String, WorkflowParticipant]()

      def start(): Unit = {}

      def start(): Unit {
        send ("Broker", Completed () )
      }

    }

    val broker = TestActorRef(new BrokerWorkflow(workflow))
    val trader = TestActorRef(new TraderWorkflow(workflow))
    val rttm = TestActorRef(new RTTMWorkflow(workflow))

    workflow.register("Trader", trader)
    workflow.register("Broker", broker)
    workflow.register("RTTM", rttm)

    // val trade = new BrokerTrade()

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






