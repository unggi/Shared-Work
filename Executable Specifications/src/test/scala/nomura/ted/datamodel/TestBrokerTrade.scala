package nomura.ted.datamodel

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import nomura.testutils.StateTestMatcher
import nomura.uml.{StateMachineActorSystem, TraceFacility}
import org.joda.time.LocalDate
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

class TestBrokerTrade
  extends TestKit(ActorSystem("TED-World"))
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll
  with StateTestMatcher {

  override def afterAll {
    TestKit.shutdownActorSystem(StateMachineActorSystem.system)
    TraceFacility.printAll
  }

  "Create a Broker Trade and Exercise its Lifecycle" when {

    val trade =
      TestActorRef(new BrokerTrade(
        Trader("BOB"),
        Broker("DLRW"),
        1000000.0,
        new TBA("FN", 4.0, 30, 10, 2014),
        890000.0,
        98.125,
        new LocalDate(2014, 8, 13),
        new LocalDate(2014, 8, 18)
      )
      )

    "Verify initialization of a Broker Trade" in {
      trade mustBeInState "Pending Front Office Confirmation"
    }

    "Accept a Trade" in {
      trade ! AcceptBrokerTrade()
      trade mustBeInTerminalState()
    }
  }
}