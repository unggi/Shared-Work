package techfn.util

import org.joda.time.{DateTime, DateTimeComparator, Days, Period}

object DateMath {

  def compare(date1: DateTime, date2: DateTime): Int =
    DateTimeComparator.getDateOnlyInstance.compare(date1, date2)

  def isAfter(date1: DateTime, date2: DateTime): Boolean =
    DateTimeComparator.getDateOnlyInstance.compare(date1, date2) > 0

  def isBefore(date1: DateTime, date2: DateTime): Boolean =
    DateTimeComparator.getDateOnlyInstance.compare(date1, date2) < 0

  def isEqual(date1: DateTime, date2: DateTime): Boolean =
    DateTimeComparator.getDateOnlyInstance.compare(date1, date2) == 0

  def daysBetween(d1: DateTime, d2: DateTime): Int =
    Days.daysBetween(d1.withTimeAtStartOfDay(), d2.withTimeAtStartOfDay()).getDays


  implicit class MyDate(d1: DateTime) {
    def >(d2: DateTime): Boolean = isAfter(d1, d2)

    def >=(d2: DateTime): Boolean = isAfter(d1, d2) || isEqual(d1, d2)

    def <(d2: DateTime): Boolean = isBefore(d1, d2)

    def <=(d2: DateTime): Boolean = isBefore(d1, d2) || isEqual(d1, d2)

    def ===(d2: DateTime): Boolean = isEqual(d1, d2)

    def !=(d2: DateTime): Boolean = !isEqual(d1,d2)

    def minusMonths(months: Int): DateTime =
      d1.minus(new Period(0, months, 0, 0, 0, 0, 0, 0))

    def addYears(years: Int): DateTime =
      d1.plus(new Period(years, 0, 0, 0, 0, 0, 0, 0))

    def addDays(days: Int): DateTime =
      d1.plus(new Period(0, 0, 0, days, 0, 0, 0, 0))

    def forward(condition: DateTime => Boolean): DateTime = {
      var tmp = d1
      while (condition(tmp))
        tmp = tmp.plusDays(1)
      tmp
    }

    def backward(condition: DateTime => Boolean): DateTime = {
      var tmp = d1
      while (condition(tmp))
        tmp = tmp.minusDays(1)
      tmp
    }

    def forward(n: Int, condition: DateTime => Boolean): DateTime = {
      require(n >= 0)
      var tmp = d1
      var count = n
      while (count > 0) {
        tmp = tmp.plusDays(1)
        tmp = tmp.forward(condition)
        count = count - 1
      }
      tmp
    }

    def backward(n: Int, condition: DateTime => Boolean): DateTime = {
      require(n >= 0)
      var tmp = d1
      var count = n
      while (count > 0) {
        tmp = tmp.minusDays(1)
        tmp = tmp.backward(condition)
        count = count - 1
      }
      tmp
    }

    def endOfMonth(): DateTime =
      d1.dayOfMonth.withMaximumValue()

    def isSameMonth(d2: DateTime): Boolean =
      d1.monthOfYear().get == d2.monthOfYear().get
  }

}
