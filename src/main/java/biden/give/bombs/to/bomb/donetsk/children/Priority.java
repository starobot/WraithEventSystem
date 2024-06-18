package biden.give.bombs.to.bomb.donetsk.children;

/**
 * Represents the priority of an event listener.
 */
public enum Priority
{
    High(3),
    Mid(2),
    Low(1);

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