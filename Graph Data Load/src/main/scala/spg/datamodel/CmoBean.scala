package spg.datamodel

import java.sql.Time
import java.util.Date

import spg.{GraphNode, ID}


@GraphNode(name="Security")
class CmoBean {
  @ID
  var ESMID: Int = -1

  /**
   * CUSIP - may not be populated.
   */
  var CUSIP: String = ""
  /**
   * Security/Collateral Type
   *
   *
   */
  var SecurityType: String = ""
  /**
   * Announcement Date
   */
  var AnnouncementDate: Date = _
  /**
   *  Security Description
   */
  var Description: String = ""
  /**
   * Rule 144A?
   */
  var IsRule144A: String = ""
  /**
   * Issue Date
   */
  var IssueDate: Date = _
  /**
   *
   */
  var ParValue: Double = 0.0
  var Series: String = ""
  var IssuerName: String = ""
  /**
   * *
   */
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
  var IsRegS: String = ""
  var BMT: String = ""
  var ISIN: String = ""
  var dataSource: String = ""
  var dataTimestamp: Time = _
  var SEC_ID: String = ""
  var MktSector: String = ""
}
