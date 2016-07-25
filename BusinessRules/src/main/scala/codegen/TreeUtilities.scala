package codegen

import codegen.symbols.NestedScope
import org.antlr.v4.runtime.tree.TerminalNode
import org.antlr.v4.runtime.{ParserRuleContext, Token}
import rules.BusinessRulesParser.ModelReferenceContext

import scala.collection.JavaConversions._
import scala.collection.immutable

/**
  * Created by unggi on 7/1/16.
  */
object TreeUtilities {

  import StringFormatter._

  def nameOf(any: Any): String = any.getClass.getSimpleName

  def tokenToText(token: Token): String = token match {
    case node: TerminalNode => unquote(node.getText)
    case token: Token => unquote(token.getText)
    case unknown =>
      assert(true, "Unhandled token type " + unknown)
      ""
  }

  def failure(msg: String): Nothing = {
    throw new RuntimeException(msg)
  }

  def pathComponents(ref: ModelReferenceContext): List[String] =
    if (ref.dotPath != null)
      ref.dotPath.ModelElementName.map(_.getText).toList
    else
      ref.propPath.ModelElementName.map(_.getText).toList

  def find[T <: NestedScope](cls: Class[T], start: NestedScope): Option[T] =
    if (start.getClass == cls)
      Some(start.asInstanceOf[T])
    else if (start.parent.isDefined)
      find[T](cls, start.parent.get)
    else
      None

  def root(ctx: ModelReferenceContext): String =
    if (ctx.dotPath != null)
      ctx.dotPath.root.getText
    else
      ctx.propPath.root.getText

  def find[T <: ParserRuleContext](ctx: ParserRuleContext, cls: Class[T]): immutable.List[T] =
    ctx.getRuleContexts(cls).toList

  def query[T <: ParserRuleContext](ctx: ParserRuleContext)(mapper: (ParserRuleContext) => Option[T]): immutable.List[T] =
    find(ctx, classOf[ParserRuleContext]).flatMap(mapper(_))

  def apply[P <: ParserRuleContext, C <: ParserRuleContext](ctx: P, cls: Class[C], body: (C) => String): String =
    ctx.getRuleContexts(cls).toList.foldLeft("")(_ + body(_))

  def apply[P <: ParserRuleContext](list: List[P])(body: (P) => String): String =
    list.foldLeft("")(_ + body(_))

}
