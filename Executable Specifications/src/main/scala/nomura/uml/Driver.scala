package nomura.uml

import java.io.{FileOutputStream, PrintWriter}
import nomura.uml.LifeCycleEvents.Completed


case class Run()

case class Wait()

case class Kill()

class AmendEventStateModel extends StateModel {

  model("Test Amend Event") {
    state("Sleep") {
      event: Kill =>

    }
    state("Awake") {
      event: Run =>

    }

    state("Waiting") {
      event: Wait =>
    }
    transition from "Start" to "Sleep" on Completed
    transition from "Sleep" to "Awake" on Run
    transition from "Awake" to "Waiting" on Wait
    transition from "Waiting" to "Final" on Kill
  }
}

object Driver {

  def main(args: Array[String]): Unit = {
    println("Testing State Machines")

    val blockTrade = new BlockTrade()
    val amendEvent = new AmendEventStateModel()

    //    assert(BlockTradeStateModel.root.states.size == 3, s"model.states.size = ${BlockTradeStateModel.root.states.size}")
    //    val capture = BlockTradeStateModel.root.states("Capture").asInstanceOf[CompositeState]
    //    assert(capture.states.size == 6)
    //    val executed = capture.states("Executed")
    //    assert(executed.name.startsWith("Executed"))
    //    assert(executed.transitions.size == 1)
    //    assert(BlockTradeStateModel.root.start.transitions.size == 1)

    val pw = new PrintWriter(new FileOutputStream("states.puml"))

    StateModelRegistry.dump(blockTrade.modelRoot.name, pw)
    StateModelRegistry.dump(amendEvent.modelRoot.name, pw)

    pw.close()
  }
}
