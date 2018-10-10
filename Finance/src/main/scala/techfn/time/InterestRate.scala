package techfn.time

import org.joda.time.DateTime
import techfn.time.daycounters.DayCounter
import techfn.util.Types.{DiscountFactor, Real}
import techfn.util.{DateMath, Types}

class InterestRate(val rate: Types.Rate,
                   val dayCounter: DayCounter,
                   val compounding: Compounding.Value,
                   val freqMakesSense: Boolean,
                   val frequency: Real) {

  import Compounding._

  def compoundFactor(term: Types.Term): DiscountFactor = {
    require(term > 0.0, s"Negative term $term not allowed")
    compounding match {
      case Simple =>
        1.0 + rate * term
      case Compounded =>
        math.pow(1.0 + rate / frequency, frequency * term)
      case Continuous =>
        math.exp(rate * term)
      case SimpleThenCompounded =>
        if (term <= 1.0 / frequency)
          1.0 + rate * term
        else
          math.pow(1.0 + rate / frequency, frequency * term)
      case CompoundedThenSimple =>
        if (term > 1.0 / frequency)
          1.0 + rate * term
        else
          math.pow(1.0 + rate / frequency, frequency * term)
      case any =>
        assert(false, s"Unknown compounding conventiona: $any")
        0.0
    }

  }

  def compoundFactor(d1: DateTime, d2: DateTime, refStart: Option[DateTime], refEnd: Option[DateTime]): DiscountFactor = {
    require(DateMath.isAfter(d2, d1), s"d2 must be greater than d1 $d2 vs $d1")
    compoundFactor(dayCounter.yearFraction(d1, d2, refStart, refEnd))
  }

  def discountFactor(term: Types.Term): DiscountFactor =
    1.0 / compoundFactor(term)

  def discountFactor(d1: DateTime, d2: DateTime, refStart: Option[DateTime], refEnd: Option[DateTime]): Unit =
    discountFactor(dayCounter.yearFraction(d1, d2, refStart, refEnd))


}
