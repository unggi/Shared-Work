package nomura.ted.trade.tba.voicebroker.capture

import _root_.nomura.uml.LifeCycleEvents.Completed
import akka.actor.ActorRef
import nomura.uml.{StateMachine, WorkflowParticipant}


class RTTMWorkflow(_workflow: ActorRef)
  extends StateMachine("RTTM Workflow")
  with WorkflowParticipant {

  var brokerTradeID: Option[String] = None
  var dealerTradeID: Option[String] = None

  def workflow: ActorRef = _workflow

  def compareTrades(): Unit = {
    if (brokerTradeID.isDefined && dealerTradeID.isDefined && brokerTradeID.get.equals(dealerTradeID.get))
      sendSelf(TradeMatchFound(dealerTradeID.get))
    else
      sendSelf(NoTradeMatch())

  }

  model("RTTM Trade Matching") {

    state("Matching Trades") {
      event: Completed =>
    }

    state("Trade is Matched") {
      event: TradeMatchFound =>
    }

    state("Match Incoming Broker Trade") {
      event: IncomingBrokerTrade =>
        brokerTradeID = Some(event.brokerTradeID)
        compareTrades()
    }

    state("Match Incoming Dealer Trade") {
      event: IncomingDealerTrade =>
        dealerTradeID = Some(event.tradeID)
        compareTrades()
    }

    state("Trade is Matched") {
      event: TradeMatchFound =>
    }

    state("No Match") {
      event: NoTradeMatch =>
    }

    flow from start to "Matching Trades"
    transition from "Matching Trades" to "Match Incoming Broker Trade" on IncomingBrokerTrade
    transition from "Matching Trades" to "Match Incoming Dealer Trade" on IncomingDealerTrade
    transition from "Match Incoming Broker Trade" to "Trade is Matched" on TradeMatchFound
    transition from "Match Incoming Dealer Trade" to "Trade is Matched" on TradeMatchFound
    transition from "Match Incoming Broker Trade" to "No Match" on NoTradeMatch
    transition from "Match Incoming Dealer Trade" to "No Match" on NoTradeMatch
    flow from "No Match" to "Matching Trades"
    flow from "Trade is Matched" to terminal
  }
}
