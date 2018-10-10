package techfn.time

object Compounding extends Enumeration {
  val Simple: Value = Value(0)
  val Compounded: Value = Value(1)
  val Continuous: Value = Value(2)
  val SimpleThenCompounded: Value = Value(3)
  val CompoundedThenSimple: Value = Value(4)
}
