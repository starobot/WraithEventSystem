package boe.jiden.test.dummies;

import net.staro.api.EventBus;

/**
 * This Dummy EventBus represents a custom EventBus created withing a project to add more generic listeners to the bus.
 * @see DummyGenericListener
 */
public class DummyEventBus extends EventBus
{
    public DummyEventBus()
    {
        super.registerListenerFactory(DummyListener.class, (instance, method) ->
                new DummyGenericListener(instance, method, method.getAnnotation(DummyListener.class).priority().getVal())
        );
    }
}
