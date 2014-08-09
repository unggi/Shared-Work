package nomura.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.Workflow

/**
 * Created by usoemard on 8/8/2014.
 */
class TBAVoiceBrokerTradeWorkflow(val name: String) extends Workflow {

   def validParticipants: Set[String] = Set("Trader", "Broker", "RTTM")

   def start(): Unit = {
     send("Broker", Completed())
   }

 }
