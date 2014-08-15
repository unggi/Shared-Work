package nomura.ted.trade.tba.voicebroker.capture

import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.{WorkflowParticipant, Workflow}

import scala.collection.mutable


trait OrchestrationWorkflow extends Workflow {

  def name: String

  def participants: mutable.HashMap[String, WorkflowParticipant]

  def start(): Unit

}
