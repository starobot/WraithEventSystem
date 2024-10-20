package boe.jiden.test.dummies.listener;

import boe.jiden.test.dummies.DummyListener;
import boe.jiden.test.dummies.event.TestDummyEvent;
import net.staro.api.Priority;

/**
 * This should do nothing since DummyGenericListener has an empty {@code invoke(Object event)} method.
 */
public class TestDummyListener
{
    @DummyListener(priority = Priority.HIGHEST)
    public void invoke(TestDummyEvent ignored)
    {

    }
}
