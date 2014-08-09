package nomura.ted.trade.tba.voicebroker

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{TestActorRef, TestKit}
import nomura.capture.{TraderTradeCaptureWorkflow, BrokerTradeBookingWorkflow, TBAVoiceBrokerTradeWorkflow, RTTMTradeMatchingWorkflow}
import nomura.uml.LifeCycleEvents.Completed
import nomura.uml._
import org.scalatest._

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





