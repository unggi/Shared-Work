package spg.etl.csv

import java.io._
import java.lang.reflect.Field
import java.sql.Time
import java.util.Date
import java.util.zip.ZipFile

import au.com.bytecode.opencsv.CSVReader
import spg.etl._
import spg.util.FieldFormatter

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer


class EntityMetaModel(cls: Class[_]) {

  def declaredFields: Array[Field] = cls.getDeclaredFields

  def getField(name: String): Field = cls.getDeclaredField(name)

  def simpleName = cls.getSimpleName

  def forEachDeclaredField(action: (Field) => Unit) = declaredFields.foreach(action)

}

case class ColumnToModelMapping(column: String, model: String) {
  var field: Field = _
}

case class NameValuePair(name: String, value: Any)

class CSVToModelMapping[T](cls: Class[_]) {

  self: CSVDataSource[T] =>

  val model = new EntityMetaModel(cls)
  val mapping = new ListBuffer[ColumnToModelMapping]()

  def mapColumns(map: Tuple2[String, String]*): Unit = {

    model.forEachDeclaredField { field => println(s"Field ${field.getName}")}

    map.foreach {
      case Tuple2(column: String, modelField: String) =>
        val c = ColumnToModelMapping(column, modelField)
        try {
          c.field = model.getField(modelField)
        }
        catch {
          case e: Throwable =>
            println(s"Missing field: ${model.simpleName} does not have field ${modelField} which comes from CSV column ${column}")
        }
        mapping.append(c)
    }
  }

  def showColumnMapping(pw: PrintWriter): Unit = {
    pw.println(s" ======== >> Processing ${self.sourcePath.getName}")
    pw.println(s"There are ${mapping.size} header columns in each input line")

    pw.println()
    pw.println("Field Mapping { ")
    pw.println("   CSV File         -> Node Property ")
    mapping.foreach {
      entry =>
        pw.println(s"  ${entry.column} -> ${entry.model} : ${entry.field.getName}")
    }
    pw.println("}")
  }


}

case class Datum(list: List[NameValuePair])

class CSVDataSource[T](val sourcePath: File, cls: Class[_]) extends CSVToModelMapping[T](cls) with DataSource[Datum] {


  var input: List[Array[String]] = _

  var lineNumber = 0

  def delimiter: Char = '~'

  def initialize: Unit = {

    val fstrm = openFile()
    val reader = new CSVReader(new InputStreamReader(fstrm), delimiter, '\000')
    input = reader.readAll.toList
    val header: Array[String] = input(0)

    // Check the headers from the file against the field to model mapping.
    mapping.zip(header).foreach {
      case (map: ColumnToModelMapping, csv: String) =>
        println(s"Column = <${map.column}>  CSV = <${csv}> ")
        require(map.column.equals(csv), s"CSV Column Heading: ${map.column} <> ${csv}")
    }
    lineNumber = 1
  }

  def hasMore = lineNumber < input.size

  def next: Datum = {

    val line = input(lineNumber)
    lineNumber = lineNumber + 1

    buildNormalizedRecord(line)
  }

  // Well formed values for using in case statements based on type switches.
  val IntClass = classOf[Int]
  val DoubleClass = classOf[Double]
  val StringClass = classOf[String]
  val DateClass = classOf[Date]
  val TimeClass = classOf[Time]
  val BooleanClass = classOf[Boolean]

  def buildNormalizedRecord(record: Array[String]): Datum = {

    val list = ListBuffer[NameValuePair]()

    for ((entry: ColumnToModelMapping, textValue: String) <- mapping.zip(record) if textValue.length > 0) {

      val modelName = entry.model
      val field: Field = entry.field
      require(field != null, s"Field not found: class = ${model.simpleName} field = ${entry.field}")

      try {

        field.getType match {
          case IntClass =>
            val integer = FieldFormatter.asInteger(textValue).get
            list += NameValuePair(modelName, integer)
          case DoubleClass =>
            val dbl = FieldFormatter.asDouble(textValue).get
            list += NameValuePair(modelName, dbl)
          case DateClass =>
            val date = FieldFormatter.asDate(textValue).get
            list += NameValuePair(modelName, date)
          case TimeClass =>
            val time = FieldFormatter.asTime(textValue).get
            list += NameValuePair(modelName, time)
          case StringClass =>
            list += NameValuePair(modelName, textValue)
          case BooleanClass =>
            val bool = FieldFormatter.asBoolean(textValue).get
            list += NameValuePair(modelName, if (bool) "true" else "false")
          case x =>
            //
            // Don't add to list if the field type is unknown
            //
            println(s"Unknown Field Type Class = $model Field = ${entry.model} FieldType = ${x.getSimpleName} value = $textValue")
        }
      } catch {
        case e: Throwable =>
          System.err.println(s"Exception for class = ${model.simpleName} field = ${field.getName} type = ${field.getType.getSimpleName} value ${e.getMessage}")
      }
    }
    Datum(list.toList)
  }

  def openFile(): InputStream =
    if (sourcePath.getName.endsWith(".zip")) {
      val zip = new ZipFile(sourcePath)
      val entry = zip.entries.find(e => e.getName.endsWith(".csv"))
      require(entry.isDefined, s"Could not find a CSV file in Zipfile ${sourcePath.getCanonicalPath}")
      zip.getInputStream(entry.get)
    }
    else
      new FileInputStream(sourcePath)

}

class CSVBatchDriver(src: DataSource[Datum], trx: DataTransformation[Datum], tgt: DataTarget[Datum]) extends ETLTask[Datum] {

  def source: DataSource[Datum] = src

  def transformer: DataTransformation[Datum] = trx

  def target: DataTarget[Datum] = tgt
}

