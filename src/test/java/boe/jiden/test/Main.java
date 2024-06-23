package boe.jiden.test;

import biden.give.bombs.to.bomb.donetsk.children.EventBus;
import boe.jiden.test.event.TestCancelableEvent;
import boe.jiden.test.event.TestEvent;
import boe.jiden.test.listener.TestListener;
import boe.jiden.test.listener.TestSafeListener;

public class Main
{
    public static void main(String[] args)
    {
        final EventBus eventBus = new EventBus();

        // Subscribe our testing listeners
        // TODO: benchmarking?
        eventBus.subscribe(new TestListener());
        eventBus.subscribe(new TestSafeListener());

        // Test by sending events
        eventBus.post(new TestEvent());
        eventBus.post(new TestCancelableEvent()); // If the test worked, we should only get this event fired once
    }
}
