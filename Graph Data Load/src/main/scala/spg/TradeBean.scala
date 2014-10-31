package spg

import java.sql.Time
import java.util.Date

/**
 * A Trade Record as read from a CSV file.
 */
class TradeBean {
  var TradeKey: Double = 0.0
  var TradeId: Int = 0
  var RevId: Int = 0
  var Ledger: String = ""
  var BookName: String = ""
  var BuySell: String = ""
  var Counterparty = ""
  var TradePrice = 0.0
  var Quantity = 0.0
  var SecurityId: String = ""
  var Cusip: String = ""
  var TradeDate: Date = _
  var SettleDate: Date = _
  var InstrumentType: String = ""
  var CreateDate: Date = _
  var ExecutionDate: Date = _
  var OrigCreateTime: Time = _
  var Comments: String = ""
  var SalesCredit: String = ""
  var SalesPerson: String = ""
  var SalesPersonName: String = ""
  var SalesQualityOfBusiness: String = ""
  var CurrentFace: Double = 0.0
  var LevelSpot1: String = ""
  var LevelSpot2: String = ""
  var LevelSpot3: String = ""
  var LevelSpot4: String = ""
  var MarketContextNotes: String = ""
  var MarketContextUpdateUser: String = ""
  var CounterpartyName: String = ""
  var SecurityDescription: String = ""
  var TradeStatus: String = ""
  var DealType: String = ""
  var DealSubType: String = ""
  var MatchStatus: String = ""
  var SecurityType: String = ""
  var InstrumentSubType: String = ""
  var OrigCreateUser: String = ""
  var CreateUser: String = ""
  var Trader: String = ""
  var OrigSource: String = ""
  var BookDesk: String = ""
  var BookStrategy: String = ""
}
