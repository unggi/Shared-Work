package nomura.ted.trade.tba.voicebroker.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.{StateMachine, Workflow, WorkflowParticipant}

class BrokerWorkflow(val workflow: Workflow) extends StateMachine("Broker Trade Booking Workflow") with WorkflowParticipant {

  var trade: Option[NewBrokerTrade] = None

   model("Broker Workflow") {

     state("Verbal Execution of Trade") {
       event: Completed =>
     }

     state("Enter Broker Trade") {
       event: NewBrokerTrade =>
         trade = Some(event)
     }

     state("Post Trade Notification") {
       event: Completed =>
         // Tell the trader via the post trade feed that there is a new trade
         workflow.send("Trader", trade.get)
     }

     state("Broker Report to RTTM") {
       event: Completed =>
         workflow.send("RTTM", IncomingBrokerTrade(trade.get.brokerTradeID))
     }

     flow from start to "Verbal Execution of Trade"
     transition from "Verbal Execution of Trade" to "Enter Broker Trade" on NewBrokerTrade
     flow from "Enter Broker Trade" to "Post Trade Notification"
     flow from "Post Trade Notification" to "Broker Report to RTTM"
     flow from "Broker Report to RTTM" to terminal
   }
 }