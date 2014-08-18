package nomura.testutils

import java.util.concurrent.TimeUnit

import akka.actor.ActorRef
import akka.pattern.ask
import akka.testkit.TestActorRef
import akka.util.Timeout
import nomura.uml.{RequestParticipantStates, ResponseParticipantStates, StateMachine}
import org.scalatest.MustMatchers

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * A helper trait to allow Scalatest units to check states of a state machine directly.
 */

trait StateTestMatcher {
  self: MustMatchers =>

  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  implicit val duration = Duration(5, TimeUnit.SECONDS)

  def packageAsString: String = self.getClass.getPackage.getName + "."

  implicit class StateTestHelper[T <: StateMachine](actor: TestActorRef[T]) {
    def mustBeInState(stateName: String) {
      actor.underlyingActor.currentState.name mustEqual stateName
    }

    def mustBeInTerminalState() {
      actor.underlyingActor.currentState.name mustEqual "[*]"
    }
  }

  implicit class WorkflowTester(workflow: ActorRef) {

    def currentStateOf(participant: String): String = {

      val reply = query()
      val description = reply.participantStates.get(participant)
      require(description.isDefined, s"Incorrect participant name in request for current state of [$participant}]")

      description.get.currentState
    }

    private def query(): ResponseParticipantStates = {
      val future = workflow ? RequestParticipantStates()
      Await.result(future, duration).asInstanceOf[ResponseParticipantStates]
    }

    def printCurrentStates(): Unit = {
      val reply = query()
      val map = reply.participantStates
      System.err.println("\n\nWorkflow States")
      for ((name, description) <- map) {
        System.err.print(s"   <${name}> is in state <${description.currentState}> and is waiting on ")
        for (event <- description.nextEvents)
          System.err.print(event.replace(packageAsString, "") + ", ")
        System.err.println("")
      }
    }
  }

}