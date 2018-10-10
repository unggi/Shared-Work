package techfn.cashflows

import org.joda.time.{DateTime, Period}
import org.scalatest.FunSuite
import techfn.time.calendars.{FinancialCalendar, UnitedStates}
import techfn.time.daycounters.{BusinessDayConvention, DateGenerationRule}

class ScheduleTest extends FunSuite {

  def DATE(month: Int, day: Int, year: Int): DateTime =
    new DateTime(year, month, day, 0, 0)

  val sch = new Schedule(
    new Array[DateTime](1),
    new FinancialCalendar(new UnitedStates.SettlementCalendar()),
    new Period(0, 3, 0, 0, 0, 0, 0, 0),
    BusinessDayConvention.Unadjusted,
    BusinessDayConvention.Unadjusted,
    DateGenerationRule.Forward,
    true,
    new Array[Boolean](1)
  )

  test("testMultiply  - 2 years x 3") {
    val period = new Period(2, 0, 0, 0, 0, 0, 0, 0)
    val result = sch.multiply(3, period)
    assert(result.getYears === 6)

  }

  test("testMultiply - 6 months x 3") {
    val period = new Period(0, 6, 0, 0, 0, 0, 0, 0)
    val result = sch.multiply(3, period)
    assert(result.getMonths === 18)

  }

  test("testMultiply - 6 weeks x 3") {
    val period = new Period(0, 0, 6, 0, 0, 0, 0, 0)
    val result = sch.multiply(3, period)
    assert(result.getWeeks === 18)

  }

  test("testMultiply - 5 days x 3") {
    val period = new Period(0, 0, 0, 5, 0, 0, 0, 0)
    val result = sch.multiply(3, period)
    assert(result.getDays === 15)

  }

  test("testGenerate - forward - regular periods") {
    sch.generate(DATE(6, 1, 2014), DATE(6, 1, 2018), DATE(9, 1, 2014), DATE(3, 1, 2018))

    assert(sch.dates.length === 17)
    assert(sch.isRegular.length === 16)

    assert(sch.dates(0) === DATE(6, 1, 2014))
    assert(sch.isRegular(0) === true)

    assert(sch.dates(1) === DATE(9, 1, 2014))
    assert(sch.isRegular(1) === true)

    assert(sch.dates(2) === DATE(12, 1, 2014))
    assert(sch.isRegular(2) === true)

    assert(sch.dates(8) === DATE(6, 1, 2016))
    assert(sch.isRegular(7) === true)

    assert(sch.dates(15) === DATE(3, 1, 2018))
    assert(sch.dates(16) === DATE(6, 1, 2018))

  }

  test("testGenerate - forward - short first and last") {
    sch.generate(DATE(7, 1, 2014), DATE(6, 1, 2018), DATE(9, 1, 2014), DATE(3, 1, 2018))

    assert(sch.dates.length === 17)
    assert(sch.isRegular.length === 16)

    assert(sch.dates(0) === DATE(7, 1, 2014))
    assert(sch.isRegular(0) === false)

    assert(sch.dates(1) === DATE(9, 1, 2014))
    assert(sch.isRegular(1) === true)

    assert(sch.dates(2) === DATE(12, 1, 2014))
    assert(sch.isRegular(2) === true)

    assert(sch.dates(8) === DATE(6, 1, 2016))
    assert(sch.isRegular(7) === true)

    assert(sch.dates(15) === DATE(3, 1, 2018))
    assert(sch.dates(16) === DATE(6, 1, 2018))

  }

  test("testGenerate - forward - long first") {
    sch.generate(DATE(4, 1, 2014), DATE(6, 1, 2018), DATE(9, 1, 2014), DATE(3, 1, 2018))

    assert(sch.dates.length === 17)
    assert(sch.isRegular.length === 16)

    assert(sch.dates(0) === DATE(4, 1, 2014))
    assert(sch.isRegular(0) === false)

    assert(sch.dates(1) === DATE(9, 1, 2014))
    assert(sch.isRegular(1) === true)

    assert(sch.dates(2) === DATE(12, 1, 2014))
    assert(sch.isRegular(2) === true)

    assert(sch.dates(8) === DATE(6, 1, 2016))
    assert(sch.isRegular(7) === true)

    assert(sch.dates(15) === DATE(3, 1, 2018))
    assert(sch.dates(16) === DATE(6, 1, 2018))

  }

  test("testGenerate - forward - long first and last") {
    sch.generate(DATE(4, 1, 2014), DATE(8, 1, 2018), DATE(9, 1, 2014), DATE(3, 1, 2018))

    assert(sch.dates.length === 17)
    assert(sch.isRegular.length === 16)

    assert(sch.dates(0) === DATE(4, 1, 2014))
    assert(sch.isRegular(0) === false)

    assert(sch.dates(1) === DATE(9, 1, 2014))
    assert(sch.isRegular(1) === true)

    assert(sch.dates(2) === DATE(12, 1, 2014))
    assert(sch.isRegular(2) === true)

    assert(sch.dates(8) === DATE(6, 1, 2016))
    assert(sch.isRegular(7) === true)

    assert(sch.dates(15) === DATE(3, 1, 2018))
    assert(sch.dates(16) === DATE(8, 1, 2018))

    assert(sch.isRegular(14) === true)
    assert(sch.isRegular(15) === false)

  }

  test("testGenerate - forward - one period series") {
    sch.generate(DATE(6, 1, 2014), DATE(9, 1, 2014), DATE(9, 1, 2014), DATE(9, 1, 2014))

    assert(sch.dates.length === 2)
    assert(sch.isRegular.length === 1)

    assert(sch.dates(0) === DATE(6, 1, 2014))
    assert(sch.isRegular(0) === true)

    assert(sch.dates(1) === DATE(9, 1, 2014))
  }


  test("testGenerate - forward - regular 6 month cycle") {

    sch.tenor = new Period(0,6,0,0,0,0,0,0)
    sch.generate(DATE(6, 1, 2014), DATE(6, 1, 2018), DATE(12, 1, 2014), DATE(12, 1, 2017))

    assert(sch.dates.length === 9)
    assert(sch.isRegular.length === 8)

    assert(sch.dates(0) === DATE(6, 1, 2014))
    assert(sch.isRegular(0) === true)

    assert(sch.dates(1) === DATE(12, 1, 2014))
    assert(sch.dates(7) === DATE(12, 1, 2017))
    assert(sch.dates(8) === DATE(6, 1, 2018))
  }

}
