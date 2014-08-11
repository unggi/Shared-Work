package nomura.ted.trade.tba.voicebroker.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.{StateMachine, Workflow, WorkflowParticipant}

class RTTMTradeMatchingWorkflow(val workflow: Workflow) extends StateMachine("RTTM Trade Matching Workflow") with WorkflowParticipant {

  var brokerTradeID: Option[String] = None
  var traderTradeID: Option[String] = None

  def compareTrades() = {
    log.debug( s"""Comparing Two Trades: BROKER ID = ${brokerTradeID.getOrElse("NULL")} TRADER ID = ${traderTradeID.getOrElse("NULL")}""")
    if (traderTradeID.isDefined && brokerTradeID.isDefined && brokerTradeID.get.equals(traderTradeID.get)) {
      sendSelf(TradeMatchFound(traderTradeID.get, brokerTradeID.get))
      log.debug("Self Send of TradeMatchFound")
    } else {
      log.debug("Self Send of No Trade Match")
      sendSelf(NoTradeMatch())
    }
  }

  model("RTTM Trade Matching") {

    state("Matching Trades") {
      event: Completed =>
        log.debug("Inside Matching Trades")
    }

    state("Trade is Matched") {
      event: RTTMTradeMatchReport =>
        println("Trade Matching i progress")
    }

    state("Match Incoming Broker Trade") {
      event: IncomingBrokerTrade =>
        brokerTradeID = Some(event.brokerTradeID)
        compareTrades()
    }

    state("Match Incoming Firm Trade") {
      event: IncomingFirmTrade =>
        traderTradeID = Some(event.tradeID)
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
    transition from "Matching Trades" to "Match Incoming Firm Trade" on IncomingFirmTrade
    transition from "Match Incoming Broker Trade" to "Trade is Matched" on TradeMatchFound
    transition from "Match Incoming Firm Trade" to "Trade is Matched" on TradeMatchFound
    transition from "Match Incoming Broker Trade" to "No Match" on NoTradeMatch
    transition from "Match Incoming Firm Trade" to "No Match" on NoTradeMatch
    flow from "No Match" to "Matching Trades"
    flow from "Trade is Matched" to terminal
  }
}
