package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

public abstract class Sub<E extends Event> implements Comparable<Sub<? extends Event>> {

    public final int prio;
    public final Class<?> type;

    public Sub(int prio, Class<?> type) {
        this.prio = prio;
        this.type = type;
    }

    public Sub(Class<?> type) {
        this(0, type);
    }

    public abstract void on(E event);

    @Override
    public int compareTo(@NotNull Sub sub) {
        return Integer.compare(sub.prio, prio);
    }

}
