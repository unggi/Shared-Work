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

  def addSubScope(subScope: NestedScope): NestedScope = {
    subScopes.append(subScope)
    subScope.parent = Some(this)
    this
  }

  def print(depth: Int): Unit = {
    val indent = " " * (depth * 4)
    println(s"$indent${getClass.getSimpleName} (${symbols.size}) ")
    symbols.foreach {
      case (name, symbol) =>
        println(s"$indent   $name => ${symbol.getClass.getSimpleName}")
      case unknown =>
        println("Unhandled entry type = " + unknown)
    }

    for (subScope <- subScopes) {
      subScope.print(depth + 1)
    }
  }

}

class GlobalScope() extends NestedScope(None)

class LocalScope(parentScope: NestedScope) extends NestedScope(Some(parentScope))

class CollectionMemberScope(parentScope: NestedScope) extends NestedScope(Some(parentScope))

class MatchScope(parentScope: NestedScope) extends NestedScope(Some(parentScope))

