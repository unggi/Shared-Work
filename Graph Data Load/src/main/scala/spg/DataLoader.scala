package spg

import java.io._
import java.sql.Time
import java.text.SimpleDateFormat
import java.util
import java.util.Date
import java.util.Formatter.DateTime
import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.bean.{ColumnPositionMappingStrategy, CsvToBean}
import org.neo4j.cypher.internal.compiler.v1_9.symbols.RelationshipType
import org.neo4j.graphdb._
import org.neo4j.graphdb.factory.{GraphDatabaseSettings, GraphDatabaseFactory}
import org.neo4j.unsafe.batchinsert.BatchInserters

import scala.collection.mutable.ListBuffer

object DataLoader {

  val TRADE_HISTORY = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\mbs-batch\\data\\HistoricalTradesSolrExtract_20140702.txt"

  val DB_PATH = "target/neo4j-test-loading-db"

  def CSV(path: File, messageFrequency: Int)(block: (Int, Array[String], Array[String]) => Unit): Int = {

    val reader = new CSVReader(new InputStreamReader(new FileInputStream(path)), '~', '\000')
    var record: Array[String] = reader.readNext()

    val fields = for (fld <- record) yield fld

    val startTime = new Date().getTime

    var line = 1
    while (((record = reader.readNext) != null) && record.repr != null) {
      //
      //Invoke the lambda function which processes a single record in the CSV file
      //
      block(line, fields, record)

      //
      // Every "messageFrequency" rows, print time and processing stats.
      //
      if (line % messageFrequency == 0) {
        val now = new Date().getTime()
        val msSinceStart: Double = now - startTime
        val rate: Double = (msSinceStart / line) * 1000.0
        val rt = Runtime.getRuntime
        print(f"Read $line%10d records. Total Time = ${msSinceStart / 1000.0}%.2f secs. Time per Record = ${rate}%.2f ns.")
        println(f" Memory (free/total) ${rt.freeMemory() / 1000000.0}%.2f/${rt.totalMemory() / 1000000.0}%.2f ")
      }

      line = line + 1
    }

    reader.close()
    line
  }

  def main(args: Array[String]): Unit = {

    println(s"Loading  Trade History File\n\nHistory File Path = [${TRADE_HISTORY}]\n")

    val history = new File(TRADE_HISTORY)
    require(history.exists, "History file not found or cannot be opened.")

    // Initialize Neo4J configuration
    //    createConfiguration()

    val db = createBatchInserter(DB_PATH)
    val label = DynamicLabel.label("Trade")

    val count = CSV(history, 100000) {
      (line, fields, record) =>
        val bean = new TradeBean()

        val node = db.createNode(label)
        for (i <- 0 until record.size) {
          setField(bean, fields(i), record(i))
          val propValue = getFieldAsCanonicalString(bean, fields(i))
          node.setProperty(fields(i), propValue)
        }
    }

    println(s"Processed ${count} records")

    db.shutdown()
  }

  val ClassOfDouble = classOf[Double]
  val ClassOfInt = classOf[Int]
  val ClassOfString = classOf[String]
  val ClassOfDate = classOf[Date]
  val ClassOfTime = classOf[Time]

  def setField(bean: TradeBean, name: String, value: String) {

    import java.lang.Double._
    import java.lang.Integer._

    val field = bean.getClass.getDeclaredField(name)
    field.setAccessible(true)
    require(field != null)

    try {
      field.getType match {
        case ClassOfDouble =>
          field.setDouble(bean, parseDouble(value))
        case ClassOfInt =>
          field.setInt(bean, parseInt(value))
        case ClassOfString =>
          field.set(bean, value)
        case ClassOfTime =>
          val df = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss.SSS")
          val time = new Time(df.parse(value).getTime)
          field.set(bean, time)
        case ClassOfDate =>
          val df = new SimpleDateFormat("yyyymmdd")
          field.set(bean, df.parse(value))
        case cls => println(s"Unhandled type ${cls} for Field: $name")
      }
    } catch {
      case e: NumberFormatException =>
        println(s"Bad numeric format in input: field = ${name} value = ${value}")
    }
  }

  def getFieldAsCanonicalString(bean: AnyRef, fieldName: String): String = {

    import java.lang.Double._
    import java.lang.Integer._

    val field = bean.getClass.getDeclaredField(fieldName)
    field.setAccessible(true)
    require(field != null, s"Field not found with name ${fieldName} in bean of class type ${bean.getClass.getSimpleName}")

    field.getType match {
      case ClassOfDouble =>
        val value = field.getDouble(bean)
        f"${value}%.6f"
      case ClassOfInt =>
        val value = field.getInt(bean)
        value.toString
      case ClassOfString =>
        (field.get(bean)).asInstanceOf[String]
      case ClassOfTime =>
        val df = new SimpleDateFormat("yyyymmdd hh:MM:ss")
        val value = field.get(bean).asInstanceOf[Time]
        df.format(value)
      case ClassOfDate =>
        val df = new SimpleDateFormat("yyyymmdd")
        val value = field.get(bean).asInstanceOf[Date]
        df.format(value)
      case cls =>
        println(s"Unhandled type ${cls} for Field: ${fieldName}")
        ""
    }
  }

  /**
   *
   * @param f - the file representing the root of the database to be removed.
   */
  def removeDatabase(f: File) {
    if (f.isDirectory) {
      f.listFiles.foreach(removeDatabase(_))
    }
    f.delete()
  }

  /**
   * Create an inserter "pseudo-GraphDatabase" used for rapid insertion.
   *
   * @param path
   * @return
   */
  def createBatchInserter(path: String): GraphDatabaseService = {
    //
    // Delete the old database first.
    //
    val root = new File(path)
    removeDatabase(root)
    //
    // Create a pseudo database which is used to do batch inserts.
    //
    BatchInserters.batchDatabase(path)
  }
}

class TradeBean {
  var TradeKey: Double = 0.0
  var TradeId: Int = 0
  var RevId: Int = 0
  var Ledger: String = ""
  var BookName: String = ""
  var BuySell: String = ""
  var Counterparty = ""
  var TradePrice = 0.0
  var Quantity = 0.0
  var SecurityId: String = ""
  var Cusip: String = ""
  var TradeDate: Date = _
  var SettleDate: Date = _
  var InstrumentType: String = ""
  var CreateDate: Date = _
  var ExecutionDate: Date = _
  var OrigCreateTime: Time = _
  var Comments: String = ""
  var SalesCredit: String = ""
  var SalesPerson: String = ""
  var SalesPersonName: String = ""
  var SalesQualityOfBusiness: String = ""
  var CurrentFace: Double = 0.0
  var LevelSpot1: String = ""
  var LevelSpot2: String = ""
  var LevelSpot3: String = ""
  var LevelSpot4: String = ""
  var MarketContextNotes: String = ""
  var MarketContextUpdateUser: String = ""
  var CounterpartyName: String = ""
  var SecurityDescription: String = ""
  var TradeStatus: String = ""
  var DealType: String = ""
  var DealSubType: String = ""
  var MatchStatus: String = ""
  var SecurityType: String = ""
  var InstrumentSubType: String = ""
  var OrigCreateUser: String = ""
  var CreateUser: String = ""
  var Trader: String = ""
  var OrigSource: String = ""
  var BookDesk: String = ""
  var BookStrategy: String = ""
}
