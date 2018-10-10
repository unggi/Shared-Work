package techfn.time

object Frequency extends Enumeration {
  val NoFrequency: Value = Value(-1)
  val Once: Value = Value(0)
  val Annual: Value = Value(1)
  val Semiannual: Value = Value(2)
  val EveryFourthMonth: Value = Value(3)
  val Quarterly: Value = Value(4)
  val Bimonthly: Value = Value(6)
  val Monthly: Value = Value(12)
  val EveryFourthWeek: Value = Value(13)
  val Biweekly: Value = Value(26)
  val Weekly: Value = Value(52)
  val Daily: Value = Value(365)
  val OtherFrequency: Value = Value(999)

}
