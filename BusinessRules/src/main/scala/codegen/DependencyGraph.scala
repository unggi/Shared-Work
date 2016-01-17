package codegen

class DependencyGraph {

  /**
   *
   * A simple dependency graph representation to derive rule calculation paths.
   * This is not a generalized graph. It assumes:
   * <li> There are input nodes which are model references into the input document.
   * <li> A rule can produce variables and set them to values which may be inputs
   * to other rules.
   * <li> There are rule nodes which take in inputs of either model references or variables.
   * <li> The graph begins with input nodes which are model references and these have no predecessors.
   * <li> The graph "fires" or propagates from these nodes to the furthest most rule which has no outputs
   * or fails to validate.
   *
   **/

  trait Node {

  }

  case class ReferenceNode() extends Node {

  }

  case class RuleNode() extends Node {

  }

  case class VariableNode() extends Node {

  }

  def addModelReference(reference: ReferenceNode): Unit = {

  }

  def addDefinition(): Unit = {

  }
}
