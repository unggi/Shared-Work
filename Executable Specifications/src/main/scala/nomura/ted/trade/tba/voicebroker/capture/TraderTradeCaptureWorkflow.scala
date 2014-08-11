package nomura.ted.trade.tba.voicebroker.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.{StateMachine, Workflow, WorkflowParticipant}

class TraderTradeCaptureWorkflow(val workflow: Workflow) extends StateMachine("Trade Capture Workflow") with WorkflowParticipant {

  var tradeID: Option[String] = None

   model("Trade Capture") {

     state("Trader Reviews Trade") {
       event: NewBrokerTrade =>
         tradeID = Some(event.brokerTradeID)
     }

     state("Trader Accepts") {
       event: ConfirmBrokerTrade =>
     }

     state("Notify RTTM") {
       event: Completed =>
         workflow.send("RTTM", IncomingFirmTrade(tradeID.get))
     }

     state("Trader Rejects") {
       event: RejectBrokerTrade =>
     }

     transition from start to "Trader Reviews Trade" on NewBrokerTrade
     transition from "Trader Reviews Trade" to "Trader Accepts" on ConfirmBrokerTrade
     flow from "Trader Accepts" to "Notify RTTM"
     flow from "Notify RTTM" to terminal
     transition from "Trader Reviews Trade" to "Trader Rejects" on RejectBrokerTrade

   }

 }
