package nomura.ted.trade.tba.voicebroker.capture

// TODO Add messages to update the trade
// TODO Add the trade object to the workflow


import akka.testkit.TestKit
import nomura.testutils.StateTestMatcher
import nomura.uml._
import org.scalatest._


class TestTBAVoiceBrokerTradeModel extends TestKit(StateMachineActorSystem.system) with WordSpecLike with
MustMatchers with BeforeAndAfterAll with StateTestMatcher {


  override def afterAll {

    TestKit.shutdownActorSystem(StateMachineActorSystem.system)
    TraceFacility.printAll

  }

  "Broker Trade Capture is Successful" when {

    val workflow = BusinessProcess.create("TBA Voice Broker Trade")

    "Broker is in wait on verbal execution of trade after initialization" in {

      workflow currentStateOf "Trader" mustBe "[*]"
      workflow currentStateOf "Broker" mustBe "Verbal Execution of Trade"
      workflow currentStateOf "RTTM" mustBe "Matching Trades"

      workflow printCurrentStates

    }

    "Broker sends a NewBrokerTrade message" in {
      val newTradeEvent = NewBrokerTrade("123", "123456789", "372897", "FN", "DW", "JS")

      workflow ! DispatchToParticipant("Broker", newTradeEvent)

      workflow currentStateOf "Broker" mustBe "[*]"
      workflow currentStateOf "Trader" mustBe "Trader Reviews Trade"
      workflow currentStateOf "RTTM" mustBe "Matching Trades"

    }

    "Trader sends ConfirmBrokerTrade and all participants will terminate" in {

      workflow ! DispatchToParticipant(BusinessProcess.TRADER, ConfirmBrokerTrade("123"))

      workflow currentStateOf "Broker" mustBe "[*]"
      workflow currentStateOf "Trader" mustBe "[*]"
      workflow currentStateOf "RTTM" mustBe "[*]"


    }
  }
}






