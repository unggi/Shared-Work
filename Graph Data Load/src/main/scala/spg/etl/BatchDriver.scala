package spg.etl

import java.io.PrintWriter
import java.util.Date


trait BatchDriver[Norm, Data] {

  val pw: PrintWriter = new PrintWriter(System.out)

  def source: DataSource[Norm]

  def normalizer: DataTransformation[Norm]

  def target: DataTarget[Norm]

  def runBatch(): Unit =
    while (source.hasMore) {
      val normalized = normalizer(source.next)
      target.consume(normalized)
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
