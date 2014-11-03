package spg.etl

import java.io.PrintWriter
import java.util.Date


trait ETLTask[Norm] {

  val pw: PrintWriter = new PrintWriter(System.out)
  val startTime = new Date().getTime
  var currentLineNumber = 0

  def progressInterval: Int = 10000

  def source: DataSource[Norm]

  def transformer: DataTransformation[Norm]

  def target: DataTarget[Norm]

  def runBatch(): Unit =
    while (source.hasMore) {
      val normalized = transformer(source.next)
      target.consume(normalized)
      if (currentLineNumber % progressInterval == 0)
        printProgressMessage(startTime, currentLineNumber)

      currentLineNumber = currentLineNumber + 1
    }

  def runRecordByRecord(): Unit = {}

  def printProgressMessage(startTime: Long, count: Int): Unit = {

    val now = new Date().getTime()
    val msSinceStart: Double = now - startTime
    val rate: Double = (msSinceStart / count) * 1000.0
    val rt = Runtime.getRuntime
    pw.print(f"Read $count%10d records. Total Time = ${msSinceStart / 1000.0}%.2f secs. Time per Record = ${rate}%.2f ns.")
    pw.println(f" Memory (free/total) ${rt.freeMemory() / 1000000.0}%.2f/${rt.totalMemory() / 1000000.0}%.2f ")
    pw.flush()
  }
}
