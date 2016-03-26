package codegen.symbols

import scala.collection.mutable.ArrayBuffer


abstract class Symbol(val name: String) {
  def asComponents: Array[String] = Array(name)
}

case class ModelReferenceSymbol(override val name: String, components: List[String]) extends Symbol(name) {

  var scope: Option[NestedScope] = None

  override def toString: String = s"${getClass.getSimpleName}($name, ${components.mkString(", ")})"

  override def asComponents: Array[String] = {

    assume(scope.isDefined, "Model Reference Symbol should have been assigned a scope: " + toString)

    val array = new ArrayBuffer[String]()
    //
    // Resolve the root object in the current scope
    //
    val root =
      scope.get.resolve(name) match {
        case Some(symbol) =>
          System.err.println(s"Found Symbol through resolve(${name}) in scope ${scope.get.getClass.getSimpleName} <- ${scope.get.keys.mkString(" - ")}")
          symbol.name
        case None =>
          scope.get.resolveImplicitParameter() match {
            case Some(symbol) =>
              System.err.println(s"Found Symbol through implicit(${name} in scope ${scope}")
              symbol.name
            case None =>
              // Not able to resolve - should not happen
              assume(false, "Symbol not able to be resolved: " + name)
              "NOT FOUND"
          }
      }
    array.append(root)
    array.appendAll(components.tail)
    array.toArray
  }
}

case class ValidationRuleSymbol(override val name: String) extends Symbol(name)

case class DefinedTermSymbol(override val name: String) extends Symbol(name)

case class LocalVariable(override val name: String) extends Symbol(name)

case class ModelParameterSymbol(override val name: String, reference: ModelReferenceSymbol) extends Symbol(name)

case class CollectionIndexSymbol(override val name: String, reference: Symbol) extends Symbol(name)

