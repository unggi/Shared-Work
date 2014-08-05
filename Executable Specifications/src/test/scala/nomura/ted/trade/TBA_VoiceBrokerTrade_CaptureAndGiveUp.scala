package nomura.ted.trade

import akka.actor
import nomura.uml._
import org.scalatest._
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestKit, TestActorRef}
import nomura.uml.LifeCycleEvents.{Initialize, Completed}

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

class TBAVoiceBrokerTradeWorkflow extends StateMachine("TBA - Voice Broker Trade - Capture And Giveup") {

  model("Workflow - TBA - Voice Broker Trade - Capture and Giveup") {

    composite("RTTM Trade Matching") {
      state("Matching Trades") {
        event: AllegedTrade =>
      }
      state("Trade is Matched") {
        event: RTTMTradeMatchReport =>
      }
    }

    composite("Broker Trade Booking") {

      state("Enter Broker Trade") {
        event: Completed =>
      }

      state("Post Trade Notification") {
        event: TradeExecutionReport =>
      }

      state("Broker Report to RTTM ") {
        event: Completed =>
      }

      flow from start to "Enter Broker Trade"
      transition from "Enter Broker Trade" to "RTTM Trade Matching" on AllegedTrade
    }


    state("Trader Reviews Trade") {
      event: NewBrokerTrade =>

    }
    state("Trader Accepts") {
      event: ConfirmBrokerTrade =>

    }

    state("Trader Rejects") {
      event: RejectBrokerTrade =>
    }






    flow from start to "Broker Trade Booking"
    transition from "Broker Trade Booking" to "Trader Reviews Trade" on NewBrokerTrade
    transition from "Trader Reviews Trade" to "Trader Accepts" on ConfirmBrokerTrade
    transition from "Trader Reviews Trade" to "Trader Rejects" on RejectBrokerTrade

    transition from "Trader Accepts" to "RTTM Matching Trades" on AllegedTrade

    transition from "RTTM Matching Trades" to "Trade is Matched" on RTTMTradeMatchReport
    flow from "Trade is Matched" to terminal
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
      workflow ! Initialize()
      tbaWorkflow.currentState.name mustEqual "Broker Enters Trade into Broker Booking System"
    }

    "should move to Trader Accepts after New Trade Event" in {
      workflow ! NewBrokerTrade("123", "123456789", "372897", "FN", Broker("DW"), Trader("JS"))
      tbaWorkflow.currentState.name mustEqual "Trader Accepts"
    }
  }
}



