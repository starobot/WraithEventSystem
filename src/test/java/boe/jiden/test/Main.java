package boe.jiden.test;

import boe.jiden.test.dummies.DummyEventBus;
import boe.jiden.test.dummies.event.TestDummyEvent;
import boe.jiden.test.dummies.listener.TestDummyListener;
import boe.jiden.test.event.TestCancelableEvent;
import boe.jiden.test.event.TestEvent;
import boe.jiden.test.listener.TestListener;

public class Main
{
    public static void main(String[] args)
    {
        final DummyEventBus dummyEventBus = new DummyEventBus();

        // Subscribe our testing listeners
        dummyEventBus.subscribe(new TestDummyListener());
        dummyEventBus.subscribe(new TestListener());

        // Test by sending events
        dummyEventBus.post(new TestCancelableEvent());
        dummyEventBus.post(new TestDummyEvent());
        dummyEventBus.post(new TestEvent());
    }
}
