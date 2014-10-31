package nomura.util

import akka.event.LoggingAdapter

object Tracer {

  var depth = 0
  val indent = 2

  def TRACER[T](s: String)(body: => T) {

    for (i <- 0 until depth) System.out.print("  ")
    System.out.println(s"====> $s")
    depth = depth + indent
    try {
      body
    } finally {
      depth = depth - indent
      for (i <- 0 until depth) System.out.print("  ")
      System.out.println(s"<==== $s")

    }
  }

  def VAR[T](name: String, log: LoggingAdapter, value: => String)(body: => T) {
    val tab = " "
    var padding = ""
    for (i <- 0 until depth) padding += tab
    log.info(s"${padding} ====> ${name} = ${value}")
    depth = depth + indent
    try {
      body
    } finally {
      depth = depth - indent
      log.info(s"${padding.substring(0, depth)} ====<  ${name} = ${value}")
    }
  }

}
