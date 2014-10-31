package nomura.uml

import akka.actor.ActorRef

trait WorkflowParticipant {
  def workflow: ActorRef
}
