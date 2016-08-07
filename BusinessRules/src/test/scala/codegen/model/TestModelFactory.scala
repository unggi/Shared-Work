package codegen.model

import java.io._
import java.nio.file.Files

import org.scalatest.FlatSpec


/**
  * Created by unggi on 7/30/16.
  */
class TestModelFactory extends FlatSpec {

  var snapshot: Snapshot = _

  override def withFixture(test: NoArgTest) = {
    snapshot = new Snapshot("out.puml")
    if (snapshot.exists) snapshot.remove
    try
      test()
    finally {
      // Remove the snapshot file
    }
  }


  "A Model" should "render as a Plant UML file" in {
    val model = ModelFactory.create("SampleTradeModel")

    val fostrm = new FileOutputStream("out.puml")
    val ppw = new PrintWriter(fostrm)

    model.renderPUML(ppw)
    ppw.close()
    fostrm.close()
  }


  "A Snapshot" should "not exist before being snapped" in {

    assert(!snapshot.exists)
  }

  it should "exist after taking a snapshot" in {
    snapshot.snap
    assert(snapshot.exists)
  }

  it should "compare successfully to the current snapshot" in {
    snapshot.snap
    assert(snapshot.exists)
    assert(snapshot.compare)
  }
}

class Snapshot(filename: String) {

  val PREFIX = "snapshot-"
  val snapshot = new File(PREFIX + filename)
  val generated = new File(filename)

  def exists: Boolean = snapshot.exists

  def snap = Files.copy(generated.toPath, snapshot.toPath)

  def compare: Boolean = {

    val snapStrm = new LineNumberReader(new BufferedReader(new FileReader(snapshot)))
    val genStrm = new LineNumberReader(new BufferedReader(new FileReader(generated)))
    var break = false
    while (!break) {
      val snapLine = snapStrm.readLine()
      val genLine = genStrm.readLine()

      break = snapLine.equals(genLine)
    }
    break
  }

  def remove = Files.deleteIfExists(snapshot.toPath)


}
