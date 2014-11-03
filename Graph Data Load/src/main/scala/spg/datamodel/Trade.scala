package spg.datamodel

import java.sql.Time
import java.util.Date

import spg.datamodel.Association.Cardinality


/**
 * A Trade Record as read from a CSV file.
 */
@GraphNode(label= "Trade")
class Trade {

  /**
   * TradeKey = TradeID + RevisionID - uniquely identifies the revision. In Sales application we are only
   * interested in the most current version of a trade. A newer version with a higher RevId will override
   * the older version.
   *
   * Therefore, TradeId is the unique ID for trades.
   */
  var TradeKey: Double = 0.0
  /**
   * Unique Trade Key - an integer that remains immutable through every revision of a trade.
   */
  @ID
  var TradeID: Int = 0
  /**
   * Revision ID
   */
  var RevId: Int = 0

  /**
   * Number of a trading book e.g. 07703602
   */
  @Association(toClass = classOf[TradingAccount])
  var Ledger: Int = 0

  /**
   * Human alias for a trading book.
   */
  @Association(toClass = classOf[Trade])
  var BookName: String = ""

  /**
   * Buy/Sell Indicator - TRUE indicates a BUY, FALSE is a SELL.
   */
  var BuySell: Boolean = false
  /**
   * Unique counterparty identifier e.g."CP8875" if this is a client trade
   * or blank if this is an internal trade.
   */
  @Association(toClass = classOf[Counterparty])
  var Counterparty: String = ""

  /**
   * Clean price of the trade up to 7 decimal places.
   */
  var TradePrice = 0.0

  /**
   * Size of the trade as original face in absolute dollar amounts.
   */
  var Quantity = 0.0
  /**
   * Security Master Reference ID. Could be a ticker (e.g. EDZ4), a Cusip or a Pool number or other
   * tickerized identifiers for synthetic "multi-leg" trades (e.g. Dollar Roll).
   */
  @Association(toClass = classOf[Security])
  var ESMID: String = ""

  /**
   * Security CUSIP
   */

  var Cusip: String = ""

  /**
   * Trade Date
   */
  var TradeDate: Date = _
  /**
   * Settle Date
   */
  var SettleDate: Date = _


  /**
   * Broad instrument type
   *
   * - G = Non-Agency or CMO
   * - P = Passthru Pool
   * - MBS = TBA
   * - T = Treasury
   * - F = Future
   * - Blank - no classification
   *
   */
  var InstrumentType: String = ""

  /**
   * Creation date and time stamp of this record.
   */
  @Optional
  var CreateDate: Time = _
  /**
   * Execution date and time stamp of this trade.
   */
  var ExecutionDate: Time = _
  /**
   * When the trade was originally booked.
   */
  var OrigCreateTime: Time = _
  /**
   * Comments as a string.
   */
  @Optional
  var Comments: String = ""
  /**
   * Sales Credit as a string. Contains ticks, points and text codes. Difficult to interpret.
   */
  @Optional
  var SalesCredit: String = ""
  /**
   * Link to Sales Person node. Sales Person is the RR number or blank (for non-customer trades).
   */
  @Association(toClass = classOf[SalesPerson], srcCount = Cardinality.ZERO_OR_MORE)
  var SalesPerson: String = ""
  /**
   * Text name of the sales person.
   */
  var SalesPersonName: String = ""
  /**
   * A code field - seems to be free form and not consistent.
   */
  var SalesQualityOfBusiness: String = ""
  /**
   * Current face
   */
  var CurrentFace: Double = 0.0
  /**
   * Free form - not interpreted.
   */
  @Optional
  var LevelSpot1: String = ""
  /**
   * Free form - not interpreted.
   */
  @Optional
  var LevelSpot2: String = ""
  /**
   * Free form - not interpreted.
   */
  @Optional
  var LevelSpot3: String = ""
  /**
   * Free form - not interpreted.
   */
  @Optional
  var LevelSpot4: String = ""
  /**
   * Free form - not interpreted.
   */
  @Optional
  var MarketContextNotes: String = ""
  /**
   * The login name of the user who captured the market context.
   */
  @Association(toClass = classOf[Trader])
  var MarketContextUpdateUser: String = ""

  /**
   * Name from Counterparty Node
   */
  @Association(toClass = classOf[Counterparty])
  var CounterpartyName: String = ""
  /**
   * Security Description from Security Node
   */
  var SecurityDescription: String = ""
  /**
   * Either "OPN" or "CAN" status.
   */
  var TradeStatus: String = ""

  /**
   * One of
   *
   * - BATCHTRADE - a bulk loaded trade,
   * - TWOSECURITY - a two security trade,
   * - THREESECURITY - a three security trade,
   * - OR "-" if not a customer trade.
   *
   */
  var DealType: String = ""
  /**
   * One of
   *
   * - AGENCYTRADE
   * - BATCHTRADE
   * - BUTTERFLY
   * - COUPON_SWAP
   * - DOLLAR_ROLL
   * - SWAP_SWITCH
   * - TSY_MTG_SWAP
   * - OR "-" if not a customer trade.
   *
   */
  var DealSubType: String = ""
  /**
   * Sales match status - always "-"
   */
  var MatchStatus: String = ""
  /**
   * Seems to be redundent - could be dropped.
   */
  var SecurityType: String = ""
  /**
   * Sub type codes:
   *
   * - ARM - ARM Pool
   * - CMO - CMO
   * - F - Futures
   * - IOS - IOS Index trade.
   * - P -
   * - PFD - CLO
   * - T - Treasury
   * - TBA - TBA
   */
  var InstrumentSubType: String = ""
  /**
   * Original user who booked the trade - user name is key.
   */
  @Association(toClass = classOf[Person])
  var OrigCreateUser: String = ""

  /**
   * Last user who modifeid the trade.
   */
  @Association(toClass = classOf[Person])
  var CreateUser: String = ""

  /**
   * User ID of the trader who executed the trade.
   */
  @Association(toClass = classOf[Trader])
  var Trader: String = ""

  /**
   * Source Platform identification.
   *
   * One of
   *
   * - MBS_BROKERTEC_TSY_PTF
   * - MBS_DEALERWEB_PTF
   * - TWEB_MBS
   * - MBS_CME_FUTURES_PTF
   * - Kozo Blotter
   * - BBG_TBA
   * - DEALERWEB_STP
   * - ICONNECT_STP
   * - MBS_BROKERTEC_MBS_PTF
   * - MBS_PTF
   *
   */
  var OrigSource: String = ""

  /**
   * Desk which booked the trade.
   *
   * One of
   *
   * - PT
   * - CMO
   * - RMBS
   * - ABS
   * - CMBS
   *
   */
  @Association(toClass=classOf[Desk])
  var BookDesk: String = ""

  /**
   * Desk Strategy - looks same as BookDesk
   */
  @Optional
  var BookStrategy: String = ""
}
