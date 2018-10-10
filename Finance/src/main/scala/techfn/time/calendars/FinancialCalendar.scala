package techfn.time.calendars

import org.joda.time.{DateTime, Period}
import techfn.time.TimeUnit
import techfn.time.daycounters.BusinessDayConvention

class FinancialCalendar(holidays: HolidayCalendar) {

  import BusinessDayConvention._
  import TimeUnit._
  import techfn.util.DateMath._

  def isHoliday(date: DateTime): Boolean = holidays.isHoliday(date)

  def isBusinessDay(date: DateTime): Boolean = holidays.isBusinessDay(date)

  def isEndOfMonth(date: DateTime): Boolean =
    date.monthOfYear.get != adjust(date.plusDays(1), Following).monthOfYear().get

  def endOfMonth(date: DateTime): DateTime =
    adjust(date.endOfMonth, Preceding)


  def advance(date: DateTime, period: Period, businessDayConvention: BusinessDayConvention.Value, endOfMonth: Boolean): DateTime = {
    var tmp = advance(date, period.getYears * 12 + period.getMonths, Months, businessDayConvention, endOfMonth)
    tmp = advance(tmp, period.getWeeks, Weeks, businessDayConvention, endOfMonth)
    advance(tmp, period.getDays, Days, businessDayConvention, endOfMonth)
  }

  def advance(date: DateTime, count: Int, unit: TimeUnit.Value, businessDayConvention: BusinessDayConvention.Value, preserveEndOfMonth: Boolean): DateTime = {
    if (count != 0)
      unit match {
        case Days =>
          if (count > 0)
            date.forward(count, isHoliday)
          else
            date.backward(-count, isHoliday)
        case Weeks =>
          val d1 = date.plusDays(count * 7)
          adjust(d1, businessDayConvention)
        case Months =>
          val d1 = date.plusMonths(count)
          if (preserveEndOfMonth && isEndOfMonth(date))
            endOfMonth(d1)
          else
            adjust(d1, businessDayConvention)
      }
    else
      date
  }

  def backward(date: DateTime, count: Int, unit: TimeUnit.Value, businessDayConvention: BusinessDayConvention.Value, endOfMonth: Boolean): DateTime =
    advance(date, -count, unit, businessDayConvention, endOfMonth)

  def backward(date: DateTime, period: Period, businessDayConvention: BusinessDayConvention.Value, endOfMonth: Boolean): DateTime = {
    var unit = Months
    val count: Int =
      if (period.getDays != 0) {
        unit = Days
        period.getDays
      } else if (period.getWeeks != 0) {
        unit = Weeks
        period.getWeeks
      } else if (period.getMonths != 0) {
        unit = Months
        period.getMonths
      }
      else {
        assert(false, s"Invalid period type: $period")
        0
      }

    advance(date, -count, unit, businessDayConvention, endOfMonth)
  }


  def adjust(date: DateTime, businessDayConvention: BusinessDayConvention.Value): DateTime =
    businessDayConvention match {
      case Following =>
        date.forward(isHoliday)
      case ModifiedFollowing =>
        val d = date.forward(isHoliday)
        if (!date.isSameMonth(d))
          adjust(date, Preceding)
        else
          d
      case HalfMonthlyModifiedFollowing =>
        val d = date.forward(isHoliday)
        if (!date.isSameMonth(d) || (date.dayOfMonth.get <= 15 && d.dayOfMonth().get > 15))
          adjust(date, Preceding)
        else
          d

      case Preceding =>
        date.backward(isHoliday)
      case ModifiedPreceding =>
        val d = date.backward(isHoliday)
        if (!date.isSameMonth(d))
          adjust(date, Following)
        else
          d
      case Nearest =>
        val future = date.forward(isHoliday)
        val past = date.backward(isHoliday)
        if (daysBetween(date, future) > daysBetween(past, date))
          past
        else
          future
      case Unadjusted =>
        date
    }

}
