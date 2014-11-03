package spg

import java.io._
import java.sql.Time
import java.util.Date
import java.util.zip.ZipFile

import au.com.bytecode.opencsv.CSVReader
import org.neo4j.graphdb._
import org.neo4j.unsafe.batchinsert.BatchInserters
import spg.datamodel._
import spg.util.FieldFormatter

import scala.collection.JavaConversions._
import scala.collection.mutable

// TODO Support for numeric data types in graph model
// TODO Support for date data types in graph model
// TODO Link counterparties to sales through coverage relation
// TODO Link trades to counterparties
// TODO Link trades to sales? Not sure if sales reps are in the trade object
// TODO Link trades to books

object DataLoader {

  val date = "20140721"
  val TRADE_HISTORY = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\mbs-batch\\data\\HistoricalTradesSolrExtract_" + date + ".txt"
  val CMO_SECURITIES = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\solr-security-feed\\data\\cmo." + date + ".zip"
  val POOL_SECURITIES = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\solr-security-feed\\data\\pool." + date + ".zip"
  val COUNTERPARTY_COVERAGE = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\mbs-batch\\data\\CounterpartySalesExtractForSolr_" + date + ".txt"
  val COUNTERPARTIES = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\mbs-batch\\data\\CounterpartyExtractForSolr_" + date + ".txt"
  val DB_PATH = "target/neo4j-test-loading-db"

  def main(args: Array[String]): Unit = {

    // Initialize Neo4J configuration
    //    createConfiguration()

    val db = createBatchInserter(DB_PATH)

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

    val cmoRecordCount = processCmoSecurities(cmo, db)
    println(s"Processed ${cmoRecordCount} CMO security records")

    val counterpartyCount = processCounterparties(counterparty, db)
    println(s"Processed ${counterpartyCount} Counterparty records.")

    val counterpartyCoverageRecordCount = processCounterpartySalesCoverage(countpartyCoverage, db)
    println(s"Processed ${counterpartyCoverageRecordCount} Counterparty records.")


    val poolRecordCount = processPoolSecurities(pool, db)
    println(s"Processed ${poolRecordCount} Pool security records")


    println(s"Loading  Trade History File\n\nHistory File Path = [${TRADE_HISTORY}]\n")
    val tradeRecordCount = processTradeHistory(history, db)
    println(s"Processed Trade ${tradeRecordCount} records")

    db.shutdown()
  }

  val IntClass = classOf[Int]
  val DoubleClass = classOf[Double]
  val StringClass = classOf[String]
  val DateClass = classOf[Date]
  val TimeClass = classOf[Time]

  def copyRecordToNode(beanClass: Class[_], node: Node, fields: IndexedSeq[String], record: Array[String]): Unit = {

    for (i <- 0 until fields.size) {
      val fieldName = fields(i)
      val textValue = record(i)
      if (textValue.length != 0) {

        val field = beanClass.getDeclaredField(fieldName)
        require(field != null, s"Field not found: bean = ${beanClass.getSimpleName} field = ${fieldName}")

        try {
          field.getType match {
            case IntClass =>
              val integer = FieldFormatter.asInteger(textValue)
              node.setProperty(fieldName, integer.get)
            case DoubleClass =>
              val dbl = FieldFormatter.asDouble(textValue)
              node.setProperty(fieldName, dbl.get)
            case DateClass =>
              val date = FieldFormatter.asDate(textValue)
              node.setProperty(fieldName, date.get.getTime)
            case TimeClass =>
              val time = FieldFormatter.asTime(textValue)
              node.setProperty(fieldName, time.get.getTime)
            case StringClass =>
              node.setProperty(fieldName, textValue)
            case x =>
              println(s"Unknown Field Type Class = $beanClass Field = $fieldName FieldType = ${x.getSimpleName} value = $textValue")
          }
        } catch {
          case e:Throwable => println(s"Exception for $beanClass field = $fieldName type = ${field.getType.getSimpleName} value ${e.getMessage}")
        }
      }
    }
  }

