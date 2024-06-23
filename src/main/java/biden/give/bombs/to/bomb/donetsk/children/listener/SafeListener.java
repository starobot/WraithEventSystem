package biden.give.bombs.to.bomb.donetsk.children.listener;

import java.lang.reflect.Method;

/**
 * An extent of Listener for an automatic nullcheck (for example).
 * Override {@code invoke(Object event)} for the additional functionality.
 */
public class SafeListener extends Listener
{
    public SafeListener(Object instance, Method method, int priority)
    {
        super(instance, method, priority);
    }
}
