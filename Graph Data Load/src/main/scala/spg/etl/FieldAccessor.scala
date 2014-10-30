package spg.etl

import java.sql.Time
import java.util.Date
import scala.util.{Failure, Success, Try}


object FieldAccessor {

  import FieldFormatter._

  def set[FieldType](pojo: AnyRef, fieldName: String, text: String, parser: (String) => Try[FieldType], setter: (AnyRef, FieldType) => Unit) =
    parser(text) match {
      case Failure(e) =>
        println(s"Error Parsing Field in Bean Class = ${pojo.getClass.getSimpleName} Field = ${fieldName} Text = [${text}]")
        println("Exception is " + e.getMessage)
      case Success(datum) =>
        Try(setter(pojo, datum)) match {
          case Success(x) =>
          case Failure(e) =>
            println(s"Error Setting Field in Bean Class = ${pojo.getClass.getCanonicalName} Field = ${fieldName} Text =  ${text}")
        }
    }

  def setField(pojo: AnyRef, name: String, value: String) = {

    val field = pojo.getClass.getDeclaredField(name)
    require(field != null, s"Invalid field name: bean = ${pojo.getClass.getSimpleName} field=$name")

    field.setAccessible(true)

    field.getType match {
      case ClassOfDouble =>
        set(pojo, name, value, asDouble, field.setDouble)
      case ClassOfInt =>
        set(pojo, name, value, asInteger, field.setInt)
      case ClassOfString =>
        set(pojo, name, value, asString, field.set)
      case ClassOfTime =>
        set(pojo, name, value, asTime, field.set)
      case ClassOfDate =>
        set(pojo, name, value, asDate, field.set)
      case cls =>
        println(s"Unhandled type ${cls} for Field: $name")
    }
  }

  def getField(pojo: AnyRef, fieldName: String): String =
    Try(pojo.getClass.getDeclaredField(fieldName)) match {
      case Success(field) =>

        field.setAccessible(true)

        field.getType match {
          case ClassOfDouble =>
            format(field.getDouble(pojo))
          case ClassOfInt =>
            format(field.getInt(pojo))
          case FieldFormatter.ClassOfString =>
            (field.get(pojo)).asInstanceOf[String]
          case FieldFormatter.ClassOfTime =>
            format(field.get(pojo).asInstanceOf[Time])
          case FieldFormatter.ClassOfDate =>
            format(field.get(pojo).asInstanceOf[Date])
          case cls => s"Unhandled type ${cls} for Field: ${fieldName}"
        }
      case Failure(e) =>
        System.err.println(s"Field not found with name [${fieldName}] in bean of class type ${pojo.getClass}")
        ""
    }
}