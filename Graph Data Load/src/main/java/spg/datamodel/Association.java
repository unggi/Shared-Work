package spg.datamodel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Association {

    enum Cardinality {ONE, ZERO_OR_MORE, ONE_OR_MORE }

    public Class toClass();
    public String name() default "";
    public Cardinality srcCount() default Cardinality.ONE;
    public Cardinality targetCount() default Cardinality.ONE;
}
