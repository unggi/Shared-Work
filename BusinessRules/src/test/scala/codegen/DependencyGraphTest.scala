package codegen

import java.io.StringWriter
import java.util.{Calendar, Date}

import org.scalatest.{Matchers, FlatSpec, FunSuite}
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

    addDependency(a -> d)
    addDependency(a -> e)
    addDependency(b -> e)

    val f = new VariableNode("F")
    val g = new VariableNode("G")

    addVariables(f, g)

    addDependency(f -> d)
    addDependency(g -> e)

    val h = new OutputNode("H")
    val i = new OutputNode("I")

    addOutputs(h, i)

    addDependency(d -> h)
    addDependency(e -> i)
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
    graph.addDependency(j -> k)
  }



}
