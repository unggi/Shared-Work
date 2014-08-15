package nomura.testutils

import akka.testkit.TestActorRef
import nomura.uml.StateMachine
import org.scalatest.MustMatchers

/**
 * A helper trait to allow Scalatest units to check states of a state machine directly.
 */

trait StateTestMatcher {
  self: MustMatchers =>

  implicit class StateTestHelper[T <: StateMachine](actor: TestActorRef[T]) {
    def mustBeInState(stateName: String) {
      actor.underlyingActor.currentState.name mustEqual stateName
    }

    def mustBeInTerminalState() {
      actor.underlyingActor.currentState.name mustEqual "[*]"
    }
  }

}