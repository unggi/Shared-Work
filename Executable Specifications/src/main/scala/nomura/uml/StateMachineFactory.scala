package nomura.uml

import akka.event.Logging
import akka.actor.{Props, ActorRef}

/**
 * Created by unggi on 5/4/14.
 */
object StateMachineFactory {

  val log = Logging(StateMachineActorSystem.system, "State Machine Factory")

  def createByName(modelName: String, instanceName: String): Option[ActorRef] = {
    modelName match {
//      case "RFQ" =>
//        val actor = StateMachineActorSystem.system.actorOf(Props[RFQ], name = instanceName)
//        actor ! Completed()
//        Some(actor)
      case _ =>
        log.error(s"Invalid model name <${modelName}> for instance <${instanceName}")
        None
    }
  }
}
