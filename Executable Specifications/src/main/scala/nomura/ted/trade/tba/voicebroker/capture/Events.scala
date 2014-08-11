package nomura.ted.trade.tba.voicebroker.capture

case class NewBrokerTrade(brokerTradeID: String,
                          cusip: String,
                          poolNumber: String,
                          issuer: String,
                          broker: String,
                          trader: String)

case class AllegedTrade(brokerTradeID: String)

case class ConfirmBrokerTrade(brokerTradeID: String)

case class RejectBrokerTrade(brokerTradeID: String)

case class RTTMTradeMatchReport(brokerTradeID: String)

case class TradeExecutionReport(brokerTradeID: String)

case class IncomingBrokerTrade(brokerTradeID: String)

case class IncomingDealerTrade(tradeID: String)

case class TradeMatchFound(tradeID: String)

case class NoTradeMatch()

