package spg

/**
 * Client Account Details - accounts, aliases and short names for the parent account of a client.
 */
@GraphNode(name="Counterparty")
class CounterpartyBean {

  var AccountID: Int = 0

  /**
   * The CP Key is the common key across sales and clients.
   */
  @ID
  var CPKey: String = ""

  /**
   * Account name -typically includes the word "PENDING" which makes it difficult to read.
   * The Name field is a better choice for display purposes.
   */
  var ShortName: String = ""

  /**
   * Best long name for an account.
   */
  var Name: String = ""
  /**
   * A 3 valued flag
   *
   * TRUE - if account is a client account and is recently active. (Not sure how recent is recent.)
   * FALSE - If account is a client account and has not been recently active.
   * internal - If account is an internal account (e.g. wash account)
   */
  var Recent: String = ""
  /**
   * A short "nick name" for an account used for handy display and input.
   */
  var Alias: String = ""
}
