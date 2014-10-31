package nomura.uml

import scala.collection._
import java.io.PrintWriter

object StateModelRegistry {

  val models = new mutable.HashMap[String, StateModel]()

  def register(key: String, model: StateModel) = models.put(key, model)

  def remove(key: String) = models.remove(key)

  def find(key: String): Option[StateModel] = models.get(key)

  def dump(key: String, pw: PrintWriter) {
    val generator = new UmlGenerator(find(key).get, key)

    generator.generate(pw)
    pw.flush()
  }
}
