package codegen.model

import java.io.{FileOutputStream, PrintWriter}

import org.scalatest.FlatSpec


/**
  * Created by unggi on 7/30/16.
  */
class TestModelFactory extends FlatSpec {

  import GlobalClassifiers._
  import MultiplicityValues._


  val interestRateSwapClass = new Classifier("InterestRateSwap")
    .addProperty("ID", StringType)
    .addProperty("name", StringType)

  val legClass = new Classifier("Leg")
    .addProperty("cashflowType", StringType)
    .addProperty("startDate", DateType)
    .addProperty("endDate", DateType)
    .addProperty("cashflowFrequency", IntegerType)

  val swapToLeg = new Association("swap has legs", interestRateSwapClass, "swap", One, legClass, "leg", OneOrMore)


  // Build a simple Interest Rate Swap Leg model
  val tradeClass = new Classifier("Trade")
    .addProperty("entity", StringType)
    .addProperty("counterparty", StringType)
    .addProperty("currency", StringType)
    .addProperty("notional", DoubleType)

  val tradeToSwap = new Association("trade on a swap", tradeClass, "trade", One, interestRateSwapClass, "swap", One)

  "A Model" should "render as a Plant UML file" in {
    val model = new Model(tradeClass)

    val fostrm = new FileOutputStream("out.puml")
    val ppw = new PrintWriter(fostrm)

    model.renderPUML(ppw)
    ppw.close()
    fostrm.close()
  }
}

class Snapshot(filename: String) {
  def exists: Boolean = {}

  def take() {}

  def compare(updated: String = filename) {}

  def delete() {}

  def replace {}

  def update() = {
    
  }
