package boe.jiden.test.listener;

import biden.give.bombs.to.bomb.donetsk.children.annotation.EventListener;
import biden.give.bombs.to.bomb.donetsk.children.Priority;
import boe.jiden.test.event.TestCancelableEvent;
import boe.jiden.test.event.TestEvent;

public class TestListener
{
    @EventListener
    public void onTestEvent(TestEvent event)
    {
        System.out.println("Retrieved test event!");
    }

    @EventListener(priority = Priority.HIGH)
    public void onTestCancelableEvent(TestCancelableEvent event)
    {
        System.out.println("Retrieved test event!");
        event.setCancelled(true);
    }
}
