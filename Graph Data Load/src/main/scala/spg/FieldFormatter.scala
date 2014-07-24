package spg

import java.io.InvalidClassException
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date

import scala.reflect.macros.ParseException
import scala.util.{Failure, Success, Try}

object FieldFormatter {

  import java.lang.Double._
  import java.lang.Integer._

  val ClassOfDouble = classOf[Double]
  val ClassOfInt = classOf[Int]
  val ClassOfString = classOf[String]
  val ClassOfDate = classOf[Date]
  val ClassOfTime = classOf[Time]


  def format(value: Double): String = value.toString

  def format(value: Int): String = value.toString

  def format(value: String): String = value

  def format(value: Time): String =
    new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(value)

  def format(value: Date): String =
    if (value != null)
      new SimpleDateFormat("yyyyMMdd").format(value)
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

  def asDate(input: String): Try[Date] =
    Try(new SimpleDateFormat("yyyyMMdd").parse(input)) orElse
      Try(new SimpleDateFormat("yyyyMMdd").parse(input))

  def asTime(input: String): Try[Time] =
    Try(new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(input).asInstanceOf[Time]) orElse
      Try(new SimpleDateFormat("yyyy-MM=dd HH:mm:ss").parse(input).asInstanceOf[Time])


  def parse(input: String, cls: Class[_]): Try[Any] =
    cls match {
      case ClassOfDouble => Try(parseDouble(input))
      case ClassOfInt => Try(parseInt(input))
      case ClassOfString => Try(input)
      case ClassOfDate => Try(new SimpleDateFormat("yyyyMMdd").parse(input)) orElse
        Try(new SimpleDateFormat("yyyyy-MM-dd"))
      case ClassOfTime => Try(new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(input).asInstanceOf[Time]) orElse
        Try(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(input).asInstanceOf[Time])
      case _ => Failure(new InvalidClassException(s"${cls.getCanonicalName} is not a supported class type"))
    }
}
