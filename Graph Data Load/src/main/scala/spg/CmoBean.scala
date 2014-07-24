package spg

import java.sql.Time
import java.util.Date

class CmoBean {
  var ESMP: String = ""
  var CSP: String = ""
  var SecurityType: String = ""
  var AnnouncementDate: Date = _
  var Description: String = ""
  var IsRule144A: String = ""
  var IssueDate: Date = _
  var ParValue: Double = 0.0
  var Series: String = ""
  var IssuerName: String = ""
  var CollateralType: String = ""
  var FirstSettlementDate: Date = _
  var CouponDividendFrequency: Int = 0
  var CouponDividendRate: Double = 0
  var DatedDate: Date = _
  var LastPayDate: Date = _
  var CurrentMaturityDate: Date = _
  var PrincipalAmountOutstanding: Double = 0.0
  var FirstCouponDate: Date = _
  var TrancheClass: String = ""
  var IOPOFlag: String = ""
  var MortgageType: String = ""
  var DelayDays: Int = 0
  var OriginalAmountOfPrincipal: Double = 0.0
  var CurrentFactor: Double = 0.0
  var CurrentFactorEffectiveDate: Date = _
  var IsMtgePaidOff: String = ""
  var MtgeCMOGroup: String = ""
  var IsRegS: Int = 0
  var BMT: String = ""
  var ISIN: String = ""
  var dataSource: String = ""
  var dataTimestamp: Time = _
  var SEC_ID: String = ""
  var MktSector: String = ""
}
