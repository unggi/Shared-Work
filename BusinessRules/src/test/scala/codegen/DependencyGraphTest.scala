package codegen

import java.io.{PrintWriter, StringWriter}

import org.scalatest.{FlatSpec, Matchers}
import org.stringtemplate.v4.AutoIndentWriter

class DependencyGraphTest extends FlatSpec with Matchers {

  import DependencyGraph._

  val graph = new DependencyGraph() {

    val a = new InputNode("A")
    val b = new InputNode("B")
    val c = new InputNode("C")

    addInputs(a, b, c)

    val d = new RuleNode("D")
    val e = new RuleNode("E")

    addRules(d, e)

    val f = new VariableNode("F")
    val g = new VariableNode("G")

    addVariables(f, g)

    val h = new FactNode("H")
    val i = new FactNode("I")

    addFacts(h, i)

    a -> d
    d -> i
    a -> e -> b -> e

    f -> d
    g -> e
    d -> h
    e -> i

  }

  "The test graph" should "render as defined" in {

    val sw = new StringWriter()
    val iw = new AutoIndentWriter(sw)

    graph.render(iw)

    println(sw.getBuffer.toString)
    assert(true)
  }

  it should "dependency implies successor relationship" in {
    val j = new InputNode("J")
    val k = new InputNode("K")
    graph.addInputs(j, k)
    j -> k
    assert(j.isSuccessor(k))
  }

  "The test graph" should "render UML as defined" in {

    val sw = new StringWriter()

    graph.toUml(new PrintWriter(sw))

    println(sw.getBuffer.toString)
    assert(true)
  }


}
