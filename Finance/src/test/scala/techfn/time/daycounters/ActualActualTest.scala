package techfn.time.daycounters

import org.joda.time.DateTime
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class ActualActualTest extends FunSuite with BeforeAndAfterEach {


  import ActualActual.Variant._

  val dc = new ActualActual(ISMA)

  def DATE(year: Int, month: Int, day: Int): DateTime = new DateTime(year, month, day, 0, 0, 0)

  test("findNotionalDateBracket - Case 1 - notional before target") {
    val notional = DATE(2018, 1, 15)
    val target = DATE(2018, 6, 25)

    val interval = dc.findNotionalDateBracket(target, notional, false)

    assert(interval.getStart.isEqual(notional), s"Interval is $interval")
    assert(interval.getEnd.isEqual(DATE(2019, 1, 15)))
  }

  test("findNotionalDateBracket - Case 2 - notional after target") {
    val notional = DATE(2019, 1, 15)
    val target = DATE(2018, 6, 25)

    val interval = dc.findNotionalDateBracket(target, notional, false)

    assert(interval.getStart.isEqual(DATE(2018, 1, 15)), s"Interval is $interval")
    assert(interval.getEnd.isEqual(notional))
  }

  test("findNotionalDateBracket - Case 3a - notional == target - first period") {
    val notional = DATE(2019, 1, 15)
    val target = notional

    val interval = dc.findNotionalDateBracket(target, notional, false)

    assert(interval.getStart.isEqual(notional), s"Interval is $interval")
    assert(interval.getEnd.isEqual(notional.plusYears(1)))
  }

  test("findNotionalDateBracket - Case 3b - notional == target - last period") {
    val notional = DATE(2019, 1, 15)
    val target = notional

    val interval = dc.findNotionalDateBracket(target, notional, true)

    assert(interval.getStart.isEqual(notional.minusYears(1)), s"Interval is $interval")
    assert(interval.getEnd.isEqual(notional))
  }

  test("findNotionalDateBracket - Case 4 - notional more than one period behind") {
    val notional = DATE(2014, 1, 15)
    val target = DATE(2018, 6, 25)

    val interval = dc.findNotionalDateBracket(target, notional, false)

    assert(interval.getStart.isEqual(DATE(2018, 1, 15)), s"Interval is $interval")
    assert(interval.getEnd.isEqual(DATE(2019, 1, 15)))
  }

  test("findNotionalDateBracket - Case 5 - notional more than one period ahead") {
    val notional = DATE(2020, 1, 15)
    val target = DATE(2018, 6, 25)

    val interval = dc.findNotionalDateBracket(target, notional, false)

    assert(interval.getStart.isEqual(DATE(2018, 1, 15)), s"Interval is $interval")
    assert(interval.getEnd.isEqual(DATE(2019, 1, 15)))
  }

  test("testCountWholeYears - Case 1 - 4 Years") {
    val count = dc.countWholeYears(DATE(2014, 1, 15), DATE(2018, 1, 15))
    assert(count == 4, s"Count is $count")
  }

  test("testCountWholeYears - Case 2 - 0 Years") {
    val count = dc.countWholeYears(DATE(2014, 1, 15), DATE(2014, 1, 15))
    assert(count == 0, s"Count is $count")
  }

  test("testCountWholeYears - Case 3 - Bad Interval") {
    val count =
      intercept[IllegalArgumentException] {
        dc.countWholeYears(DATE(2018, 1, 15), DATE(2014, 1, 15))
      }
  }

  test("testYearFraction - Case 1 - short first period") {
    val y = dc.yearFraction(DATE(1999, 2, 1), DATE(2000, 7, 1), Some(DATE(1998, 7, 1)), Some(DATE(2018, 7, 1)))
    assert(y == 1.410958904109589)
  }

  test("testYearFraction - Case 2 - long first period") {
    val y = dc.yearFraction(DATE(2002, 8, 15), DATE(2003, 7, 15), Some(DATE(2003, 1,15)), Some(DATE(2003, 1,15)))
    assert(y == 0.915068493150685)
  }

  test("testYearFraction - Case 3 - regular first period") {
    val y = dc.yearFraction(DATE(2002, 8, 15), DATE(2003, 7, 15), Some(DATE(2003, 8,15)), Some(DATE(2003, 8,15)))
    assert(y == 0.915068493150685)
  }

  test("testYearFraction - Case 4 - intra-period - non-leap year") {
    val y = dc.yearFraction(DATE(2015, 7, 15), DATE(2015, 8, 15), Some(DATE(2003, 8,15)), Some(DATE(2003, 8,15)))
    assert(y == 0.08493150684931507)
  }

  test("testYearFraction - Case 5 - intra-period - leap year") {
    val y = dc.yearFraction(DATE(2016, 7, 15), DATE(2016, 8, 15), Some(DATE(2003, 8,15)), Some(DATE(2003, 8,15)))
    assert(y == 0.08469945355191257)
  }

}
