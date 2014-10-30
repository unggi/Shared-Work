package spg

import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.scalatest.{Suite, BeforeAndAfterAll, Matchers, FlatSpec}
import spg.etl.Main

trait GraphDatabaseSuite extends BeforeAndAfterAll {
  suite: Suite =>

  var db : GraphDatabaseService = _

  override def beforeAll() {
    db = new GraphDatabaseFactory().newEmbeddedDatabase(Main.DB_PATH)
  }

  override def afterAll() {
    db.shutdown()
  }

  def withDatabase(testCode: GraphDatabaseService => Any) {
    testCode(db)
  }
}

class TestGraphLoad extends FlatSpec with Matchers with GraphDatabaseSuite {

  behavior of "Graph Data Load"

  it should "Check the database has at least 900,000 records" in withDatabase {
    db =>

  }


}
