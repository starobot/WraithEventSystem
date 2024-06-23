package biden.give.bombs.to.bomb.donetsk.children.annotation;

import biden.give.bombs.to.bomb.donetsk.children.Priority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods as safe event listeners
 * <p>
 *     P.S: safe listener is an example type of listener that has an additional functionality
 * </p>.
 * The methods are required to be public.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SafeEventListener
{
    Priority priority() default Priority.DEFAULT;
}
