package spg

import java.sql.Time
import java.util.Date

@GraphNode(name="AgencyPool")
class PoolBean {

  @ID
  var ESMID: Int = -1
  /**
   * Cusip
   */
  var CUSIP: String = ""
  var SecurityType: String = ""
  var Description: String = ""
  var IsRule144A: String = ""
  var IssueDate: Date = _
  var Series: String = ""
  var IssuerName: String = ""
  var CollateralType: String = ""
  var TrancheClass: String = ""
  var MortgageType: String = ""
  var CurrentFactor: Double = 0.0
  var CurrentFactorEffectiveDate: Date = _
  var dataSource: String = ""
  var dataTimestamp: Time = _
  var SEC_ID: String = ""
  var MktSector: String = ""
  var Agency: String = ""
  var PoolNumber: String = ""

}
