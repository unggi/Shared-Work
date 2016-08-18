package codegen.symbols

import codegen.ParseTreeScopeAnnotations
import codegen.TreeUtilities._
import org.antlr.v4.runtime.tree.ParseTree

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

trait Scope {

  def declare(entry: Symbol): Option[Symbol]

  protected def resolve(name: String): Option[Symbol]

  def resolve(name: String, components: List[String]): Symbol

  def hasDefined(name: String): Boolean

}

abstract class NestedScope(var parent: Option[NestedScope] = None) extends Scope {

  val symbols = new mutable.HashMap[String, Symbol]()
  val subScopes = new ListBuffer[NestedScope]()

  override def declare(symbol: Symbol): Option[Symbol] = {
    assume(!symbols.contains(symbol.name), "Symbol already defined in the same scope")
    symbols.put(symbol.name, symbol)
  }

  override def hasDefined(name: String): Boolean =
    symbols.contains(name)

  override def resolve(base: String, components: List[String]): Symbol = {
    resolve(base) match {
      case Some(parameter: Parameter) =>
        new ParameterReference(parameter, components)
      case Some(any) =>
        require(false, s"Symbol was resolved to $any but should have been a Parameter")
        null
      case None =>
        //
        // Must be an implicit use of the parameter in the current rule scope
        //
        val ruleScope = find(classOf[RuleScope], this).get
        val parameter = ruleScope.modelParameterSymbol
        new ParameterReference(parameter, parameter.name :: components)
    }
  }

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


  def addSubScope(subScope: NestedScope): NestedScope = {
    subScopes.append(subScope)
    subScope.parent = Some(this)
    this
  }

  def descriptor = s"${getClass.getSimpleName} (${symbols.size})"

  def printAncestors(ctx: ParseTree, annotations: ParseTreeScopeAnnotations, depth: Int = 0): Unit = {
    val indent = " " * (depth * 2)
    System.err.println(s"$indent${ctx.getClass.getSimpleName}: ${annotations.scopes.get(ctx).get.descriptor}")
    if (ctx.getParent != null)
      printAncestors(ctx.getParent, annotations, depth + 1)
  }

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

case class RuleScope(parentScope: NestedScope, modelParameterSymbol: Parameter) extends NestedScope(Some(parentScope)) {

  declare(modelParameterSymbol)

  override def descriptor = super.descriptor + " " + modelParameterSymbol.name

}

case class DefinitionScope(parentScope: NestedScope, val parameters: List[Parameter]) extends NestedScope(Some(parentScope)) {
  parameters.foreach(sym => declare(sym))

  override def descriptor = s"${getClass.getSimpleName}(${parameters.map(_.name).mkString(", ")})"
}

class CollectionMemberScope(parentScope: NestedScope) extends NestedScope(Some(parentScope)) {

  var indexSymbol: Option[CollectionIndexSymbol] = None

  override def resolve(name: String, components: List[String]): Symbol =
    parent.get.resolve(name) match {
      case Some(symbol) if name.equals(symbol.name) =>
        symbol
      case Some(other) =>
        failure(s"Unexpected Symbol type: $other")
      case None =>
        require(indexSymbol.isDefined)
        new CollectionIndexReference(indexSymbol.get, components)
    }

  override def descriptor = s"CollectionMemberScope (index = ${indexSymbol})"
}

