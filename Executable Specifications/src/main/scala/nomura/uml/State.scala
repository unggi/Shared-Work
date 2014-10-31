package nomura.uml

import scala.collection.mutable.HashMap
import scala.collection.mutable
import nomura.uml.LifeCycleEvents.Completed


abstract class State(val name: String, val parent: Option[CompositeState]) {

  val transitions = new HashMap[Class[_], State]()

  def transition(targetState: State, onEventOfClass: Class[_]): Unit = transitions.put(onEventOfClass, targetState)

  def invoke(any: Any): Unit = {}

  def hasCompletedTransition: Boolean = transitions.contains(classOf[Completed])

  def hasParent: Boolean = parent.isDefined

  def transitionForEvent(eventOfClass: Class[_]): Option[State] = transitions.get(eventOfClass)

  def hasTransitionForEvent(eventOfClass: Class[_]): Boolean = transitions.contains(eventOfClass)

}

class ActionState[T](stateName: String, parent: Option[CompositeState])(onEntryAction: (T) => Unit) extends State(stateName, parent) {

  def onEntry(event: T): Unit = onEntryAction(event)

  override def invoke(any: Any): Unit =
  try {
    onEntry(any.asInstanceOf[T])
  }
  catch {
    case e: ClassCastException =>

      System.err.println(s"Invalid Event Type Received by State Machine [${getClass.getSimpleName}]: Action State [${stateName}] received ${any.getClass.getSimpleName}")
      System.err.println(s"Exception Message: ${e.getMessage}")
      System.err.println("Stack Trace: ")
      e.printStackTrace(System.err)
  }


}

class InitialState(parent: CompositeState) extends State("[*]", Some(parent))

class FinalState(parent: CompositeState) extends State("[*]", Some(parent))


object CompositeState {

  val START_STATE = "Start"
  val TERMINAL_STATE = "Final"

}


class CompositeState(stateName: String, override val parent: Option[CompositeState]) extends State(stateName, parent) {

  val states = new mutable.HashMap[String, State]()

  val start = new InitialState(this)

  val terminal = new FinalState(this)

  states.put(CompositeState.START_STATE, start)
  states.put(CompositeState.TERMINAL_STATE, terminal)

  var currentState: Option[State] = Some(start)

  def findState(s: String): Option[State] =
    states.get(s) match {
      case Some(state: State) => Some(state)
      case None =>
        if (parent.isDefined)
          parent.get.findState(s)
        else
          None
    }
}








 

