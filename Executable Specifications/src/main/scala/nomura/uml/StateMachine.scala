package nomura.uml

import akka.actor.Actor
import akka.event.Logging
import nomura.uml.LifeCycleEvents.{Completed, QueryState}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

abstract class StateMachine(val name: String) extends Actor with StateModel {

  val log = Logging(context.system, this)

  /**
   * Useful String constants
   */
  val start = CompositeState.START_STATE
  val terminal = CompositeState.TERMINAL_STATE
  val selfMessageQueue = new mutable.Queue[Any]
  var currentState: State = _

  override def preStart() {

    require(modelRoot != null, "Model Root hasn't been initialized - construction sequence error")

    //
    // Then move to the first state which can receive an event and is reachable by control flow
    // from the start node.
    //
    currentState = flowOutOfState(modelRoot.start)
  }

  def receive = {
    case query: QueryState =>
      sender() ! currentState.name
    case event =>
      //
      // Make sure the sender didn't send an object rather than a class instance.
      // This is a common accident!!!
      require(!event.getClass.getSimpleName.endsWith("$"),
        s"""Did you accidentally forget to instantiate a message?
           |Found a static class event ${event.getClass.getCanonicalName}""".stripMargin)

      // Process an external event
      processEvent.applyOrElse(event, unhandled)

      // then process self messages that were sent during the action method of the
      // target state.
<<<<<<< HEAD
      while (selfMessageQueue.nonEmpty) {
        val msg = selfMessageQueue.dequeue()
=======
      while (!selfMessageQueue.isEmpty) {
        val msg = selfMessageQueue.dequeue()
        log.debug("Process Dequeued Message: " + msg.toString)
>>>>>>> Multiple fixes to Broker Trade Workflow
        processEvent.applyOrElse(msg, unhandled)
      }
  }

  def processEvent: Receive = {

    case event: Any =>
      trace(this, event) {
        currentState.transitions.get(event.getClass) match {

          case Some(targetState: CompositeState) =>
            currentState = targetState.start
            processEvent.applyOrElse(event, unhandled)

          case Some(finalState: FinalState) =>
            unhandled(event)

          case Some(initialState: InitialState) =>
            processEvent.applyOrElse(event, unhandled)

          case Some(targetState: State) =>
            currentState = targetState
            currentState.invoke(event)
          case _ =>
            unhandled(_)
        }


        // follow the control flow from the new state
        currentState = flowOutOfState(currentState)
      }
  }


  def flowOutOfState(from: State): State =
    from.transitionForEvent(classOf[Completed]) match {

      case Some(targetState: CompositeState) =>
        targetState.start.invoke(Completed())
        flowOutOfState(targetState.start)

      case Some(targetState: InitialState) =>
        flowOutOfState(targetState)

      case Some(targetState: FinalState) =>
        if (targetState.hasParent && targetState.parent.get != modelRoot)
          flowOutOfState(targetState.parent.get)
        else
          targetState

      case Some(targetState: State) =>
        targetState.invoke(Completed())
        flowOutOfState(targetState)

      case None =>
        from
    }

  override def unhandled(event: Any): Unit =
    log.error(s"Unhandled message type in state [${currentState.name}]: no transition for [${event.getClass}}]")

  protected def sendSelf(message: Any): Unit =
    selfMessageQueue.enqueue(message)

  def trace[T](stateMachine: StateMachine, event: Any)(body: => T): T = {
    val record = StateTraceRecord(stateMachine.name, currentState.name, "<not available>", event.getClass.getSimpleName)
    TraceFacility.records.append(record)
    val result: T = body
    record.toState = currentState.name

    result
  }

}


case class StateTraceRecord(machine: String, fromState: String, var toState: String, onEvent: String)

object TraceFacility {
  var records = new ListBuffer[StateTraceRecord]()

  def printAll(): Unit = {
    System.err.println("\n\n\n=========================")
    val indent = "                                 "
    for (r <- records)
      System.err.println(f"[${r.machine}%30.30s] FROM <${r.fromState}>\n${indent}TO <${r.toState}>\n${indent}ON <${r.onEvent}>")
    System.err.println("\n=========================\n\n\n")
  }

}
