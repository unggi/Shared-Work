package nomura.ted.trade.tba.voicebroker.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.{StateMachine, Workflow, WorkflowParticipant}

/**
 * Created by usoemard on 8/8/2014.
 */
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
