package nomura.uml

import com.typesafe.config.Config
import akka.actor.ActorSystem
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}

/**
 * Created by unggi on 5/25/14.
 */
class PriorityMailbox(settings: ActorSystem.Settings, config: Config) extends UnboundedPriorityMailbox(

  PriorityGenerator {
    case 'Exception => 0
    case 'SelfMessage => 1
    case 'Standard => 2
    case _ => 3
  }
) {

}