  def processCounterparties(path: File, db: GraphDatabaseService): Int = {
    val label = DynamicLabel.label("Counterparty")

    val map = mutable.HashMap[String, String](
      "account_id" -> "AccountID",
      "cp_key" -> "CPKey",
      "sname" -> "ShortName",
      "name" -> "Name",
      "recent" -> "Recent",
      "alias" -> "Alias"
    )

    for ((k, v) <- map) {
      println(s"Key = $k Value = $v")
    }

    CSV(path, 1000, ',', Some(map)) {
      (line, fields, record) =>
        val node = db.createNode(label)
        copyRecordToNode(classOf[Counterparty], node, fields, record)
    }
  }


  def processCounterpartySalesCoverage(path: File, db: GraphDatabaseService): Int = {
    val label = DynamicLabel.label("CounterpartyCoverage")

    val map = mutable.HashMap(
      "cpkeyRepId" -> "ID", "cp_key" -> "CounterpartyID", "repId" -> "RepID", "salesName" -> "SalesName")

    for ((k, v) <- map) {
      println(s"Key = $k Value = $v")
    }

    CSV(path, 1000, ',', Some(map)) {
      (line, fields, record) =>
        val node = db.createNode(label)
        copyRecordToNode(classOf[CounterpartySalesCoverage], node, fields, record)
    }
  }

  val securityMap = new mutable.HashMap[String, Long]()

  def processCmoSecurities(path: File, db: GraphDatabaseService): Int = {
    val label = DynamicLabel.label("Security")
    // Map Field Names
    val map = mutable.HashMap(
      "ESMP" -> "ESMID", "CSP" -> "CUSIP"
    )

    for ((k, v) <- map) {
      println(s"Key = $k Value = $v")
    }

    CSV(path, 100000, '|', Some(map)) {
      (line, fields, record) =>
        val node = db.createNode(label)
        copyRecordToNode(classOf[Cmo], node, fields, record)
        securityMap.put(node.getProperty("ESMID").asInstanceOf[String], node.getId)
    }
  }

  def processPoolSecurities(path: File, db: GraphDatabaseService): Int = {
    val label = DynamicLabel.label("Security")
    val map = mutable.HashMap(
      "ESMP" -> "ESMID", "CSP" -> "CUSIP"
    )

    CSV(path, 100000, '|', Some(map)) {
      (line, fields, record) =>


        val pool = new Pool()
        val node = db.createNode(label)
        copyRecordToNode(pool.getClass(), node, fields, record)
        securityMap.put(pool.CUSIP, node.getId)
    }
  }

  def processTradeHistory(path: File, db: GraphDatabaseService): Int = {
    val label = DynamicLabel.label("Trade")

    CSV(path, 100000) {
      (line, fields, record) =>
        val node = db.createNode(label)
        copyRecordToNode(classOf[Trade], node, fields, record)
      //        securityMap.get(bean.Cusip) match {
      //          case Some(id) =>
      //            node.createRelationshipTo(db.getNodeById(id), Relationships.PRODUCT)
      //          case None =>
      //            println(s"Security not found: ${bean.Cusip}")
      //        }
    }
  }

  def CSV(path: File,
          interval: Int,
          delimiter: Char = '~',
          fieldMap: Option[mutable.HashMap[String, String]] = None)
         (block: (Int, Array[String], Array[String]) => Unit): Int = {

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

    val fields =
      for (fld <- header if (fld.length > 0))
      yield
        if (fieldMap.isDefined)
          fieldMap.get.get(fld).getOrElse(fld)
        else
          fld

    println(s" ======== >> Processing ${path.getName}")
    println(s"There are ${header.size} header columns in each input line")
    println(s"There are ${fields.size} fields in the data object")

    println()
    println("Fields { ")
    for (f <- fields) {
      println(s"  ${f} ")
    }
    println("}")

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






