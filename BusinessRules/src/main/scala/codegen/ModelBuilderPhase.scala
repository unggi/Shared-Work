package codegen

import codegen.model.{Model, ModelFactory}
import rules.BusinessRulesBaseListener
import rules.BusinessRulesParser.ModelFileReferenceContext

/**
  * Created by unggi on 8/7/16.
  */
case class ModelBuilderPhase() extends BusinessRulesBaseListener {

  import StringFormatter._

  var model: Model = _

  override def enterModelFileReference(ctx: ModelFileReferenceContext): Unit = {

    val modelPath = unquote(ctx.DoubleQuotedString().getText)
    model = ModelFactory.create(modelPath)

  }
}
