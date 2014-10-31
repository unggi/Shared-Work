package spg

import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.scalatest.{Suite, BeforeAndAfterAll, Matchers, FlatSpec}
<<<<<<< HEAD
import spg.etl.Main
=======
>>>>>>> parent of 9f7f0df... Refreshing

trait GraphDatabaseSuite extends BeforeAndAfterAll {
  suite: Suite =>

  var db : GraphDatabaseService = _

  override def beforeAll() {
<<<<<<< HEAD
    db = new GraphDatabaseFactory().newEmbeddedDatabase(Main.DB_PATH)
=======
    db = new GraphDatabaseFactory().newEmbeddedDatabase(DataLoader.DB_PATH)
>>>>>>> parent of 9f7f0df... Refreshing
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
