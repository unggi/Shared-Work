package techfn.cashflows

import scala.collection.mutable.ArrayBuffer

//
// A cashflow series is an immutable series of Cashflow instances.
//
class CashflowSeries(generator: () => ArrayBuffer[Cashflow]) extends Seq[Cashflow] {
  private val series: Array[Cashflow] = generator().toArray

  override def iterator: Iterator[Cashflow] = series.iterator

  override def apply(idx: Int): Cashflow = series.apply(idx)

  override def length: Int = series.length

  override def reverseIterator: Iterator[Cashflow] = series.reverseIterator


}
