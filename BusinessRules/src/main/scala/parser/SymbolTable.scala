package parser

import scala.collection.mutable.{HashMap, Map}

class SymbolTable[T](val name: String) {
  val map: Map[String, T] = new HashMap[String, T]()

  def put(key: String, entry: T): Option[T] = map.put(key, entry)

  def get(key: String): Option[T] = map.get(key)

  def contains(key: String) : Boolean = map.contains(key)
}
