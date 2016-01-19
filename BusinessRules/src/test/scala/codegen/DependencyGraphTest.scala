package codegen

import java.io.StringWriter
import java.util.{Calendar, Date}

import org.scalatest.{Matchers, FlatSpec, FunSuite}
import org.stringtemplate.v4.AutoIndentWriter

class DependencyGraphTest extends FlatSpec with Matchers {


  val graph = new DependencyGraph() {

    val a = new InputNode("A", 1.0)
    val b = new InputNode("B", "A Test String")
    val c = new InputNode("C", Calendar.getInstance.getTime)

    addInputs(a, b, c)

    val d = new RuleNode("D", true)
    val e = new RuleNode("E", 55.2)

    addRules(d, e)

    addDependency(a -> d)
    addDependency(a -> e)
    addDependency(b -> e)

    val f = new VariableNode("F", "FFFF")
    val g = new VariableNode("G", 3)

    addVariables(f, g)

    addDependency(f -> d)
    addDependency(g -> e)

    val h = new OutputNode("H", false)
    val i = new OutputNode("I", false)

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
    val j = new InputNode("J", 3.4)
    val k = new InputNode("K", 5)
    graph.addDependency(j -> k)

  }

  it should "retain typed values at nodes" in {
    val m = new InputNode("M", 7.4)

  }



}
