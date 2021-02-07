package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

public abstract class Sub<E extends Event> implements Comparable<Sub<? extends Event>> {

    public final int prio;

    public Sub(int prio) {
        this.prio = prio;
    }

    public Sub() {
        this(0);
    }

    public abstract void on(E event);

    @Override
    public int compareTo(@NotNull Sub sub) {
        return Integer.compare(sub.prio, prio);
    }

}
