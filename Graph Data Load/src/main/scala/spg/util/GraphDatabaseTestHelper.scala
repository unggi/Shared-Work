package spg.util

import java.io.File

import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.unsafe.batchinsert.BatchInserters
import org.scalatest.{Suite, BeforeAndAfterAll}

trait GraphDatabaseTestHelper extends BeforeAndAfterAll {

  self: Suite =>

  val DATABASE_PATH = "target/" + getClass().getSimpleName()

  val databaseDir = new File(DATABASE_PATH)

  var database: GraphDatabaseService = _

  override protected def beforeAll(): Unit = {

    super.beforeAll()
    removeDatabase(new File(DATABASE_PATH))
    database = createBatchInserter(new File(DATABASE_PATH))

  }

  override protected def afterAll(): Unit = {

    super.afterAll()
    database.shutdown()

  }

  /**
   *
   * @param f - the file representing the root of the database to be removed.
   */
  def removeDatabase(f: File) {
    if (f.isDirectory) {
      f.listFiles.foreach(removeDatabase(_))
    }
    f.delete()
  }

  /**
   * Create an inserter "pseudo-GraphDatabase" used for rapid insertion.
   *
   * @param path
   * @return
   */
  def createBatchInserter(path: File): GraphDatabaseService = {
    //
    // Delete the old database first.
    //
    removeDatabase(path)
    //
    // Create a pseudo database which is used to do batch inserts.
    //
    BatchInserters.batchDatabase(path.getCanonicalPath)
  }
}
