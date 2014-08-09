package nomura.uml

import akka.actor.ActorRef

import scala.collection.mutable

/**
 * Created by usoemard on 8/8/2014.
 */
trait Workflow {

   def validParticipants: Set[String]

   def name: String

   val switchboard = new mutable.HashMap[String, ActorRef]()

   def send(participant: String, message: Any): Boolean =
     switchboard.get(participant) match {
       case Some(a) =>
         a ! message
         true
       case None =>
         false
     }

   def register(participant: String, actorRef: ActorRef) {
     require(validParticipants.contains(participant), s"Participant [$participant] is not a valid participant. Should be one of ${validParticipants}")
     switchboard.put(participant, actorRef)
   }

   def start(): Unit

 }
