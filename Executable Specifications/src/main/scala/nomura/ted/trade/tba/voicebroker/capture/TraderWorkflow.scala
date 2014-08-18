package nomura.ted.trade.tba.voicebroker.capture

import akka.actor.ActorRef
import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.{DispatchToParticipant, ParticipantHasTerminated, StateMachine, WorkflowParticipant}

class TraderWorkflow(_workflow: ActorRef)
  extends StateMachine("Trade Capture Workflow")
  with WorkflowParticipant {

  var tradeID: Option[String] = None

  def workflow: ActorRef = _workflow

  model("Trader Workflow") {

    state("Create Trade") {
      event: NewBrokerTrade =>
        tradeID = Some(event.brokerTradeID)
    }

    state("Trader Reviews Trade") {
      event: Completed =>

    }

    state("Trader Accepts") {
      event: ConfirmBrokerTrade =>
    }

    state("Notify RTTM") {
      event: Completed =>
        workflow ! DispatchToParticipant("RTTM", IncomingDealerTrade(tradeID.get))
    }

    state("Update Trade to Confirmed State") {
      event: Completed =>
    }

    state("Notify Orchestrator") {
      event: Completed =>
        workflow ! ParticipantHasTerminated(name)
    }


    state("Trader Rejects") {
      event: RejectBrokerTrade =>
    }

    transition from start to "Create Trade" on NewBrokerTrade
    flow from "Create Trade" to "Trader Reviews Trade"
    transition from "Trader Reviews Trade" to "Trader Rejects" on RejectBrokerTrade
    transition from "Trader Reviews Trade" to "Trader Accepts" on ConfirmBrokerTrade
    flow from "Trader Accepts" to "Notify RTTM"
    flow from "Notify RTTM" to "Update Trade to Confirmed State"
    flow from "Update Trade to Confirmed State" to "Notify Orchestrator"
    flow from "Notify Orchestrator" to terminal
  }

}
