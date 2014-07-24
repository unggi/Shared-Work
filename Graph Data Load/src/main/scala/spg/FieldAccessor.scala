package spg

import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date

import scala.util.{Try, Failure, Success}


object FieldAccessor {

  import spg.FieldFormatter._

  def setField(bean: AnyRef, name: String, value: String) {

    import java.lang.Double._
    import java.lang.Integer._

    val field = bean.getClass.getDeclaredField(name)
    field.setAccessible(true)
    require(field != null)

    field.getType match {
      case ClassOfDouble =>
        asDouble(value) match {
          case Success(d) => field.setDouble(bean, d)
          case Failure(e) => println(s"Error ${e.getMessage} in date field: Bean Class = ${bean.getClass.getCanonicalName} Field = ${name} Text = ${value}")
        }
      case ClassOfInt =>
        FieldFormatter.asInteger(value) match {
          case Success(i) => field.setInt(bean, i)
          case Failure(e) => println(e)
        }
      case ClassOfString =>
        field.set(bean, value)
      case ClassOfTime =>
        field.set(bean, asTime(value).getOrElse(new Time(0)))
      case ClassOfDate =>
        println(s"${bean.getClass.getSimpleName} -> ${name} = [${value}]")
        if (!value.isEmpty) {
          val df = new SimpleDateFormat("yyyyMMdd")
          field.set(bean, df.parse(value))
        }
      case cls => println(s"Unhandled type ${cls} for Field: $name")
    }
  }

  def getField(bean: AnyRef, fieldName: String): String = {

    val field = bean.getClass.getDeclaredField(fieldName)
    field.setAccessible(true)
    require(field != null, s"Field not found with name ${fieldName} in bean of class type ${bean.getClass.getSimpleName}")

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
      case cls =>
        s"Unhandled type ${cls} for Field: ${fieldName}"
    }
  }
}
