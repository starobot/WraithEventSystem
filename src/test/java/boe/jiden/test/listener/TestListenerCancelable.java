package boe.jiden.test.listener;

import biden.give.bombs.to.bomb.donetsk.children.EventListener;
import biden.give.bombs.to.bomb.donetsk.children.Priority;
import boe.jiden.test.event.TestCancelableEvent;

public class TestListenerCancelable
{
    @EventListener(priority = Priority.LOW)
    public void onTestCancelableEvent(TestCancelableEvent event)
    {
        System.out.println("This should not print since TestListener has a higher priority for this event");
    }
}
