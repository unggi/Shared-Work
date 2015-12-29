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

  def resolveInContextScope(name: String): Option[Entry] = {


    val result = this match {
      case ctx: ContextScope =>
        // This scope is a context
        // Lookup the name directly in this context or answer the only member of the context which must be an alias.
        map.get(name) match {
          case Some(scope) => Some(scope)
          case None => Some(ctx.map.values.toIterator.next)
        }
      case ctx: GlobalScope =>
        map.get(name)
      case ctx: NestedScope if parent.isDefined =>
        map.get(name) match {
          case Some(entry) => Some(entry)
          case None => parent.get.resolveInContextScope(name)
        }
      case unknown =>
        System.err.println(s"Unknown case in resolveInContextScope($name)")
        None
    }
    result match {
      case Some(x) =>
        System.err.println("Resolve in Context Scope: " + name + " found " + result.get.name)
      case None =>
        System.err.println("Not found")
    }
    result

  }
}

class GlobalScope() extends NestedScope(None)

class ContextScope(parentScope: NestedScope) extends NestedScope(Some(parentScope))

class ConstraintScope(parentScope: NestedScope) extends NestedScope(Some(parentScope))

class SymbolTable(verbose: Boolean = false) extends LexicalScope {
  val global: GlobalScope = new GlobalScope()

  var scope: NestedScope = global

  override def declare(name: String, entry: Entry): Option[Entry] = scope.declare(name, entry)

  override def resolve(name: String): Option[Entry] = scope.resolve(name)

  def openContextScope(): Unit = trace("Open Context") {
    val s = new ContextScope(scope)
    scope.addSubScope(s)
    scope = s
  }

  def closeContextScope(): Unit = trace("Close Context") {
    scope = scope.parent.getOrElse(global)
  }

  def openConstraintScope(): Unit = trace("Open Constraint") {

    val s = new ConstraintScope(scope)
    scope.addSubScope(s)
    scope = s
  }

  def closeConstraintScope(): Unit = trace("Close Constraint") {
    scope = scope.parent.getOrElse(global)
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