package nomura.ted.trade.tba.voicebroker.capture

import _root_.nomura.uml.LifeCycleEvents.Completed
import akka.actor.ActorRef
import nomura.uml.{DispatchToParticipant, StateMachine, WorkflowParticipant}

class BrokerWorkflow(_workflow: ActorRef)
  extends StateMachine("Broker Trade Booking Workflow")
  with WorkflowParticipant {
  var trade: Option[NewBrokerTrade] = None

  def workflow = _workflow

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
        workflow ! DispatchToParticipant("Trader", trade.get)
    }

    state("Broker Report to RTTM") {
      event: Completed =>
        workflow ! DispatchToParticipant("RTTM", IncomingBrokerTrade(trade.get.brokerTradeID))
    }

    flow from start to "Verbal Execution of Trade"
    transition from "Verbal Execution of Trade" to "Enter Broker Trade" on NewBrokerTrade
    flow from "Enter Broker Trade" to "Post Trade Notification"
    flow from "Post Trade Notification" to "Broker Report to RTTM"
    flow from "Broker Report to RTTM" to terminal
  }
}
