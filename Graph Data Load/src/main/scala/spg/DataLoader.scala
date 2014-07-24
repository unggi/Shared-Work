package spg

import java.io._
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date
import java.util.zip.{ZipEntry, ZipFile, ZipInputStream}
import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.bean.{ColumnPositionMappingStrategy, CsvToBean}
import org.neo4j.graphdb._
import org.neo4j.graphdb.factory.{GraphDatabaseSettings, GraphDatabaseFactory}
import org.neo4j.unsafe.batchinsert.BatchInserters
import scala.collection.JavaConversions._

object DataLoader {

  val date = "20140721"
  val TRADE_HISTORY = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\mbs-batch\\data\\HistoricalTradesSolrExtract_" + date + ".txt"
  val CMO_SECURITIES = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\solr-security-feed\\data\\cmo." + date + ".zip"

  val DB_PATH = "target/neo4j-test-loading-db"

  def main(args: Array[String]): Unit = {

    // Initialize Neo4J configuration
    //    createConfiguration()

    val db = createBatchInserter(DB_PATH)

    val cmo = new File(CMO_SECURITIES)
    require(cmo.exists(), s"CMO File does not exist or cannot be read: ${cmo}")

    val cmoRecordCount = processCmoSecurities(cmo, db)
      println(s"Processed ${cmoRecordCount} CMO security records")

    System.exit(1)

    val history = new File(TRADE_HISTORY)
    require(history.exists, "History file not found or cannot be opened.")

    println(s"Loading  Trade History File\n\nHistory File Path = [${TRADE_HISTORY}]\n")


    val tradeRecordCount = processTradeHistory(history, db)

    println(s"Processed Trade ${tradeRecordCount} records")

    db.shutdown()
  }

  def processCmoSecurities(path: File, db: GraphDatabaseService): Int = {
    val label = DynamicLabel.label("Security")
    CSV(path, 100, '|') {
      (line, fields, record) =>
        val cmo = new CmoBean()
        val node = db.createNode(label)
        for (i <- 0 until record.size) {
          FieldAccessor.setField(cmo, fields(i), record(i))
          val propValue = FieldAccessor.getField(cmo, fields(i))
          node.setProperty(fields(i), propValue)
        }
    }
  }

  def processTradeHistory(path: File, db: GraphDatabaseService): Int = {
    val label = DynamicLabel.label("Trade")

    CSV(path, 100000) {
      (line, fields, record) =>
        val bean = new TradeBean()

        val node = db.createNode(label)
        for (i <- 0 until record.size) {
          FieldAccessor.setField(bean, fields(i), record(i))
          val propValue = FieldAccessor.getField(bean, fields(i))
          node.setProperty(fields(i), propValue)
        }
    }
  }


  def CSV(path: File, interval: Int, delimiter: Char = '~')(block: (Int, Array[String], Array[String]) => Unit): Int = {

    require(path.exists(), s"[${path}] not found")

    val fstrm =
      if (path.getName.endsWith(".zip")) {
        val zip = new ZipFile(path)
        val entry = zip.entries.find(e => e.getName.endsWith(".csv"))
        require(entry.isDefined, s"Could not find a CSV file in Zipfile ${path.getCanonicalPath}")
        zip.getInputStream(entry.get)
      }
      else
        new FileInputStream(path)

    val reader = new CSVReader(new InputStreamReader(fstrm), delimiter, '\000')

    val header: Array[String] = reader.readNext()

    val fields = for (fld <- header) yield fld

    val startTime = new Date().getTime

    var line = 1
    Stream.continually(reader.readNext).takeWhile(record => (record ne null) && (record.repr ne null)).foreach {
      record =>
        //
        //Invoke the lambda function which processes a single record in the CSV file
        //
        block(line, fields, record)

        //
        // Every "interval" rows, print time and processing stats.
        //
        if (line % interval == 0) {
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






