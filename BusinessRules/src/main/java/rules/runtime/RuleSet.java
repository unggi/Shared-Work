package rules.runtime;

import java.util.Map;

/**
 * Interface to a compiled rule set which will be applied to a document.
 *
 *
 * Created by unggi on 8/22/15.
 */
public interface RuleSet {

    public Boolean evaluate(Map<String,Object> inputs);

}
