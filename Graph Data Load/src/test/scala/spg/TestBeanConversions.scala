package spg

import org.scalatest.{FlatSpec, Matchers}
<<<<<<< HEAD
import spg.datamodel.TradeBean
import spg.etl.{FieldFormatter, FieldAccessor}
=======
>>>>>>> parent of 9f7f0df... Refreshing

/**
 * Created by usoemard on 7/17/2014.
 */
class TestBeanConversions extends FlatSpec with Matchers {

  behavior of "Bean Field Conversions"

  it should "throw a NoSuchFieldException if an invalid field name is given" in {
    val bean = new TradeBean()

    a[NoSuchFieldException] should be thrownBy {
      FieldAccessor.setField(bean, "ABADFieldName", "123")
    }
  }

  it should "Convert a double field to and from text" in {
    val bean = new TradeBean()
    FieldAccessor.getField(bean, "TradePrice") should equal("0.0")
    FieldAccessor.setField(bean, "TradePrice", "99.123456")
    FieldAccessor.getField(bean, "TradePrice") should equal("99.123456")

  }

  it should "Convert an integer to and from text" in {
    val bean = new TradeBean()
    FieldAccessor.getField(bean, "TradeId") should equal("0")
    FieldAccessor.setField(bean, "TradeId", "123456")
    FieldAccessor.getField(bean, "TradeId") should equal("123456")
  }

  it should "Convert a string to and from text" in {
    val bean = new TradeBean()
    FieldAccessor.getField(bean, "Counterparty") should equal("")
    FieldAccessor.setField(bean, "Counterparty", "A Dummy Counterparty")
    FieldAccessor.getField(bean, "Counterparty") should equal("A Dummy Counterparty")
  }

  it should "Convert date fields back and forth symmetrically" in {
    val date = FieldFormatter.asDate("2014-11-30")
    date.isSuccess should be(true)
    FieldFormatter.format(date.get) should equal("2014-11-30")
  }

  it should "Convert a date to and from text" in {
    val bean = new TradeBean()

    FieldAccessor.getField(bean, "TradeDate") should equal("")
    FieldAccessor.setField(bean, "TradeDate", "2014-11-30")
    FieldAccessor.getField(bean, "TradeDate") should equal("2014-11-30")
    FieldAccessor.setField(bean, "TradeDate", "20141130")
    FieldAccessor.getField(bean, "TradeDate") should equal("2014-11-30")
  }

  val testDateTimes =
    List("20141130 10:12:30" -> "2014-11-30 10:12:30",
      "20141130 23:12:30" -> "2014-11-30 23:12:30",
      "07541201 00:00:00" -> "0754-12-01 00:00:00",
      "99991231 23:59:59" -> "9999-12-31 23:59:59")

  it should "Parse date times with no leniency" in {
    for ((in, out) <- testDateTimes) {
      println("RESULT IS " + FieldFormatter.asTime(in))
      FieldFormatter.format(FieldFormatter.asTime(in).get) should equal(out)
    }
  }

  it should "Convert a time to and from text" in {
    val bean = new TradeBean()
    FieldAccessor.getField(bean, "OrigCreateTime") should equal("")
    for ((in, out) <- testDateTimes) {
      FieldAccessor.setField(bean, "OrigCreateTime", in)
      FieldAccessor.getField(bean, "OrigCreateTime") should equal(out)
    }
  }
}
