package nomura.ted.trade

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import nomura.uml.LifeCycleEvents.Completed
import nomura.uml._
import org.scalatest._

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

class TBAVoiceBrokerTradeWorkflow extends StateMachine("TBA - Voice Broker Trade - Capture And Giveup") {

  model("Workflow - TBA - Voice Broker Trade - Capture and Giveup") {

    composite("Broker Trade Booking") {

      state("Enter Broker Trade") {
        event: Completed =>
      }

      state("Post Trade Notification") {
        event: Completed =>
      }

      state("Broker Report to RTTM") {
        event: Completed =>
      }

      flow from start to "Enter Broker Trade"
      flow from "Enter Broker Trade" to "Post Trade Notification"
      flow from "Post Trade Notification" to "Broker Report to RTTM"
      flow from "Broker Report to RTTM" to terminal
    }


    composite("Trade Capture") {
      state("Trader Reviews Trade") {
        event: NewBrokerTrade =>

      }

      state("Trader Accepts") {
        event: ConfirmBrokerTrade =>

      }

      state("Trader Rejects") {
        event: RejectBrokerTrade =>
      }
      flow from start to "Trader Reviews Trade"
      transition from "Trader Reviews Trade" to "Trader Accepts" on ConfirmBrokerTrade
      transition from "Trader Reviews Trade" to "Trader Rejects" on RejectBrokerTrade
      flow from "Trader Accepts" to terminal
    }

    composite("RTTM Trade Matching") {
      state("Matching Trades") {
        event: AllegedTrade =>
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

    flow from start to "Broker Trade Booking"
    flow from "Broker Trade Booking" to terminal

    //    transition from "Broker Trade Booking" to "Trader Reviews Trade" on NewBrokerTrade
    //    transition from "Trader Reviews Trade" to "Trader Accepts" on ConfirmBrokerTrade
    //    transition from "Trader Reviews Trade" to "Trader Rejects" on RejectBrokerTrade

    //   transition from "Trader Accepts" to "RTTM Matching Trades" on AllegedTrade

    //   transition from "RTTM Matching Trades" to "Trade is Matched" on RTTMTradeMatchReport
    //   flow from "Trade is Matched" to terminal
  }
}

class TestTBAVoiceBrokerTradeModel extends TestKit(ActorSystem("TED-World")) with WordSpecLike with
MustMatchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(StateMachineActorSystem.system)
  }

  "A Broker Trade" when {

    val workflow = TestActorRef[TBAVoiceBrokerTradeWorkflow]
    val tbaWorkflow = workflow.underlyingActor

    "should move to Broker Trade Capture after initialization" in {
      //workflow ! Initialize()
      //tbaWorkflow.currentState.name mustEqual "Broker Enters Trade into Broker Booking System"
    }

    "should move to Trader Accepts after New Trade Event" in {
      //workflow ! NewBrokerTrade("123", "123456789", "372897", "FN", Broker("DW"), Trader("JS"))
      //tbaWorkflow.currentState.name mustEqual "Trader Accepts"
    }
  }
}



