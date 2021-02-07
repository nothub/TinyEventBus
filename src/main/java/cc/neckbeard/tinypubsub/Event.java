package cc.neckbeard.tinypubsub;

public abstract class Event {

    private boolean cancelled;

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

}
