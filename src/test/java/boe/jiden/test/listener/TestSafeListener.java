package boe.jiden.test.listener;

import biden.give.bombs.to.bomb.donetsk.children.Priority;
import biden.give.bombs.to.bomb.donetsk.children.annotation.SafeEventListener;
import boe.jiden.test.event.TestEvent;

public class TestSafeListener
{
    @SafeEventListener(priority = Priority.MEDIUM)
    public void onSafeTestEvent(TestEvent ignored)
    {
        System.out.println("Retrieved safe test event!");
    }
}
