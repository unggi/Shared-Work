package nomura.uml

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef}
import akka.pattern.ask
import akka.util.Timeout
import nomura.uml.LifeCycleEvents.{QueryState, QueryStateReply, StateDescription}

import scala.collection._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

case class DispatchToParticipant(participant: String, message: Any)

case class RegisterParticipant(participant: String, actorRef: ActorRef)

case class ParticipantHasTerminated(participant: String)

case class DispatchToTarget(sendingParticipant: String, message: Any)

case class RequestParticipantStates()

case class ResponseParticipantStates(participantStates: immutable.Map[String, StateDescription])

trait OrchestrationWorkflow {

  self: Actor =>

  val switchboard = new mutable.HashMap[String, ActorRef]()

  var participantCount = 0

  // Sub-class will set and manage life of target
  var target: Option[ActorRef] = None

  override def receive: Actor.Receive = {
    case DispatchToParticipant(participant, message) =>
      sendToParticipant(participant, message)
    case RegisterParticipant(participant, actorRef) =>
      register(participant, actorRef)
    case ParticipantHasTerminated(participant) =>
      participantCount -= 1
    case DispatchToTarget(sendingParticipant, message) =>
      sendToTarget(message)
    case RequestParticipantStates() =>
      sender() ! requestParticipantStates()
  }

  def requestParticipantStates(): ResponseParticipantStates = {

    implicit val timeout = Timeout(5, TimeUnit.SECONDS)
    implicit val duration = Duration(5, TimeUnit.SECONDS)

    val list =
      for ((participant, actorRef) <- switchboard) yield {

        val future = actorRef ? QueryState()
        val reply = Await.result(future, duration).asInstanceOf[QueryStateReply]
        (participant -> reply.description)
      }

    ResponseParticipantStates(list.toMap)
  }

  def sendToTarget(message: Any): Unit = {
    require(target.isDefined, "Workflow target has not been set")
    target.get ! message
  }

  def register(participant: String, actorRef: ActorRef) {
    switchboard.put(participant, actorRef)
    participantCount += 1
  }

  def sendToParticipant(participant: String, message: Any): Unit =
    switchboard.get(participant) match {
      case Some(a) =>
        a ! message
      case None =>
        require(false, s"Attempting to send message to invalid participant: ${participant}")
    }
}
