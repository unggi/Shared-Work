package nomura.uml

import java.io.{File, FileOutputStream, PrintWriter}

import nomura.uml.LifeCycleEvents.Completed

import scala.collection._


trait StateModel {

  type StateStack = mutable.Stack[CompositeState]

  /**
   * Stack is used to hold traversal state as a machine is build or as a machine is executed.
   */
  val stack = new StateStack()
  /**
   * The model root is the top node of the whole model but the initial model
   */
  var modelRoot: CompositeState = null


  /**
   * Create Plant UML rendering of this state machine.
   * @param name The base name of the output file for rendering. The output will be "name.puml".
   */
  def generateUml(name: String) {

    val path = new File("diagrams/" + getClass().getPackage.getName.replace('.', '/') + "/" + name + ".puml")

    if (path.exists)
      path.delete()


    if (!path.getParentFile.exists)
      path.getParentFile.mkdirs()

    val fstrm = new FileOutputStream(path)
    val pw = new PrintWriter(fstrm)

    new UmlGenerator(this, path.getCanonicalPath).generate(pw)
    pw.flush()
    pw.close()
    fstrm.close()
  }

  def model(name: String)(body: => Unit) {
    StateModelRegistry.find(name) match {
      case Some(model) =>
        modelRoot = model.modelRoot
      case None =>
        // Create a new state model and add it to the stack without a parent
        val state = new CompositeState(name, None)
        stack.push(state)
        body
        stack.pop()
        modelRoot = state
        StateModelRegistry.register(name, this)
        generateUml(name)
    }
    require(modelRoot != null)
  }

  def state[T](name: String)(onEntryAction: (T) => Unit) = {
    // Create a new state and add it to the parent
    val state = new ActionState[T](name, Some(stack.top))(onEntryAction)
    stack.top.states.put(name, state)

  }

  def composite(name: String)(body: => Unit) = {

    // Create a new state model and add it to the caller
    val state = new CompositeState(name, Some(stack.top))
    stack.top.states.put(name, state)
    stack.push(state)
    require(state.findState(CompositeState.START_STATE).isDefined, s"Did not find start state")
    body
    stack.pop()

  }

  def flow() = new {

    class ToClause() {
      var fromState: Option[State] = None

      def to(toState: State) = addTransition(fromState.get, toState, classOf[Completed])

      def to(toStateName: String) = {
        val toState = stack.top.findState(toStateName)
        fatal(toState.isDefined, s"To State not found <$toStateName>")

        addTransition(fromState.get, toState.get, classOf[Completed])
      }

      def to(finalState: FinalState) = {
        val myCompositeFinalNode = stack.top.terminal
        addTransition(myCompositeFinalNode, finalState, classOf[Completed])
      }
    }

    def from(fromStateNode: State) = new ToClause() {
      fromState = Some(fromStateNode)
    }

    def from(fromStateName: String) = new ToClause() {
      require(!stack.isEmpty)

      fromState = stack.top.findState(fromStateName)
      fatal(fromState.isDefined, s"Flow from State - from state not found <$fromStateName>")
    }

    def from(fromStateNode: InitialState) = new ToClause {
      require(!stack.isEmpty)
      fromState = Some(stack.top.start)
    }
  }

  def transition() = new {

    def from(fromState: InitialState) = new {
      require(!stack.isEmpty)
      val myCompositeStartNode = stack.top.start

      def to(t: String) = new {
        val toState = stack.top.findState(t)
        fatal(toState.isDefined, s"TRANSITION from ${myCompositeStartNode.name} - to State not found <$t>")

        def on(event: Any) = addTransition(myCompositeStartNode, toState.get, findTypeName(event))

      }
    }

    def from(f: String) = new {

      require(!stack.isEmpty)

      val fromState = stack.top.findState(f)
      fatal(fromState.isDefined, s"TRANSITION from State not found <$f>")

      def to(t: String) = new {
        val toState = stack.top.findState(t)
        fatal(toState.isDefined, s"TRANSITION from ${f} - to State not found <$t>")

        def on(event: Any) = addTransition(fromState.get, toState.get, findTypeName(event))
      }

      def to(toState: FinalState) = new {
        require(!stack.isEmpty)
        val myCompositeFinalNode = stack.top.terminal

        // TODO Suspicious - looks like the current state is ignored and this transition goes from the current final state to the parent final state.

        def on(event: Any) = addTransition(myCompositeFinalNode, toState, findTypeName(event))
      }
    }
  }

  protected def addTransition(from: State, to: State, on: Class[_]) = from.transition(to, on)

  protected def findTypeName(instance: Any): Class[_] =
    instance.getClass.getName match {
      case cls: String if cls.endsWith("$") =>
        val className = cls.dropRight(1)
        Class.forName(className)
      case cls: String =>
        Class.forName(cls)
      case _ =>
        /* Should never happen */
        null
    }


  def fatal(condition: Boolean, message: String) =
    if (condition == false) {
      System.err.println(s"FATAL ERROR in Model Definition <${getClass.getSimpleName}>: $message")
      System.exit(1)
    }

}



