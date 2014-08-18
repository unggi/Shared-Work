package nomura.ted.trade.tba.voicebroker.capture

import akka.actor.ActorSystem
import akka.testkit.TestKit
import nomura.testutils.StateTestMatcher
import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.TraceFacility
import org.scalatest._

class BusinessProcessTest
  extends TestKit(ActorSystem("TED-World"))
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll
  with StateTestMatcher {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
    TraceFacility.printAll
  }

  "Broker Trade Capture is Successful" when {
    val process = new BusinessProcess("Test TBA Broker Trade")

    "Broker is in wait state on verbal execution of trade after initialization" in {
      process.sendToParticipant("Broker", Completed())
    }

  }


}
