package nomura.uml



case class BlockExecute(name: String) extends BusinessEvent

case class BlockConfirm() extends BusinessEvent

case class BlockCancel() extends BusinessEvent

case class BlockAmend() extends BusinessEvent

case class BlockAllocate() extends BusinessEvent

case class BlockCheckedOut() extends BusinessEvent

class BlockTrade extends StateModel {

  import LifeCycleEvents._

  model("Test Block Trade") {

    composite("Capture") {
      state("Executed") {
        event: BlockExecute =>
      }
      composite("Confirmed") {
        state("WaitingForCheckout") {
          event: BlockConfirm =>
        }
        state("CheckedOut") {
          event: BlockCheckedOut =>
        }

        transition from "Start" to "WaitingForCheckout" on BlockConfirm
        transition from "WaitingForCheckout" to "CheckedOut" on BlockCheckedOut
        transition from "CheckedOut" to "Final" on Completed
      }

      state("Amending") {
        event: BlockAmend =>
      }

      state("Cancelled") {
        event: BlockCancel =>
      }

      transition from "Start" to "Executed" on BlockExecute
      transition from "Executed" to "Confirmed" on BlockConfirm
      transition from "Confirmed" to "Amending" on BlockAmend
      transition from "Confirmed" to "Cancelled" on Completed
      transition from "Cancelled" to "Final" on BlockCancel
      transition from "Amending" to "Final" on Completed
    }

    transition from "Start" to "Capture" on BlockExecute
    transition from "Capture" to "Final" on Completed
  }
}
