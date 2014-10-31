package spg.datamodel

import spg.DerivedRelationship

class CounterpartySalesCoverageBean {

  /**
   * This is a string starting with "CP"
   */
  var ID: String = ""
  /**
   *
   */
  var CounterpartyID: String = ""

  /**
   * The Rep's RR number
   */
  @DerivedRelationship(fromClass = "SalesPerson", fromField="RepID", toClass="Client", toField="CounterpartyID")
  var RepID: Array[Int] = _
  /**
   * A special field encoded with the RR and names of covering sales people.
   *
   * The syntax is:
   *
   *              (<RR>\@\@<Name>||)*(<RR>\@\@<Name>)
   */

  var SalesCoverage: String = ""
}
