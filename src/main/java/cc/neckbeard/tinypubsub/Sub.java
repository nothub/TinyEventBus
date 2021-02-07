package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

public abstract class Sub<E extends Event> implements Comparable<Sub<? extends Event>> {

    /**
     * Priority of invocation.<br>
     * higher -> earlier<br>
     * lower -> later
     */
    public final int prio;

    /**
     * Constructs a Subscriber using a custom priority.
     *
     * @param prio Custom priority
     * @see Sub#prio
     * @see Sub#Sub()
     */
    public Sub(int prio) {
        this.prio = prio;
    }

    /**
     * Constructs a Subscriber using the default priority of 0.
     *
     * @see Sub#prio
     * @see Sub#Sub(int)
     */
    public Sub() {
        this(0);
    }

    public abstract void on(E event);

    @Override
    public int compareTo(@NotNull Sub sub) {
        return Integer.compare(sub.prio, prio);
    }

}
