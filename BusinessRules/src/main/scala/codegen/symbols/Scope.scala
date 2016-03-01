package codegen.symbols

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

trait Scope {

  def declare(entry: Symbol): Option[Symbol]

  def resolve(name: String): Option[Symbol]

  def hasDefined(name: String): Boolean

}

abstract class NestedScope(var parent: Option[NestedScope] = None) extends Scope {

  private val symbols = new mutable.HashMap[String, Symbol]()
  private val subScopes = new ListBuffer[NestedScope]()

  override def declare(symbol: Symbol): Option[Symbol] = {
    assume(!symbols.contains(symbol.name), "Symbol already defined in the same scope")
    symbols.put(symbol.name, symbol)
  }

  override def hasDefined(name: String): Boolean =
    symbols.contains(name)

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

  def keys = symbols.keySet

  def find[T <: NestedScope](cls: Class[T], start: NestedScope): Option[T] =
    if (start.getClass == cls)
      Some(start.asInstanceOf[T])
    else if (start.parent.isDefined)
      find[T](cls, start.parent.get)
    else
      None


  // Walk back to a containing scope which is a rule scope and find the model reference there.
  def resolveImplicitParameter(): Option[Symbol] =
    find(classOf[CollectionMemberScope], this) match {
      case Some(collectionScope) => collectionScope.collectionSymbol
      case None =>
        find(classOf[MatchScope], this) match {
          case Some(matchScope) => Some(matchScope.parameter)
          case None =>
            None
        }
    }

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
        println(s"$indent    ${symbol.getClass.getSimpleName} : ${symbol}")
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
  val parameter = new ModelParameterSymbol(modelParameterName, modelParameterType)

  declare(parameter)

  override def descriptor = super.descriptor + " " + modelParameterName

}

class CollectionMemberScope(parentScope: NestedScope) extends NestedScope(Some(parentScope)) {

  var collectionSymbol: Option[Symbol] = None

  override def descriptor = s"CollectionMemberScope (_ , ${collectionSymbol.get})"
}

