package nomura.ted.trade.tba.voicebroker.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.{StateMachine, Workflow, WorkflowParticipant}

/**
 * Created by usoemard on 8/8/2014.
 */
class BrokerTradeBookingWorkflow(val workflow: Workflow) extends StateMachine("Broker Trade Booking Workflow") with WorkflowParticipant {

   model("Broker Trade Booking Workflow") {

     state("Verbal Execution of Trade") {
       event: Completed =>
     }

     state("Enter Broker Trade") {
       event: NewBrokerTrade =>
         // Book the trade here
         // then send myself a message that a new trade message
         sendSelf(event)

     }

     state("Post Trade Notification") {
       event: NewBrokerTrade =>
         // Tell the trader via the post trade feed that there is a new trade
         workflow.send("Trader", event)
         // Tell myself to proceed with the New Broker Trade
         sendSelf(event)
     }

     state("Broker Report to RTTM") {
       event: NewBrokerTrade =>
         workflow.send("RTTM", IncomingBrokerTrade())
     }

     flow from start to "Verbal Execution of Trade"
     transition from "Verbal Execution of Trade" to "Enter Broker Trade" on NewBrokerTrade
     transition from "Enter Broker Trade" to "Post Trade Notification" on NewBrokerTrade
     transition from "Post Trade Notification" to "Broker Report to RTTM" on NewBrokerTrade
     flow from "Broker Report to RTTM" to terminal
   }
 }
