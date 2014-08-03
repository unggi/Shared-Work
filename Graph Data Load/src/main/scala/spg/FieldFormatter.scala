package spg

import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date

import scala.util.{Success, Try}

object FieldFormatter {

  import java.lang.Double._
  import java.lang.Integer._

  val dateFormats = List("yyyy-MM-dd", "yyyyMMdd")
  val timeFormats = List("yyyy-MM-dd HH:mm:ss", "yyyyMMdd HH:mm:ss")

  val ClassOfDouble = classOf[Double]
  val ClassOfInt = classOf[Int]
  val ClassOfString = classOf[String]
  val ClassOfDate = classOf[Date]
  val ClassOfTime = classOf[Time]


  def format(value: Double): String = value.toString

  def format(value: Int): String = value.toString

  def format(value: String): String = value

  def format(value: Time): String =
    if (value != null)
      new SimpleDateFormat(timeFormats(0)).format(value)
    else
      ""

  def format(value: Date): String =
    if (value != null)
      new SimpleDateFormat(dateFormats(0)).format(value)
    else
      ""

  def format(value: Any): String =
    value match {
      case dbl: Double => format(dbl)
      case integer: Int => format(integer)
      case str: String => format(str)
      case time: Time => format(time)
      case date: Date => format(date)
      case _ => s"${value.getClass.getSimpleName} is not a supported data type"
    }

  def asDouble(input: String): Try[Double] = Try(parseDouble(input))

  def asInteger(input: String): Try[Int] = Try(parseInt(input))

  def asString(input: String): Try[String] = Success(input)

  def asDate(input: String): Try[Date] = {

    Try {
      val format1 = new SimpleDateFormat(dateFormats(0))
      format1.setLenient(false)
      format1.parse(input)
    } orElse Try {
      val format2 = new SimpleDateFormat(dateFormats(1))
      format2.setLenient(false)
      format2.parse(input)
    }
  }

  def asTime(input: String): Try[Time] = {
    Try {
      val format1 = new SimpleDateFormat(timeFormats(0))
      format1.setLenient(false)
      new Time(format1.parse(input).getTime)
    } orElse Try {
      val format2 = new SimpleDateFormat(timeFormats(1))
      format2.setLenient(false)
      new Time(format2.parse(input).getTime)
    }
  }
}
