package techfn.time.daycounters

import org.joda.time.DateTime
import techfn.util.Types.Term

trait DayCounter {

  def name: String

  def dayCount(from: DateTime, to: DateTime): Term

  def yearFraction(from: DateTime, to: DateTime, refStart: Option[DateTime], refEnd: Option[DateTime]): Term

}
