package spg.etl

trait DataSource[Norm] {

  def initialize : Unit

  def hasMore: Boolean

  def next: Norm

}
