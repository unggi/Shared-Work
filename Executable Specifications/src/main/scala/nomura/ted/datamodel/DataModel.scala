package nomura.ted.datamodel

import java.util.Date
import javax.security.auth.login.AccountException

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

class Obligation

class Commitment {
  who
  what
}

Obligation --->"1" Commitment: seller >
Obligation --->"1" Commitment: buyer >

@enduml


 */

case class Commitment(party: Counterparty, deliver: FinancialAssetHolding, receive: FinancialAssetHolding)

trait Obligation {

  val deliverer: Commitment
  val receiver: Commitment

  def product: FinancialAsset

  def tradeDate: Date

  def settleDate: Date
}

class BrokerTrade(trader: Trader, broker: Broker, product: Security, price: MoneyValue)
  extends Obligation {


  def product = new TBA()
  deliverer = new Commitment(trader, product, new CashHolding())


}


trait Substitutable {

  val parentEvent: Action
  val substitutions: List[Obligation]

}

trait Revisionable {

  val economicRevision: Int
  val nonEconomicRevision: Int

  val nextRevision: Revisionable
  val previousRevision: Revisionable

  override def toString(): String = s"$economicRevision:$nonEconomicRevision"
}


class FinancialAssetHolding(quantity: Double, asset: Asset)


abstract class Asset() {
  def describe: String
}

case class Currency(iso: String) extends Asset {
  def describe: String = iso
}


object Currency {

  val USD = new Currency("USD")
  val EUR = new Currency("EUR")
  val GBP = new Currency("GBP")
  val AUD = new Currency("AUD")

}

class MoneyValue()


class Account {

}

abstract class FinancialAsset

class CashHolding(quantity: Double, currency: Currency) extends FinancialAsset {

}

class SecurityHolding extends FinancialAsset {

}

abstract class Security()

class TBA() extends Security

abstract class Counterparty()

class Client() extends Counterparty()

class Trader() extends Counterparty()

class Salesperson() extends Counterparty()

class Broker() extends Counterparty()

class Dealer() extends Counterparty()


