package nomura.uml

trait BusinessEvent {

}

object LifeCycleEvents {

  case class Initialize() extends BusinessEvent

  case class Terminate() extends BusinessEvent

  case class Completed() extends BusinessEvent

  case class QueryState() extends BusinessEvent

  case class QueryStateReply(description: StateDescription) extends BusinessEvent

  case class StateDescription(currentState: String, nextEvents: Array[String])

}

