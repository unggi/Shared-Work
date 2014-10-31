package spg.etl

trait DataTransformation[Norm] {

  def apply(record: Norm): Norm

}
