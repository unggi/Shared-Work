package codegen.symbols

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

trait Scope {

  def declare(entry: Symbol): Option[Symbol]

  def resolve(name: String): Option[Symbol]

}

abstract class NestedScope(var parent: Option[NestedScope] = None) extends Scope {

  private val symbols = new mutable.HashMap[String, Symbol]()
  private val subScopes = new ListBuffer[NestedScope]()

  override def declare(symbol: Symbol): Option[Symbol] = symbols.put(symbol.name, symbol)

  override def resolve(name: String): Option[Symbol] =
    symbols.get(name) match {
      case Some(symbol) =>
        Some(symbol)
      case None =>
        parent match {
          case Some(scope) => scope.resolve(name)
          case None => None
        }
    }

  // Default is to find in the usual path via parent links.
  def resolveImplicitParameter(name: String): Option[Symbol] = resolve(name)

  def addSubScope(subScope: NestedScope): NestedScope = {
    subScopes.append(subScope)
    subScope.parent = Some(this)
    this
  }

  def descriptor = s"${getClass.getSimpleName} (${symbols.size})"

  def print(depth: Int): Unit = {
    val indent = " " * (depth * 2)
    println(s"$indent$descriptor")
    symbols.foreach {
      case (name, symbol) =>
        println(s"$indent    $name => ${symbol}")
      case unknown =>
        println("Unhandled entry type = " + unknown)
    }

    for (subScope <- subScopes)
      subScope.print(depth + 1)

  }

}

class GlobalScope() extends NestedScope(None)

class LocalScope(parentScope: NestedScope) extends NestedScope(Some(parentScope))


case class MatchScope(parentScope: NestedScope, modelParameterName: String, modelParameterType: ModelReferenceSymbol) extends NestedScope(Some(parentScope)) {

  declare(new ModelParameterSymbol(modelParameterName, modelParameterType))

  // If the name argument isn't actually defined in the scope at any
  // point in the path back to the global scope, then use the parameter
  // defined in this scope.
  override def resolveImplicitParameter(name: String): Option[Symbol] =
    Some(resolve(name) match {
      case Some(symbol) => symbol
      case None => modelParameterType
    })

}

class CollectionMemberScope(parentScope: NestedScope) extends NestedScope(Some(parentScope)) {

  var collectionSymbol: Symbol = _
  var parameterName: String = _

  override def resolveImplicitParameter(name: String): Option[Symbol] =
    Some(resolve(name) match {
      case Some(symbol) => symbol
      case None => collectionSymbol
    })

  override def descriptor = s"CollectionMemberScope #$hashCode ($parameterName, $collectionSymbol)"
}

