package techfn.cashflows

import org.joda.time._
import techfn.time.daycounters.DayCounter
import techfn.time.{Compounding, Frequency, InterestRate}
import techfn.util.Types.{DiscountFactor, Rate, Real}


object CashflowAnalytics {

  def npv(cashflows: CashflowSeries,
          interestRate: InterestRate,
          includeSettlementDateFlows: Boolean,
          settlementDate: DateTime,
          npvDate: DateTime
         ): Unit = {

    val npv: Real = 0.0
    if (cashflows.nonEmpty) {
      val discountFactor: DiscountFactor = 1.0
      val lastDate = npvDate
      val dayCounter = interestRate.dayCounter
      cashflows
        .filter(_.hasOccurred(settlementDate))
        .foreach {
          cf =>
            val amount = cf.amount(settlementDate)
            val factor = interestRate.discountFactor(cf.getStepwiseDiscountTerm(dayCounter, npvDate, lastDate))
        }
    }

  }

  def npv(cashflows: CashflowSeries,
          yld: Rate,
          dayCounter: DayCounter,
          compounding: Compounding.Value,
          frequency: Frequency.Value,
          includeSettlementDateFlows: Boolean,
          settlementDate: DateTime,
          npvDate: DateTime): Unit = {
  }


}




