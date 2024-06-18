package biden.give.bombs.to.bomb.donetsk.children;

/**
 * The event bus for registering and distributing events.
 */
public interface EventBus
{
    void post(Object event);

    void subscribe(Object instance);

    void unsubscribe(Object instance);
}
