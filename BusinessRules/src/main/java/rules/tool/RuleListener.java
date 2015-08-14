package rules.tool;

import rules.BusinessRulesBaseListener;
import rules.BusinessRulesParser;

/**
 * Created by usoemard on 7/31/2015.
 */
public class RuleListener extends BusinessRulesBaseListener{

    public RuleListener() {

    }

    @Override
    public void exitDeclarations(BusinessRulesParser.DeclarationsContext ctx) {
        super.exitDeclarations(ctx);

        System.err.println("Finished a declaration: ");
        System.err.println("Context is " + ctx.declaration().get(0).getText());

    }
}
