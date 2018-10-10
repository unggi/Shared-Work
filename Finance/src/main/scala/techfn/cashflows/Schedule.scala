package techfn.cashflows

import org.joda.time.{DateTime, Period}
import techfn.time.calendars.FinancialCalendar
import techfn.time.daycounters.{BusinessDayConvention, DateGenerationRule}
import techfn.util.DateMath._

import scala.collection.mutable.ArrayBuffer

class Schedule(var dates: Array[DateTime],
               val calendar: FinancialCalendar,
               var tenor: Period,
               val businessDayConvention: BusinessDayConvention.Value,
               val terminationDateConvention: BusinessDayConvention.Value,
               val rule: DateGenerationRule.Value,
               val endOfMonth: Boolean,
               var isRegular: Array[Boolean]) {

  def isEmpty: Boolean = dates.isEmpty

  def adjust(date: DateTime): DateTime =
    calendar.adjust(date, businessDayConvention)

  def advance(date: DateTime, period: Period): DateTime =
    calendar.advance(date, period, businessDayConvention, endOfMonth)

  def multiply(count: Int, period: Period): Period = {

    val days = count * period.getDays
    val weeks = count * period.getWeeks
    val months = count * period.getMonths
    val years = count * period.getYears

    new Period(years, months, weeks, days, 0, 0, 0, 0)
  }

  def generate(effectiveDate: DateTime,
               terminationDate: DateTime,
               firstDate: DateTime,
               nextToLastDate: DateTime): Unit = {
    require(effectiveDate < firstDate)
    require(firstDate <= terminationDate)
    require(nextToLastDate <= terminationDate)

    // Buffers for accumulating as we generate forward
    val dateBuffer = new ArrayBuffer[DateTime]()
    val regularFlagBuffer = new ArrayBuffer[Boolean]()

    // Start with effectiveDate
    dateBuffer.append(effectiveDate)

    // Add first date
    dateBuffer.append(firstDate)
    // Determine if first period is regular or not
    var tmp = advance(effectiveDate, tenor)
    regularFlagBuffer.append(tmp === firstDate)
    // Iterate through dates until nextToLastDate
    var period = 1
    while (tmp < nextToLastDate) {
      tmp = advance(firstDate, multiply(period, tenor))
      if (adjust(dateBuffer.last) != adjust(tmp)) {
        dateBuffer.append(tmp)
        regularFlagBuffer.append(true)
      }
      period = period + 1
    }
    if (adjust(dateBuffer.last) != adjust(terminationDate)) {
      dateBuffer.append(terminationDate)
      regularFlagBuffer.append(tmp === terminationDate)
    }

    dates = dateBuffer.toArray
    isRegular = regularFlagBuffer.toArray
  }
}
