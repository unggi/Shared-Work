package spg.etl.csv

import java.io._
import java.lang.reflect.Field
import java.sql.Time
import java.util.Date
import java.util.zip.ZipFile

import au.com.bytecode.opencsv.CSVReader
import org.neo4j.graphdb.Node
import spg.etl.{BatchDriver, DataSource, FieldFormatter}

import scala.collection.JavaConversions._
import scala.collection.immutable.HashMap
import scala.collection.mutable.ListBuffer

case class ColumnToModelMapping(column: String, model: String) {
  var field: Field = _
}

class NormalizedRecord extends HashMap[String, String]

class CSVDataSource(sourcePath: File, driver: BatchDriver[NormalizedRecord]) extends DataSource[NormalizedRecord] {

  var currentLineNumber = 0

  def progressInterval: Int = 10000

  val mapping = new ListBuffer[ColumnToModelMapping]()

  val startTime = new Date().getTime
  var input: List[Array[String]] = _

  def initialize: Unit = {

    val fstrm = openFile()
    val reader = new CSVReader(new InputStreamReader(fstrm), delimiter, '\000')
    val input = reader.readAll
    val header: Array[String] = reader.readNext()

    // Check the headers from the file against the field to model mapping.
    mapping.zip(header).foreach {
      case (map: ColumnToModelMapping, csv: String) =>
        println(s"Column = <${map.column}>  CSV = <${csv}> ")
        require(map.column.equals(csv), s"CSV Column Heading: ${map.column} <> ${csv}")
    }
    currentLineNumber = 1

  }

  def hasMore = {

  }

  def next: NormalizedRecord = {
    val line = reader.readNext
    val record: NormalizedRecord = _
    if (line != null || line.repr != null) {
      // Convert to Record

      //
      // Every "interval" rows, print time and processing stats.
      //

      if (currentLineNumber % progressInterval == 0)
        driver.printProgressMessage(startTime, currentLineNumber)

      currentLineNumber = currentLineNumber + 1
    }

    reader.close()

    driver.printProgressMessage(startTime, currentLineNumber)

    record

  }

  def delimiter: Char = '~'

  def showColumnMapping(pw: PrintWriter): Unit = {
    pw.println(s" ======== >> Processing ${sourcePath.getName}")
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

  def mapColumns(cls: Class[_], map: Tuple2[String, String]*): Unit = {

    map.foreach {
      case Tuple2(column: String, model: String) =>
        val c = ColumnToModelMapping(column, model)
        try {
          c.field = cls.getDeclaredField(model)
        }
        catch {
          case e: Throwable =>
            println(s"Missing field: ${cls.getSimpleName} does not have field ${model} from ${column}")
        }
        mapping.append(c)
    }
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


trait CSVBatchDriver extends BatchDriver[NormalizedRecord] {

  def sourcePath: File


  /**
   * To be overidden in a subclass
   * @param line
   * @param data
   */
  def processRow(line: Int, data: Array[String]): Unit


  // Well formed values for using in case statements based on type switches.
  val IntClass = classOf[Int]
  val DoubleClass = classOf[Double]
  val StringClass = classOf[String]
  val DateClass = classOf[Date]
  val TimeClass = classOf[Time]
  val BooleanClass = classOf[Boolean]


  def rowToBean(bean: AnyRef, node: Node, record: Array[String]): Unit = {

    import spg.etl.FieldAccessor._
    val beanClass = bean.getClass

    for ((entry: ColumnToModelMapping, textValue: String) <- mapping.zip(record)) {

      if (textValue.length > 0) {

        val modelName = entry.model
        var field: Field = null

        try {
          field = entry.field
          require(field != null, s"Field not found: bean = ${beanClass.getSimpleName} field = ${entry.field}")

          field.getType match {
            case IntClass =>
              val integer = FieldFormatter.asInteger(textValue)
              node.setProperty(modelName, integer.get)
              setField(bean, modelName, textValue)
            case DoubleClass =>
              val dbl = FieldFormatter.asDouble(textValue)
              node.setProperty(modelName, dbl.get)
              setField(bean, modelName, textValue)
            case DateClass =>
              val date = FieldFormatter.asDate(textValue)
              node.setProperty(modelName, date.get.getTime)
              setField(bean, modelName, textValue)
            case TimeClass =>
              val time = FieldFormatter.asTime(textValue)
              node.setProperty(modelName, time.get.getTime)
              setField(bean, modelName, textValue)
            case StringClass =>
              node.setProperty(modelName, textValue)
              setField(bean, modelName, textValue)
            case BooleanClass =>
              val bool = FieldFormatter.asBoolean(textValue)
              node.setProperty(modelName, if (bool.get == true) 1 else 0)
              setField(bean, modelName, textValue)
            case x =>
              println(s"Unknown Field Type Class = $beanClass Field = $modelName FieldType = ${x.getSimpleName} value = $textValue")
          }
        } catch {

          case e: Throwable =>

            System.err.println(s"Class = $beanClass")
            System.err.println("Field Instance = " + field)
            System.err.println(s"Field  = ${entry.field}")
            System.err.println(s"Model = $modelName")
            System.err.println(s"Type   = ${field.getType}")
            System.err.println(s"Exception for $beanClass field = $modelName type = ${field.getType.getSimpleName} value ${e.getMessage}")
        }
      }
    }
  }
}

