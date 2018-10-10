package techfn.time.calendars

import org.joda.time.{DateTime, DateTimeConstants}

object UnitedStates {

  import org.joda.time.DateTimeConstants._

  def isWashingtonBirthday(d: Int, m: Int, y: Int, w: Int): Boolean =
    if (y >= 1971) { // third Monday in February
      (d >= 15 && d <= 21) && (w == MONDAY) && (m == FEBRUARY)
    }
    else { // February 22nd, possily adjusted
      ((d == 22) || ((d == 23) && (w == MONDAY)) || ((d == 21) && (w == FRIDAY))) && (m == FEBRUARY)
    }

  def isMemorialDay(d: Int, m: Int, y: Int, w: Int): Boolean = if (y >= 1971) { // last Monday in May
    d >= 25 && (w == MONDAY) && (m == MAY)
  }
  else { // May 30th, possibly adjusted
    ((d == 30) || ((d == 31) && (w == MONDAY)) || ((d == 29) && (w == FRIDAY))) && (m == MAY)
  }

  def isLaborDay(d: Int, m: Int, y: Int, w: Int): Boolean = { // first Monday in September
    d <= 7 && (w == MONDAY) && (m == SEPTEMBER)
  }

  def isColumbusDay(d: Int, m: Int, y: Int, w: Int): Boolean = { // second Monday in October
    (d >= 8 && d <= 14) && (w == MONDAY) && (m == OCTOBER) && y >= 1971
  }

  def isVeteransDay(d: Int, m: Int, y: Int, w: Int): Boolean =
    if (y <= 1970 || y >= 1978) { // November 11th, adjusted
      ((d == 11) || ((d == 12) && (w == MONDAY)) || ((d == 10) && (w == FRIDAY))) && (m == NOVEMBER)
    }
    else { // fourth Monday in October
      (d >= 22 && d <= 28) && (w == MONDAY) && (m == OCTOBER)
    }

  def isVeteransDayNoSaturday(d: Int, m: Int, y: Int, w: Int): Boolean =
    if (y <= 1970 || y >= 1978) { // November 11th, adjusted, but no Saturday to Friday
      ((d == 11) || ((d == 12) && (w == MONDAY))) && (m == NOVEMBER)
    }
    else (d >= 22 && d <= 28) && (w == MONDAY) && (m == OCTOBER)

  class SettlementCalendar extends WesternHolidayCalendar {

    import DateTimeConstants._

    override def isBusinessDay(date: DateTime): Boolean = {
      val w = date.dayOfWeek.get
      val d = date.dayOfMonth.get
      val m = date.monthOfYear.get
      val y = date.year().get


      !(isWeekend(w)
        // New Year's Day (possibly moved to Monday if on Sunday)
        || ((d == 1 || (d == 2 && w == MONDAY)) && m == JANUARY)
        // (or to Friday if on Saturday)
        || (d == 31 && w == FRIDAY && m == DECEMBER)
        // Martin Luther King's birthday (third Monday in January)
        || ((d >= 15 && d <= 21) && w == MONDAY && m == JANUARY
        && y >= 1983)
        // Washington's birthday (third Monday in February)
        || isWashingtonBirthday(d, m, y, w)
        // Memorial Day (last Monday in May)
        || isMemorialDay(d, m, y, w)
        // Independence Day (Monday if Sunday or Friday if Saturday)
        || ((d == 4 || (d == 5 && w == MONDAY) ||
        (d == 3 && w == FRIDAY)) && m == JULY)
        // Labor Day (first Monday in September)
        || isLaborDay(d, m, y, w)
        // Columbus Day (second Monday in October)
        || isColumbusDay(d, m, y, w)
        // Veteran's Day (Monday if Sunday or Friday if Saturday)
        || isVeteransDay(d, m, y, w)
        // Thanksgiving Day (fourth Thursday in November)
        || ((d >= 22 && d <= 28) && w == THURSDAY && m == NOVEMBER)
        // Christmas (Monday if Sunday or Friday if Saturday)
        || ((d == 25 || (d == 26 && w == MONDAY) ||
        (d == 24 && w == FRIDAY)) && m == DECEMBER))


    }
  }

}
