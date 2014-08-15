package nomura.ted.trade.tba.voicebroker.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.Workflow


class OrchestrationWorkflow(val name: String) extends Workflow {

   def validParticipants: Set[String] = Set("Trader", "Broker", "RTTM")

   def start(): Unit = {
     send("Broker", Completed())
   }

 }
