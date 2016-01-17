package codegen.symbols

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

trait LexicalScope {
  def declare(name: String, entry: Entry): Option[Entry]

  def resolve(name: String): Option[Entry]
}

abstract class NestedScope(val parent: Option[NestedScope] = None) extends LexicalScope {

  val map = new mutable.HashMap[String, Entry]()
  val subScopes = new ListBuffer[NestedScope]()

  override def declare(name: String, entry: Entry): Option[Entry] = map.put(name, entry)

  override def resolve(name: String): Option[Entry] = map.get(name)

  def addSubScope(child: NestedScope): Unit = {
    require(child.parent.isEmpty || child.parent.get.equals(this))
    require(!subScopes.contains(child))
    subScopes.append(child)
  }

  def resolveFromContextScope(name: String): Option[Entry]

}

class GlobalScope() extends NestedScope(None) {
  override def resolveFromContextScope(name: String): Option[Entry] = map.get(name)
}

class ContextScope(parentScope: NestedScope, val alias: Entry) extends NestedScope(Some(parentScope)) {

  map.put(alias.name, alias)

  // This scope is a context
  // Lookup the name directly in this context or answer the only member of the context which must be an alias.
  override def resolveFromContextScope(name: String): Option[Entry] =
    Some(map.getOrElse(name, alias))

}

class CollectionMemberScope(parentScope: NestedScope) extends NestedScope(Some(parentScope)) {

  // This is a scope in which an iteration variable is defined.
  override def resolveFromContextScope(name: String): Option[Entry] =
    map.get(name) match {
      case Some(entry) => Some(entry)
      case None => parent.get.resolveFromContextScope(name)
    }

}

class SymbolTable(verbose: Boolean = false) extends LexicalScope {
  val global: GlobalScope = new GlobalScope()

  var scope: NestedScope = global

  override def declare(name: String, entry: Entry): Option[Entry] = scope.declare(name, entry)

  override def resolve(name: String): Option[Entry] = scope.resolve(name)

  def openContextScope(entry: Entry): Unit = trace("Open Context") {
    val s = new ContextScope(scope, entry)
    scope.addSubScope(s)
    scope = s
  }

  def closeContextScope(): Unit = trace("Close Context") {
    scope = scope.parent.getOrElse(global)
  }

  def openConstraintScope(): Unit = trace("Open Constraint") {

    val s = new CollectionMemberScope(scope)
    scope.addSubScope(s)
    scope = s
  }

  def closeConstraintScope(): Unit = trace("Close Constraint") {
    scope = scope.parent.getOrElse(global)
  }

  def openCollectionMemberScope(): Unit = trace("Open Collection Member") {
    val s = new CollectionMemberScope(scope)
    scope.addSubScope(s)
    scope = s
  }

  def closeCollectionMemberScope(): Unit = trace("Close Collection Member") {
    scope.parent.getOrElse(global)
  }

  def trace[T](msg: String)(body: => T): T = {
    val result: T = body
    if (verbose)
      println(s"$msg: ${identity(result)}")
    result
  }

  def print(): Unit = {
    println("Symbol Table")
    print(global, 4)
  }

  def print(s: NestedScope, depth: Int): Unit = {
    val indent = " " * (depth * 4)
    println(s"$indent${s.getClass.getSimpleName} (${s.map.size}) ")
    s.map.foreach {
      case (name: String, entry: ModelReferenceEntry) =>
        println(s"$indent   $name => ${entry.getClass.getSimpleName}")
      case unknown =>
        println("Unhanded entry type = " + unknown)
    }

    for (ss <- s.subScopes) {
      print(ss, depth + 1)
    }
  }
}

abstract class Entry(val name: String)

case class ModelReferenceEntry(n: String, components: List[String]) extends Entry(n)

case class ValidationRuleEntry(n: String) extends Entry(n)

case class DefinedTermEntry(n: String) extends Entry(n)

case class LocalVariable(n: String) extends Entry(n)