package nomura.ted.datamodel

import javax.security.auth.login.AccountException

trait PositionIdentifier {

}

trait Position {

}

trait TradeIdentfier {
  self : Obligation =>

}

trait Event {

}

class Direction(val tag: String)

object Direction {

  val BUY = new Direction("Buy")
  val SELL = new Direction("Sell")
}

trait Obligation {

  def party1: Counterparty
  def party2: Counterparty

  def party1Holding: FinancialAssetHolding
  def party2Holding: FinancialAssetHolding

  def direction: Direction

  def creatingEvent: Event
  def substitutingEvents: List[Event]
}

class FinancialAssetHolding(quantity: Double, asset: Asset) {

}

abstract class Asset() {
  def describe: String
}

case class Currency(iso: String) extends Asset {
  def describe: String = iso
}


object Asset {

  val USD = new Currency("USD")
  val EUR = new Currency("EUR")



}

class MoneyValue


class BrokerTrade(trader: Trader, broker: Broker, _direction: Direction, security: Security, price: MoneyValue) extends Obligation {

  def party1: Counterparty =  trader
  def party2: Counterparty = broker

  def party1Holding: FinancialAssetHolding = new FinancialAssetHolding(100000.0, Asset.USD)
  def party2Holding: FinancialAssetHolding = new FinancialAssetHolding(100000.0, Asset.EUR)

  def direction: Direction = _direction

  def creatingEvent: Event
  def substitutingEvents: List[Event]
}

class Account {

}

abstract class FinancialAsset

class CashHolding extends FinancialAsset {

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


