package nomura.capture

import nomura.uml.{StateMachine, Workflow, WorkflowParticipant}

/**
 * Created by usoemard on 8/8/2014.
 */
class TraderTradeCaptureWorkflow(val workflow: Workflow) extends StateMachine("Trade Capture Workflow") with WorkflowParticipant {
   model("Trade Capture") {

     state("Trader Reviews Trade") {
       event: NewBrokerTrade =>

     }

     state("Trader Accepts") {
       event: ConfirmBrokerTrade =>

     }

     state("Trader Rejects") {
       event: RejectBrokerTrade =>
     }
     transition from start to "Trader Reviews Trade" on NewBrokerTrade
     transition from "Trader Reviews Trade" to "Trader Accepts" on ConfirmBrokerTrade
     transition from "Trader Reviews Trade" to "Trader Rejects" on RejectBrokerTrade
     flow from "Trader Accepts" to terminal
   }

 }
