package boe.jiden.test.dummies;

import net.staro.api.listener.GenericListener;

import java.lang.reflect.Method;

/**
 * A dummy Listener.
 * Override {@code invoke(Object event)} to get additional functionality.
 */
public class DummyGenericListener extends GenericListener
{
    public DummyGenericListener(Object instance, Method method, int priority)
    {
        super(instance, method, priority);
    }

    @Override
    public void invoke(Object event)
    {
        super.invoke(event);
    }
}
