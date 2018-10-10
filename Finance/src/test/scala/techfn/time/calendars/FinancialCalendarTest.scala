package techfn.time.calendars

import org.joda.time.{DateTime, Period}
import org.scalatest.FunSuite

class FinancialCalendarTest extends FunSuite {

  val hc = new UnitedStates.SettlementCalendar()
  val fc = new FinancialCalendar(hc)

  def DATE(month: Int, day: Int, year: Int) = new DateTime(year, month, day, 0, 0, 0)

  test("testIsHoliday") {
    assert(fc.isHoliday(DATE(10, 8, 2018)))
    assert(fc.isHoliday(DATE(12, 25, 2018)))
    assert(fc.isHoliday(DATE(10, 7, 2018)))
    assert(!fc.isHoliday(DATE(12, 31, 2018)))

  }

  test("testIsBusinessDay") {
    assert(fc.isBusinessDay(DATE(10, 9, 2018)))
    assert(!fc.isBusinessDay(DATE(10, 8, 2018)))
    assert(!fc.isBusinessDay(DATE(2, 19, 2018)))

  }

  import techfn.time.TimeUnit._
  import techfn.time.daycounters.BusinessDayConvention._

  test("testAdjust") {
    assert(fc.adjust(DATE(10, 9, 2018), Following) === DATE(10, 9, 2018))
    assert(fc.adjust(DATE(10, 8, 2018), Following) === DATE(10, 9, 2018))

    assert(fc.adjust(DATE(5, 31, 2021), ModifiedFollowing) === DATE(5, 28, 2021))
    assert(fc.adjust(DATE(5, 28, 2021), ModifiedFollowing) === DATE(5, 28, 2021))

    assert(fc.adjust(DATE(2, 15, 2021), HalfMonthlyModifiedFollowing) === DATE(2, 12, 2021))
    assert(fc.adjust(DATE(5, 31, 2021), HalfMonthlyModifiedFollowing) === DATE(5, 28, 2021))

    assert(fc.adjust(DATE(2, 15, 2021), Preceding) === DATE(2, 12, 2021))
    assert(fc.adjust(DATE(5, 31, 2021), Preceding) === DATE(5, 28, 2021))
    assert(fc.adjust(DATE(6, 1, 2021), Preceding) === DATE(6, 1, 2021))

    assert(fc.adjust(DATE(1, 1, 2021), ModifiedPreceding) === DATE(1, 4, 2021))
    assert(fc.adjust(DATE(8, 1, 2021), ModifiedPreceding) === DATE(8, 2, 2021))
    assert(fc.adjust(DATE(6, 1, 2021), ModifiedPreceding) === DATE(6, 1, 2021))

    assert(fc.adjust(DATE(1, 1, 2021), Nearest) === DATE(12, 31, 2020))
    assert(fc.adjust(DATE(8, 1, 2021), Nearest) === DATE(8, 2, 2021))
    assert(fc.adjust(DATE(6, 1, 2021), Nearest) === DATE(6, 1, 2021))

    assert(fc.adjust(DATE(1, 1, 2021), Unadjusted) === DATE(1, 1, 2021))
    assert(fc.adjust(DATE(8, 1, 2021), Unadjusted) === DATE(8, 1, 2021))

  }

  def PERIOD(days: Int, weeks: Int, months: Int): Period = new Period(0, months, weeks, days, 0, 0, 0, 0)

  test("testAdvance - Count and Time Units") {
    assert(fc.advance(DATE(10, 9, 2018), 6, Months, Following, false) === DATE(4, 9, 2019))
    assert(fc.advance(DATE(10, 9, 2018), 6, Days, Following, false) === DATE(10, 17, 2018))
    assert(fc.advance(DATE(10, 9, 2018), 6, Weeks, Following, false) === DATE(11, 20, 2018))
    assert(fc.advance(DATE(1, 1, 2018), 2, Weeks, Following, false) === DATE(1, 16, 2018))

    assert(fc.advance(DATE(2, 28, 2018), 3, Months, Following, true) === DATE(5, 31, 2018))
    assert(fc.advance(DATE(2, 28, 2018), 3, Months, Following, false) === DATE(5, 29, 2018))

    assert(fc.advance(DATE(2, 28, 2018), -3, Months, Following, true) === DATE(11, 30, 2017))
    assert(fc.advance(DATE(2, 28, 2018), -3, Months, Following, false) === DATE(11, 28, 2017))

  }

  test("testAdvance - Periods") {
    assert(fc.advance(DATE(10, 9, 2018), PERIOD(0, 0, 6), Following, false) === DATE(4, 9, 2019))
    assert(fc.advance(DATE(10, 9, 2018), PERIOD(6, 0, 0), Following, false) === DATE(10, 17, 2018))
    assert(fc.advance(DATE(10, 9, 2018), PERIOD(0, 6, 0), Following, false) === DATE(11, 20, 2018))
    assert(fc.advance(DATE(1, 1, 2018), PERIOD(0, 2, 0), Following, false) === DATE(1, 16, 2018))

    assert(fc.advance(DATE(2, 28, 2018), PERIOD(0, 0, 3), Following, true) === DATE(5, 31, 2018))
    assert(fc.advance(DATE(2, 28, 2018), PERIOD(0, 0, 3), Following, false) === DATE(5, 29, 2018))

  }

  test("testIsEndOfMonth") {
    assert(fc.isEndOfMonth(DATE(2, 28, 2016)) === false)
    assert(fc.isEndOfMonth(DATE(2, 29, 2016)) === true)
  }

  test("testBackward") {
    assert(fc.backward(DATE(10, 9, 2018), PERIOD(0, 0, 6), Following, false) === DATE(4, 9, 2018))
    assert(fc.backward(DATE(10, 9, 2018), PERIOD(6, 0, 0), Following, false) === DATE(9, 28, 2018))
    assert(fc.backward(DATE(10, 9, 2018), PERIOD(0, 6, 0), Following, false) === DATE(8, 28, 2018))

    assert(fc.backward(DATE(2, 28, 2018), PERIOD(0, 0, 3), Following, true) === DATE(11, 30, 2017))
    assert(fc.backward(DATE(2, 28, 2018), PERIOD(0, 0, 3), Following, false) === DATE(11, 28, 2017))

  }

}
