package codegen

import codegen.symbols.NestedScope
import org.antlr.v4.runtime.tree.xpath.XPath
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}
import org.antlr.v4.runtime.{Parser, ParserRuleContext, Token}
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

  def failure[T](msg: String): T = {
    throw new RuntimeException(msg)
  }

  def debug(msg: String): Unit = {
    System.err.println("DEBUG >> " + msg)
  }

  def pathComponents(ref: ModelReferenceContext): List[String] =
    if (ref.dotPath != null) {
      def x = ref.dotPath.ModelElementName.map(_.getText).toList
      System.err.println(s"""PATH COMPONENTS ${x.mkString(".")}""")
      x
    }
    else
      ref.propPath.ModelElementName.map(_.getText).toList

  def findAncestor[T <: NestedScope](cls: Class[T], start: NestedScope): Option[T] =
    if (start.getClass == cls)
      Some(start.asInstanceOf[T])
    else if (start.parent.isDefined)
      findAncestor[T](cls, start.parent.get)
    else
      None

  def root(ctx: ModelReferenceContext): String =
    if (ctx.dotPath != null)
      ctx.dotPath.root.getText
    else
      ctx.propPath.root.getText

  def findChild[T <: ParserRuleContext](ctx: ParserRuleContext, cls: Class[T]): immutable.List[T] =
    ctx.getRuleContexts(cls).toList

  def query[T <: ParserRuleContext](ctx: ParserRuleContext)(mapper: (ParserRuleContext) => Option[T]): immutable.List[T] =
    findChild(ctx, classOf[ParserRuleContext]).flatMap(mapper(_))

  def apply[P <: ParserRuleContext, C <: ParserRuleContext](ctx: P, cls: Class[C], body: (C) => String): String =
    ctx.getRuleContexts(cls).toList.foldLeft("")(_ + body(_))

  def apply[P <: ParserRuleContext](list: List[P])(body: (P) => String): String =
    list.foldLeft("")(_ + body(_))

  def find(ctx: ParserRuleContext, xpath: String, parser: Parser)(body: (ParseTree) => Unit): Unit = {
    for (t <- XPath.findAll(ctx, xpath, parser))
      body(t)
  }

}
