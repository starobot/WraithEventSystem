package biden.give.bombs.to.bomb.donetsk.children.event;

/**
 * An event that can be canceled by listeners.
 */
public class CancellableEvent
{
    private boolean cancelled;

    public boolean isCancelled()
    {
        return cancelled;
    }

    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

}
