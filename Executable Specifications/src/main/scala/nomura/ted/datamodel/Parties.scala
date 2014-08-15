package nomura.ted.datamodel


abstract class Counterparty(val rdmid: String)

class Client(_rdmid: String) extends Counterparty(_rdmid)

case class Trader(_rdmid: String) extends Counterparty(_rdmid)

case class Salesperson(_rdmid: String) extends Counterparty(_rdmid)

case class Broker(_rdmid: String) extends Counterparty(_rdmid)

case class Dealer(_rdmid: String) extends Counterparty(_rdmid)


