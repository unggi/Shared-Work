package spg

import java.io._
import java.lang.reflect.Field
import java.sql.Time
import java.util.Date
import java.util.zip.ZipFile

import au.com.bytecode.opencsv.CSVReader
import org.neo4j.graphdb._
import org.neo4j.unsafe.batchinsert.BatchInserters

import scala.collection.JavaConversions._
import scala.collection.immutable.HashMap
import scala.collection.mutable

object DataLoader {

  val date = "20140721"
  val TRADE_HISTORY = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\mbs-batch\\data\\HistoricalTradesSolrExtract_" + date + ".txt"
  val CMO_SECURITIES = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\solr-security-feed\\data\\cmo." + date + ".zip"
  val POOL_SECURITIES = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\solr-security-feed\\data\\pool." + date + ".zip"
  val COUNTERPARTY_COVERAGE = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\mbs-batch\\data\\CounterpartySalesExtractForSolr_" + date + ".txt"
  val COUNTERPARTIES = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\mbs-batch\\data\\CounterpartyExtractForSolr_" + date + ".txt"
  val DB_PATH = "target/neo4j-test-loading-db"

  def main(args: Array[String]): Unit = {

    //
    // Check files exist
    //

    val counterparty = new File(COUNTERPARTIES)
    require(counterparty.exists, s"Counterparty daily file does not exist or cannot be read: ${counterparty}")

    val countpartyCoverage = new File(COUNTERPARTY_COVERAGE)
    require(countpartyCoverage.exists, s"Counterparty daily file does not exist or cannot be read: ${countpartyCoverage}")

    val cmo = new File(CMO_SECURITIES)
    require(cmo.exists(), s"CMO File does not exist or cannot be read: ${cmo}")

    val pool = new File(POOL_SECURITIES)
    require(pool.exists(), s"CMO File does not exist or cannot be read: ${cmo}")

    val history = new File(TRADE_HISTORY)
    require(history.exists, "History file not found or cannot be opened.")

    //
    // Create database
    //

    val db = createBatchInserter(DB_PATH)


    //
    //
    //    val poolRecordCount = processPoolSecurities(pool, db)
    //    println(s"Processed ${poolRecordCount} Pool security records")
    //
    //    val cmoRecordCount = processCmoSecurities(cmo, db)
    //    println(s"Processed ${cmoRecordCount} CMO security records")
    //
    //
    //    println(s"Loading  Trade History File\n\nHistory File Path = [${TRADE_HISTORY}]\n")
    //    val tradeRecordCount = processTradeHistory(history, db)
    //    println(s"Processed Trade ${tradeRecordCount} records")


    //    val counterpartyCount = processCounterparties(counterparty, db)
    //    println(s"Processed ${counterpartyCount} Counterparty records.")
    //
    //    val counterpartyCoverageRecordCount = processCounterpartySalesCoverage(countpartyCoverage, db)
    //    println(s"Processed ${counterpartyCoverageRecordCount} Counterparty records.")
    //



    db.shutdown()
  }


