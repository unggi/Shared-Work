package nomura.ted.datamodel

import java.util.Date
import nomura.ted.trade.tba.voicebroker.capture.{RejectBrokerTrade, ConfirmBrokerTrade}
import nomura.uml.LifeCycleEvents.Completed
import nomura.uml.StateMachine

trait PositionIdentifier {

}

trait Position {

}

trait TradeIdentfier {
  self: Obligation =>

}

trait Action {

}

class Direction(val tag: String)

object Direction {

  val BUY = new Direction("Buy")
  val SELL = new Direction("Sell")
}


/*

@startuml

!include ../../../../../../diagrams/Skin.iuml

hide members
hide stereotype

class Obligation

class Commitment

Obligation --->"1" Commitment: deliverer >
Obligation --->"1" Commitment: receiver >

interface FinancialInstrument
class Security -u-|> FinancialInstrument
class Cash -u-|> FinancialInstrument
class TBA -u-|> Security

class Holding ---> Quantity: quantity
class Holding ---> Denomination: units
class Holding -l-> FinancialInstrument: instrument

@enduml


 */


trait FinancialInstrument {

  def id: String

  def description: String
}

case class Currency(iso: String) extends FinancialInstrument {

  def id = iso

  def description = iso
}

object Currency {

  val USD = new Currency("USD")
  val EUR = new Currency("EUR")
  val GBP = new Currency("GBP")
  val AUD = new Currency("AUD")

}


trait Security extends FinancialInstrument {

}

object TBA {

  val deliveryMonthNames: List[String] =
    List("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

}

class TBA(val agency: String, val coupon: Double, val maturity: Int, val deliveryMonth: Int, val deliveryYear: Int) extends Security {

  def id: String = f"$agency $maturity%2d $coupon%.2f ${TBA.deliveryMonthNames(deliveryMonth)} $deliveryYear"

  def description: String = "TBA " + id

}

trait SizeDenomination {
  val scaling: Double
  val name: String
}

object SizeDenomination {
  val FaceAmount = new SizeDenomination {
    val scaling = 1.0
    val name = "Face"
  }
}

class Holding(val quantity: Double, val units: SizeDenomination, val instrument: FinancialInstrument)

case class CashHolding(override val quantity: Double, val currency: Currency)
  extends Holding(quantity, SizeDenomination.FaceAmount, currency)

case class Commitment(party: Counterparty, deliver: Holding, receive: Holding)

trait Obligation {

  val deliverer: Commitment
  val receiver: Commitment

  def product: Holding

  def tradeDate: Date

  def settleDate: Date
}

class BrokerTrade(trader: Trader, broker: Broker, originalFace: Double,
                  security: Security, currentFace: Double,
                  price: Double, val _tradeDate: Date,
                  val _settleDate: Date)
  extends StateMachine("Broker Trade") with Obligation {

  def product: Holding = new Holding(originalFace, SizeDenomination.FaceAmount, security)

  def money: CashHolding = new CashHolding(currentFace, Currency.USD)

  def settleDate: Date = _settleDate

  def tradeDate: Date = _tradeDate

  override val deliverer = new Commitment(trader, product, money)
  override val receiver = new Commitment(broker, money, product)

  model("Broker Trade") {
    state("Pending Front Office Confirmation") {
      event: Completed =>
    }

    state("Front Office Confirmed") {
      event: ConfirmBrokerTrade =>
    }

    state("Front Office Rejected") {
      event: RejectBrokerTrade =>

    }

    flow from start to "Pending Front Office Confirmation"
    transition from "Pending Front Office Confirmation" to "Front Office Confirmed"
    transition from "Pending Front Office Confirmation" to terminal

  }

}

trait Substitutable {

  val creatingAction: Action
  val substitutionActions: List[Action]

  val parentObligation: Obligation
  val substitutionObligations: List[Obligation]

}

trait Revisionable {

  self: Obligation =>

  val economicRevision: Int
  val nonEconomicRevision: Int

  val nextRevision: Revisionable
  val previousRevision: Revisionable

  override def toString(): String = s"$economicRevision:$nonEconomicRevision"
}


abstract class Counterparty()

class Client() extends Counterparty()

class Trader() extends Counterparty()

class Salesperson() extends Counterparty()

class Broker() extends Counterparty()

class Dealer() extends Counterparty()


