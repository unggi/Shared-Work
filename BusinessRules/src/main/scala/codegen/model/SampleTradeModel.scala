package codegen.model

/**
  * Created by unggi on 8/5/16.
  */
object SampleTradeModel {

  import GlobalClassifiers._
  import MultiplicityValues._


  val productTypeClass = new Classifier("ProductType")
    .addProperty("type", StringType)

  val interestRateSwapClass = new Classifier("InterestRateSwap")
    .addProperty("ID", StringType)
    .addProperty("name", StringType)
    .addProperty("product", productTypeClass)

  val legClass = new Classifier("Leg")
    .addProperty("cashflowType", StringType)
    .addProperty("startDate", DateType)
    .addProperty("endDate", DateType)
    .addProperty("cashflowFrequency", IntegerType)

  val swapToLeg = new Association("swap has legs", interestRateSwapClass, "swap", One, legClass, "legs", OneOrMore)

  // Build a simple Interest Rate Swap Leg model
  val tradeClass = new Classifier("Trade")
    .addProperty("entity", StringType)
    .addProperty("counterparty", StringType)
    .addProperty("currency", StringType)
    .addProperty("notional", DoubleType)

  val tradeToSwap = new Association("trade on a swap", tradeClass, "trade", One, interestRateSwapClass, "instrument", One)

}