  //
  //
  //  def processCounterparties(path: File, db: GraphDatabaseService): Int = {
  //    val label = DynamicLabel.label("Counterparty")
  //
  //    val map = mutable.HashMap[String, String](
  //      "account_id" -> "AccountID",
  //      "cp_key" -> "CPKey",
  //      "sname" -> "ShortName",
  //      "name" -> "Name",
  //      "recent" -> "Recent",
  //      "alias" -> "Alias"
  //    )
  //
  //    for ((k, v) <- map) {
  //      println(s"Key = $k Value = $v")
  //    }
  //
  //    CSV(path, 1000, ',', Some(map)) {
  //      (line, fields, record) =>
  //        val node = db.createNode(label)
  //        copyRecordToNode(classOf[CounterpartyBean], node, fields, record)
  //    }
  //  }
  //
  //
  //  def processCounterpartySalesCoverage(path: File, db: GraphDatabaseService): Int = {
  //    val label = DynamicLabel.label("CounterpartyCoverage")
  //
  //    val map = mutable.HashMap(
  //      "cpkeyRepId" -> "ID", "cp_key" -> "CounterpartyID", "repId" -> "RepID", "salesName" -> "SalesName")
  //
  //    for ((k, v) <- map) {
  //      println(s"Key = $k Value = $v")
  //    }
  //
  //    CSV(path, 1000, ',', Some(map)) {
  //      (line, fields, record) =>
  //        val node = db.createNode(label)
  //        copyRecordToNode(classOf[CounterpartySalesCoverageBean], node, fields, record)
  //    }
  //  }
  //
  //
  //  val securityMap = new mutable.HashMap[String, Long]()
  //
  //  def processCmoSecurities(path: File, db: GraphDatabaseService): Int = {
  //
  //    val label = DynamicLabel.label("Security")
  //
  //    // Map Field Names
  //    val map = mutable.HashMap(
  //      "ESMP" -> "ESMID", "CSP" -> "CUSIP"
  //    )
  //
  //
  //    CSV(path, 100000, '|', Some(map)) {
  //      (line, fields, record) =>
  //        val node = db.createNode(label)
  //        val bean = new CmoBean()
  //        copyRecordToNode(bean, node, fields, record)
  //        println(s"CMO is ${bean}")
  //      //securityMap.put(securityID, node.getId)
  //    }
  //  }
  //
  //  def processPoolSecurities(path: File, db: GraphDatabaseService): Int = {
  //    val label = DynamicLabel.label("Security")
  //    val map = mutable.HashMap(
  //      "ESMP" -> "ESMID", "CSP" -> "CUSIP"
  //    )
  //
  //    CSV(path, 100000, '|', Some(map)) {
  //      (line, fields, record) =>
  //
  //
  //        val node = db.createNode(label)
  //        val bean = new PoolBean()
  //        copyRecordToNode(bean, node, fields, record)
  //
  //      //
  //      //
  //
  //
  //      //ecurityMap.put(securityID, node.getId)
  //    }
  //  }
  //
  //  def processTradeHistory(path: File, db: GraphDatabaseService): Int = {
  //    val label = DynamicLabel.label("Trade")
  //
  //    val map = mutable.HashMap(
  //      "SecurityId" -> "ESMID"
  //    )
  //
  //    CSV(path, 100000) {
  //      (line, fields, record) =>
  //        val node = db.createNode(label)
  //        copyRecordToNode(classOf[TradeBean], node, fields, record)
  //
  //        //
  //        // Replace Security ID with ESM ID
  //        //
  //        // ESMID maps to SecurityId in the input file. The value of this field in the input file
  //        // is a Winfits ID. Use Cusip or pool number to find the corresponding ID from the security Map.
  //        //
  //        val cusip = node.getProperty("CUSIP").asInstanceOf[String]
  //        val pool = node.getProperty("ESMID").asInstanceOf[String]
  //
  //        val nodeID = securityMap.get(cusip) orElse securityMap.get(pool)
  //
  //        nodeID match {
  //          case Some(id) =>
  //            val securityNode = db.getNodeById(id)
  //            node.createRelationshipTo(securityNode, Relationships.PRODUCT)
  //          case None =>
  //            println(s"Security not found: Cusip = $cusip PoolNum = $pool)")
  //        }
  //    }
  //  }
  //
  //
  //  case class Mapping(val column: String, val property: String)
  //
  //  def CSV(path: File,
  //          interval: Int,
  //          fieldMap: Array[Mapping],
  //          delimiter: Char = '~')
  //         (block: (Int, Array[Mapping], Array[String]) => Unit): Int = {
  //
  //    require(path.exists(), s"[${path}] not found")
  //
  //    val fstrm =
  //      if (path.getName.endsWith(".zip")) {
  //        val zip = new ZipFile(path)
  //        val entry = zip.entries.find(e => e.getName.endsWith(".csv"))
  //        require(entry.isDefined, s"Could not find a CSV file in Zipfile ${path.getCanonicalPath}")
  //        zip.getInputStream(entry.get)
  //      }
  //      else
  //        new FileInputStream(path)
  //
  //    val reader = new CSVReader(new InputStreamReader(fstrm), delimiter, '\000')
  //
  //    val header: Array[String] = reader.readNext()
  //
  //    println(s" ======== >> Processing ${path.getName}")
  //    println(s"There are ${header.size} header columns in each input line")
  //    println(s"There are ${fieldMap.size} fields in the field map")
  //
  //    require(header.size == fieldMap.size, "The fieldMap must define a mapping for each field in the CSV file.")
  //    println()
  //    println("Field Mapping { ")
  //    println("   CSV File         -> Node Property ")
  //    for (f <- fieldMap) {
  //      mapping: Mapping =>
  //        println(s"  ${mapping.column} -> ${mapping.property}")
  //    }
  //    println("}")
  //
  //    val startTime = new Date().getTime
  //
  //    var line = 1
  //    Stream.continually(reader.readNext).takeWhile(record => (record ne null) && (record.repr ne null)).foreach {
  //      record =>
  //        //
  //        //Invoke the lambda function which processes a single record in the CSV file
  //        //
  //        block(line, fieldMap, record)
  //
  //        //
  //        // Every "interval" rows, print time and processing stats.
  //        //
  //        if (line % interval == 0) {
  //          val now = new Date().getTime()
  //          val msSinceStart: Double = now - startTime
  //          val rate: Double = (msSinceStart / line) * 1000.0
  //          val rt = Runtime.getRuntime
  //          print(f"Read $line%10d records. Total Time = ${msSinceStart / 1000.0}%.2f secs. Time per Record = ${rate}%.2f ns.")
  //          println(f" Memory (free/total) ${rt.freeMemory() / 1000000.0}%.2f/${rt.totalMemory() / 1000000.0}%.2f ")
  //        }
  //
  //        line = line + 1
  //    }
  //
  //    reader.close()
  //    line
  //  }
  //
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







