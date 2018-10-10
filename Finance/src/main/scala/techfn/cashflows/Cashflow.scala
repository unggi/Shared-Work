package techfn.cashflows

import org.joda.time.DateTime
import techfn.time.daycounters.DayCounter
import techfn.util.{DateMath, Types}
import techfn.util.Types.Term

case class Cashflow(override val date: DateTime, argAmount: Types.Real) extends Event(date) {

  def amount(refDate: DateTime): Types.Real = argAmount

  def hasOccurred(refDate: DateTime): Boolean = {
    DateMath.compare(date, refDate) match {
      case -1 => // Before
        true
      case 0 => // Equal
        false
      case 1 => // After
        false
    }
  }

  def getStepwiseDiscountTerm(aDayCounter: DayCounter, npvDate: DateTime, lastDate: DateTime): Term = {
    val refStartDate =
      if (lastDate.isEqual(npvDate))
        date.minusYears(1)
      else
        lastDate
    aDayCounter.yearFraction(lastDate, date, Some(refStartDate), Some(date))
  }
}
