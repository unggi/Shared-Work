package techfn.time.daycounters

import org.joda.time.{DateTime, Interval}
import techfn.util.Types.Term


object ActualActual {

  object Variant extends Enumeration {

    val ISMA: Value = Value(0)
    val Bond: Value = Value(1)
    val ISDA: Value = Value(2)
    val Historical: Value = Value(3)
    val Actual365: Value = Value(4)
    val AFB: Value = Value(5)
    val Euro: Value = Value(6)
  }

}

class ActualActual(val variant: ActualActual.Variant.Value) extends DayCounter {

  import ActualActual.Variant._
  import techfn.util.DateMath._

  override def name: String = "ActualActual/" + variant

  override def dayCount(from: DateTime, to: DateTime): Term = {
    0.0
  }

  override def yearFraction(from: DateTime, to: DateTime,
                            refStart: Option[DateTime] = None,
                            refEnd: Option[DateTime] = None): Term =
    variant match {
      case ISMA | Bond =>
        require(refStart.nonEmpty, s"Empty reference start date for $name")
        require(refEnd.nonEmpty, s"Empty reference end date for $name")
        yearFractionISMA(from, to, refStart.get, refEnd.get)
      case ISDA | Historical | Actual365 =>
        yearFractionISDA(from, to)
      case AFB | Euro =>
        yearFractionASB(from, to)
      case other =>
        assert(false, s"Invalid ActualActual Variant type - $other")
        0.0
    }

  def yearFractionISDA(from: DateTime, to: DateTime): Term = {
    require(from.isBefore(to), s"From Date $from must be before To Date $to")
    val y1 = from.year().get
    val y2 = to.year().get
    val dib1 = if (from.year.isLeap) 366.0 else 365.0
    val dib2 = if (to.year.isLeap) 366.0 else 365.0
    y2 - y1 - 1 +
      daysBetween(from, new DateTime(y1 + 1, 1, 1, 0, 0)) / dib1 +
      daysBetween(new DateTime(y2, 1, 1, 0, 0), to) / dib1
  }

  def yearFractionASB(from: DateTime, to: DateTime): Term = {
    0.0
  }


  def findNotionalDateBracket(date: DateTime, notionalDate: DateTime, isLastPeriod: Boolean): Interval = {

    var d = notionalDate
    val bracket =
      if (notionalDate <= date) {
        while (d <= date)
          d = d.addYears(1)
        new Interval(d.minusYears(1), d)
      }
      else {
        while (d > date)
          d = d.minusYears(1)
        new Interval(d, d.plusYears(1))

      }
    if (isLastPeriod && bracket.getStart === date)
      new Interval(date.minusYears(1), date)
    else
      bracket

  }

  def countWholeYears(notionalStart: DateTime, notionalEnd: DateTime): Int = {
    require(notionalStart <= notionalEnd, s"Start = $notionalStart and End = $notionalEnd")
    var count = 0
    var d = notionalStart
    while (d < notionalEnd) {
      d = d.plusYears(1)
      count = count + 1
    }
    count

  }

  def yearFractionISMA(from: DateTime, to: DateTime, firstCoupon: DateTime, lastCoupon: DateTime): Term = {
    require(firstCoupon <= lastCoupon)
    require(from <= to)

    val bracketFrom = findNotionalDateBracket(from, firstCoupon, false)
    require(bracketFrom.getStart <= from && from < bracketFrom.getEnd)

    val bracketTo = findNotionalDateBracket(to, lastCoupon, true)
    require(bracketTo.getStart <= to && to <= bracketTo.getEnd, s"To date must be within it's bracket - to = $to bracket = $bracketTo")

    if (bracketFrom.isEqual(bracketTo))
      daysBetween(from, to).toDouble / daysBetween(bracketFrom.getStart, bracketTo.getEnd).toDouble
    else {
      val stubFrom = daysBetween(from, bracketFrom.getEnd).toDouble / daysBetween(bracketFrom.getStart, bracketFrom.getEnd).toDouble

      val stubTo = daysBetween(bracketTo.getStart, to).toDouble / daysBetween(bracketTo.getStart, bracketTo.getEnd).toDouble

      val wholePeriods = countWholeYears(bracketFrom.getEnd, bracketTo.getStart)

      stubFrom + wholePeriods + stubTo
    }
  }

  /**
    *
    * See ISMA Documentation for Rule 251 to explain the methodology.
    *
    * @param from     Beginning date
    * @param to       End date
    * @param refStart A notional payment date
    * @param refEnd
    * @return
    */

  /*
  def yearFractionISMA_Original(from: DateTime, to: DateTime, refStart: DateTime, refEnd: DateTime): Term = {
    var refPeriodStart = refStart
    var refPeriodEnd = refEnd
    var months: Int = (0.5 + 12.0 * daysBetween(refStart, refEnd) / 365.0).toInt
    if (months == 0) {
      refPeriodStart = from
      refPeriodEnd = from.addYears(1)
      months = 12
    }
    val period = months / 12.0
    if (to <= refPeriodEnd)
      if (from >= refPeriodStart)
        period * daysBetween(from, to) / daysBetween(refPeriodStart, refPeriodEnd)
      else {
        val previousRef =
          if (schedule.isEmpty)
            refPeriodStart.minusMonths(months)
          else
            schedule.calendar.advance(refPeriodStart, schedule.tenor, schedule.businessDayConvention, schedule.endOfMonth.get)
        if (to > previousRef)
          yearFraction(from, refPeriodStart, Some(previousRef), Some(refPeriodStart)) +
            yearFraction(refPeriodStart, to, Some(refPeriodStart), Some(refPeriodEnd))
        else
          yearFraction(from, to, Some(previousRef), Some(refPeriodStart))
      }
    else {
      require(refPeriodStart <= from, "invalid dates: d1 < refPeriodEnd < refPeriodEnd < d2")
      var sum = yearFraction(from, refPeriodEnd, Some(refPeriodStart), Some(refPeriodEnd))
      var newRefStart = refPeriodStart
      var newRefEnd = refPeriodEnd
      var periodCount = 0
      do {
        newRefStart = refPeriodEnd.plusMonths(months * periodCount)
        newRefEnd = refPeriodEnd.plusMonths(months * (periodCount + 1))
        periodCount = periodCount + 1
        sum = sum + period
      } while (to < newRefEnd)

    }


    0.0
  } */

}
