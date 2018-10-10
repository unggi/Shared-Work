package techfn.cashflows

import org.joda.time.DateTime
import techfn.time.InterestRate
import techfn.time.daycounters.DayCounter
import techfn.util.Types
import techfn.util.Types.Real

class FixedRateCoupon(paymentDate: DateTime, nominal: Real, interestRate: InterestRate,
                      override val accrualStartDate: DateTime,
                      override val accrualEndDate: DateTime,
                      override val refPeriodStart: Option[DateTime] = None,
                      override val refPeriodEnd: Option[DateTime] = None,
                      override val exCouponDate: Option[DateTime] = None)
  extends Coupon(paymentDate, nominal, accrualStartDate, accrualEndDate, refPeriodStart, refPeriodEnd, exCouponDate) {

  override def rate: Types.Rate = interestRate.rate

  override def dayCounter: DayCounter = interestRate.dayCounter
}
