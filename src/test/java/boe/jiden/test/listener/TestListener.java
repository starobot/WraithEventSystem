package boe.jiden.test.listener;

import boe.jiden.test.event.TestCancelableEvent;
import boe.jiden.test.event.TestEvent;
import net.staro.api.Priority;
import net.staro.api.annotation.Listener;

public class TestListener
{
    @Listener
    public void onTestEvent(TestEvent event)
    {
        System.out.println("Retrieved test event!");
    }

    @Listener(priority = Priority.HIGH)
    public void onTestCancelableEvent(TestCancelableEvent event)
    {
        System.out.println("Retrieved test event!");
        event.setCancelled(true);
    }
}
