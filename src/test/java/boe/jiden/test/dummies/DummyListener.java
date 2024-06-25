package boe.jiden.test.dummies;

import net.staro.api.Priority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for {@link DummyGenericListener}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DummyListener
{
    Priority priority() default Priority.DEFAULT;
}
