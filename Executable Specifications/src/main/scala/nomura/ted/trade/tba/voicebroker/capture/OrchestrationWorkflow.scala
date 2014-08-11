package nomura.ted.trade.dba.voicebroker.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.Workflow

/**
 * Created by usoemard on 8/8/2014.
 */
class OrchestrationWorkflow(val name: String) extends Workflow {

   def validParticipants: Set[String] = Set("Trader", "Broker", "RTTM")

   def start(): Unit = {
     send("Broker", Completed())
   }

 }
