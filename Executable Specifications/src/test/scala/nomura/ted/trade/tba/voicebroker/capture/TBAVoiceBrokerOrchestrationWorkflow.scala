package nomura.ted.trade.tba.voicebroker.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.StateMachine

import scala.collection.mutable

/**
 * Created by usoemard on 8/15/2014.
 */
class TBAVoiceBrokerOrchestrationWorkflow extends StateMachine("Test") with OrchestrationWorkflow("TBA - Voice Broker - Orchstration Workflow") {


  def participants: mutable.HashMap[String, StateMachine] = (
    "Trader" -> ,
    "Broker", "RTTM")

  def start(): Unit = {
    send("Broker", Completed())
  }


}
