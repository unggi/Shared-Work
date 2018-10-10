package techfn.time.calendars

import org.joda.time.DateTime

trait HolidayCalendar {
  def isWeekend(weekday: Int): Boolean

  def isBusinessDay(date: DateTime): Boolean

  def isHoliday(date: DateTime): Boolean = !isBusinessDay(date)
}
