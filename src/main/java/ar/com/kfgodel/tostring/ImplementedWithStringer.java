package ar.com.kfgodel.tostring;

import java.lang.annotation.*;

/**
 * This annotation is a mark for toString() methods implemented with Stringer, to avoid calling them inside other object representation.<br>
 *     This annotation is needed to avoid toString() recursion since java.lang.String cannot be extended to carry Stringer information in recursions.<br>
 * <br>
 *     Apply this annotation to every toString method implemented as Stringer.representationOf(this);
 *
 * Created by kfgodel on 17/09/14.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImplementedWithStringer {
}
