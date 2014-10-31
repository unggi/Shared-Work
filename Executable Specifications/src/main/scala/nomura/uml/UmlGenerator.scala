package nomura.uml

import java.io.{File, PrintWriter}

import scala.collection._

class StateVisitor(model: CompositeState) {

  def visit() {
    model.states.values.foreach(visitState(_))
  }

  def visitState(s: State) {
    s match {
      case c: CompositeState => visitCompositeState(c)
      case i: InitialState => visitInitialState(i)
      case f: FinalState => visitFinalState(f)
      case state: State => visitActionState((state))
      case x =>
        println("Unknown State Type " + x)
    }
  }

  def visitActionState(s: State) {

  }

  def visitCompositeState(c: CompositeState) {
    c.states.values.foreach(visitState(_))
  }

  def visitInitialState(i: InitialState) {

  }

  def visitFinalState(f: FinalState) {

  }

}

class TransitionVisitor(model: CompositeState) {


  var visited = new mutable.HashMap[String, State]

  def visit() {
    visitState(model.start)
  }

  def visitState(s: State) {
    s match {
      case c: CompositeState => visitCompositeState(c)
      case state: State => visitStateTransitions(state)
      case x =>
        println("Unknown State Type " + x)
    }
  }

  def visitCompositeState(c: CompositeState) {
    visitStateTransitions(c)
    visitState(c.start)
  }

  def visitStateTransitions(s: State) {
    if (!visited.contains(s.name)) {
      visited.put(s.name, s)
      s.transitions.foreach {
        case (event, target) =>
          visitTransition(s, target, event)
          visitState(target)
      }

    }
  }

  def visitTransition(from: State, to: State, on: Class[_]) {
  }

}


class UmlGenerator(stateModel: StateModel, docPath: String) {

  def generate(pw: PrintWriter) {
    new UmlStates(stateModel.modelRoot, pw).visit()
  }

  def normalizeStateName(s: String): String = s.replace(' ', '_')

  class UmlStates(model: CompositeState, pw: PrintWriter) extends StateVisitor(model) {

    val includedSkinParams = "Skin.iuml"

    override def visit() {

      val slash = File.separatorChar
      val b = new StringBuilder
      for (i <- 1 until docPath.substring(docPath.indexOf("diagrams" + slash)).count(_ == slash))
        b.append(".." + slash)

      pw.println("@startuml")

      pw.println(s"!include ${b.toString}/${includedSkinParams}")
      super.visit()
      new UmlTransitions(model, pw).visit()
      pw.println("@enduml")
    }

    override def visitActionState(s: State) {
      val stateType =
        if (s.hasCompletedTransition)
          "ACTION_STATE"
        else
          "WAIT_STATE"
      val stereoType =
        if (s.hasCompletedTransition)
          "<<ACTION>>"
        else
          "<<WAIT>>"

      pw.println( s"""state "${stateType} ${s.name}" as ${normalizeStateName(s.name)} ${stereoType}""")

      super.visitActionState(s)
    }

    override def visitCompositeState(c: CompositeState) {
      pw.println( s"""state "${c.name}" as ${normalizeStateName(c.name)} {""")
      super.visitCompositeState(c)
      new UmlTransitions(c, pw).visit()
      pw.println("}")
    }

  }

  class UmlTransitions(model: CompositeState, pw: PrintWriter) extends TransitionVisitor(model) {

    override def visitTransition(from: State, to: State, on: Class[_]) =

      if (from.parent.isDefined && from.parent.get == model)
        on match {
          case event: Class[_] if event.getSimpleName.equals("Completed") =>
            pw.println(s"${normalizeStateName(from.name)} --> ${normalizeStateName(to.name)}")
          case _ =>
            pw.println(s"${normalizeStateName(from.name)} --> ${normalizeStateName(to.name)} : MESSAGE ${on.getSimpleName}")
        }


  }

}
