package nomura.uml

import org.scalatest.{MustMatchers, WordSpecLike, BeforeAndAfterAll}
import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import nomura.uml.LifeCycleEvents.Completed


case class Advance()

class DeepHierarchy extends StateMachine("DeepHierarchy") {
  var depth = 0

  model("Deep Hierarchy Root") {

    composite("Level 1") {

      composite("Level 2") {

        composite("Level 3") {

          state("Level3a") {
            event: Completed =>
              depth = 3
          }

          state("Level3b") {
            event: Advance =>
              depth = 3
          }
          flow from start to "Level3a"
          transition from "Level3a" to "Level3b" on Advance
          flow from "Level3b" to terminal
        }
        flow from start to "Level 3"
        flow from "Level 3" to terminal
      }
      flow from start to "Level 2"
      flow from "Level 2" to terminal
    }
    flow from start to "Level 1"
    flow from "Level 1" to terminal
  }

}

class IntermediateCompositeState extends StateMachine("IntermediateCompositeState") {

  model("IntermediateCompositeState") {

    composite("Composite 1") {
      state("State 1") {
        event: Advance =>

      }

      state("State 2") {
        event: Advance =>
      }

      transition from start to "State 1" on Advance
      transition from "State 1" to "State 2" on Advance
      transition from "State 2" to terminal on Completed

    }

    transition from start to "Composite 1" on Advance

    state("State 3") {
      event: Completed =>
    }

    state("State 4") {
      event: Advance =>
    }

    state("State 5") {
      event: Advance =>

    }

    composite("Composite 6") {

      state("State 7") {
        event: Advance =>

      }

      transition from start to "State 7" on Advance
      transition from "State 7" to terminal on Completed
    }

    transition from "Composite 1" to "State 3" on Completed
    transition from "State 3" to "State 4" on Advance
    transition from "State 4" to "State 5" on Advance
    transition from "State 5" to "Composite 6" on Advance
    transition from "Composite 6" to terminal on Completed
  }
}

case class PrivateMessage()

class SelfSender extends StateMachine("SelfSender") {
  model("SelfSendingActor") {

    state("State 1") {
      event: Advance =>
        sendSelf(PrivateMessage())
    }

    state("State 2") {
      event: Completed =>
    }

    state("State 3") {
      event: PrivateMessage =>
    }

    state("State 4") {
      event: Advance =>
    }

    transition from start to "State 1" on Advance
    transition from "State 1" to "State 2" on Completed
    transition from "State 2" to "State 3" on PrivateMessage
    transition from "State 3" to "State 4" on Advance
    transition from "State 4" to terminal on Completed

  }
}

class TestStateModels extends TestKit(ActorSystem("Testing"))
with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(StateMachineActorSystem.system)
  }

  """Hierarchical State Model with deep hierarchy""" when {

    val actorRef = TestActorRef[DeepHierarchy]
    val actor = actorRef.underlyingActor

    def state: String = actor.currentState.name
    def parent: Option[State] = actor.currentState.parent

    """should move to "Level3a" when initialized """ in {
      state mustEqual "Level3a"
    }

    """should move to "Terminal State" after Advance()""" in {
      actorRef ! Advance()
      state mustEqual "[*]"
    }

    """should be at top level state""" in {
      parent must not be empty
      parent.get.name mustEqual "Deep Hierarchy Root"
      actor.depth mustEqual 3
    }
  }

  "Hierarchical State Model with an intermediate composite state" when {

    val actorRef = TestActorRef[IntermediateCompositeState]
    val actor = actorRef.underlyingActor

    def state: String = actor.currentState.name
    def parent: Option[State] = actor.currentState.parent

    "Should be in initial state after starting" in {
      state mustEqual "[*]"
    }

    "Should move to State 1 on Advance" in {
      actorRef ! Advance()
      state mustEqual "State 1"
    }

    "Should move to State 3 on Advance" in {
      actorRef ! Advance()
      state mustEqual "State 3"
    }

    "Should move to State 4 on Advance" in {
      actorRef ! Advance()
      state mustEqual "State 4"
    }

    "Should move to State 5 on Advance" in {
      actorRef ! Advance()
      state mustEqual "State 5"
    }

    "Should move to Terminal on Advance" in {
      actorRef ! Advance()
      parent.getOrElse(actor.modelRoot).name mustEqual ("IntermediateCompositeState")
      state mustEqual "[*]"
    }
  }


  "Self Send Example" when {

    val actorRef = TestActorRef[SelfSender]
    val actor = actorRef.underlyingActor

    def state: String = actor.currentState.name
    def parent: Option[State] = actor.currentState.parent

    "Should be in initial state after starting" in {
      state mustEqual "[*]"
    }

    "Should move to State 3 after Advance and then a self send of Private Message" in {
      actorRef ! Advance()
      state mustEqual "State 3"
    }

    "Should move to Terminal State after Advance " in {
      actorRef ! Advance()
      state mustEqual "[*]"
    }

  }
}
