package techfn.cashflows

import org.joda.time.DateTime
import techfn.time.daycounters.DayCounter
import techfn.util.Types
import techfn.util.Types.{Real, Term}



abstract class Coupon(val paymentDate: DateTime, val nominal: Real,
                      val accrualStartDate: DateTime,
                      val accrualEndDate: DateTime,
                      val refPeriodStart: Option[DateTime] = None,
                      val refPeriodEnd: Option[DateTime] = None,
                      val exCouponDate: Option[DateTime] = None)
  extends Cashflow(paymentDate, nominal) {

  override def amount(settlementDate: DateTime): Real =
    if (isTradingExCoupon(settlementDate))
      0.0
    else
      nominal

  def dayCounter: DayCounter

  def rate: Types.Rate

  def isTradingExCoupon(date: DateTime): Boolean =
    exCouponDate match {
      case Some(ecd) =>
        !date.isBefore(ecd)
      case None =>
        false
    }

  override def getStepwiseDiscountTerm(aDayCounter: DayCounter, npvDate: DateTime, lastDate: DateTime): Term = {
    require(refPeriodStart.nonEmpty)
    require(refPeriodEnd.nonEmpty)
    if (!lastDate.isEqual(accrualStartDate))
      aDayCounter.yearFraction(accrualStartDate, paymentDate, refPeriodStart, refPeriodEnd)
    -aDayCounter.yearFraction(accrualStartDate, lastDate, refPeriodStart, refPeriodEnd)
  }
}
