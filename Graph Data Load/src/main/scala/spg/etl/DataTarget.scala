package spg.etl

import scala.util.Try

trait DataTarget[Nrm] {

  def initialize()

  def consume(normalized: Nrm): Try[Nrm]

}
