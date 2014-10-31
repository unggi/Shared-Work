package nomura.ted.trade.tba.voicebroker.capture

import akka.actor.{Actor, ActorRef, Props}
import nomura.uml.{OrchestrationWorkflow, StateMachineActorSystem}

object BusinessProcess {
  val TRADER = "Trader"
  val BROKER = "Broker"
  val RTTM = "RTTM"


  def create(name: String): ActorRef = {
    // Create the Business Process as a system level actor
    StateMachineActorSystem.system.actorOf(Props(new BusinessProcess(name)))
  }

}

class BusinessProcess(val _name: String)
  extends Actor with OrchestrationWorkflow {

  val broker = context.actorOf(Props(classOf[BrokerWorkflow], self))
  val trader = context.actorOf(Props(classOf[TraderWorkflow], self))
  val rttm = context.actorOf(Props(classOf[RTTMWorkflow], self))

  register(BusinessProcess.TRADER, trader)
  register(BusinessProcess.BROKER, broker)
  register(BusinessProcess.RTTM, rttm)

}

