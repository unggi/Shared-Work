package techfn.time.daycounters

object BusinessDayConvention extends Enumeration {

  val Following: Value = Value("Following")
  val ModifiedFollowing: Value = Value("ModifiedFollowing")
  val Preceding: Value = Value("Preceding")
  val ModifiedPreceding: Value = Value("ModifiedPreceding")
  val Unadjusted: Value = Value("Unadjusted")
  val HalfMonthlyModifiedFollowing: Value = Value("HalfMonthlyModifiedFollowing")
  val Nearest: Value = Value("Nearest")
}
