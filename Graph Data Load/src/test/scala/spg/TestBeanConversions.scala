package spg

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by usoemard on 7/17/2014.
 */
class TestBeanConversions extends FlatSpec with Matchers {

  behavior of "Bean Field Conversions"

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

  it should "Convert a date to and from text" in {
    val bean = new TradeBean()
    FieldAccessor.getField(bean, "TradeDate") should equal ("")
    FieldAccessor.setField(bean, "TradeDate", "20141130")
    FieldAccessor.getField(bean, "TradeDate") should equal("20141130")
  }

  it should "Convert a time to and from text" in {
    val bean = new TradeBean()
    FieldAccessor.getField(bean, "OrigCreateTime") should equal("")
    for ( t <- List("20141130 23:12:30", "07541201 00:00:00", "99991231 23:59:59")) {
      FieldAccessor.setField(bean, "OrigCreateTime", t)
      FieldAccessor.getField(bean, "OrigCreateTime") should equal(t)
    }
  }
}
