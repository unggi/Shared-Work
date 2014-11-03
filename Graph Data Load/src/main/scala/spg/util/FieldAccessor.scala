package spg.util

import java.sql.Time
import java.util.Date

import scala.util.{Failure, Success, Try}


object FieldAccessor {

  import spg.util.FieldFormatter._

  def set[FieldType](bean: AnyRef, fieldName: String, text: String, parser: (String) => Try[FieldType], setter: (AnyRef, FieldType) => Unit) =
    parser(text) match {
      case Failure(e) =>
        println(s"Error Parsing Field in Bean Class = ${bean.getClass.getSimpleName} Field = ${fieldName} Text = [${text}]")
        println("Exception is " + e.getMessage)
      case Success(datum) =>
        Try(setter(bean, datum)) match {
          case Success(x) =>
          case Failure(e) =>
            println(s"Error Setting Field in Bean Class = ${bean.getClass.getCanonicalName} Field = ${fieldName} Text =  ${text}")
        }
    }

  def setField(bean: AnyRef, name: String, value: String) = {

    val field = bean.getClass.getDeclaredField(name)
    require(field != null, s"Invalid field name: bean = ${bean.getClass.getSimpleName} field=$name")

    field.setAccessible(true)

    field.getType match {
      case ClassOfDouble =>
        set(bean, name, value, asDouble, field.setDouble)
      case ClassOfInt =>
        set(bean, name, value, asInteger, field.setInt)
      case ClassOfString =>
        set(bean, name, value, asString, field.set)
      case ClassOfTime =>
        set(bean, name, value, asTime, field.set)
      case ClassOfDate =>
        set(bean, name, value, asDate, field.set)
      case cls =>
        println(s"Unhandled type ${cls} for Field: $name")
    }
  }

  def getField(bean: AnyRef, fieldName: String): String =
    Try(bean.getClass.getDeclaredField(fieldName)) match {
      case Success(field) =>

        field.setAccessible(true)

        field.getType match {
          case ClassOfDouble =>
            format(field.getDouble(bean))
          case ClassOfInt =>
            format(field.getInt(bean))
          case FieldFormatter.ClassOfString =>
            (field.get(bean)).asInstanceOf[String]
          case FieldFormatter.ClassOfTime =>
            format(field.get(bean).asInstanceOf[Time])
          case FieldFormatter.ClassOfDate =>
            format(field.get(bean).asInstanceOf[Date])
          case cls => s"Unhandled type ${cls} for Field: ${fieldName}"
        }
      case Failure(e) =>
        System.err.println(s"Field not found with name [${fieldName}] in bean of class type ${bean.getClass}")
        ""
    }
}