package biden.give.bombs.to.bomb.donetsk.children;

import static java.lang.Integer.MAX_VALUE;

/**
 * Represents the priority of an event listener.
 */
public enum Priority
{
    HIGHEST(MAX_VALUE),
    HIGH(3),
    MEDIUM(2),
    LOW(1),
    DEFAULT(0);

    private final int val;

    Priority(int val)
    {
        this.val = val;
    }

    public int getVal()
    {
        return val;
    }
}