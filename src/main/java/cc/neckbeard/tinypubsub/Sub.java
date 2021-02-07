package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Sub<E extends Event> implements Comparable<Sub<E>> {

    public final int prio;
    public final Class<E> type;
    public final Consumer<E> consumer;

    public Sub(int prio, Class<E> type, Consumer<E> consumer) {
        this.prio = prio;
        this.type = type;
        this.consumer = consumer;
    }

    public Sub(Class<E> type, Consumer<E> consumer) {
        this(0, type, consumer);
    }

    @Override
    public int compareTo(@NotNull Sub sub) {
        return sub.prio == prio ? sub.consumer.getClass().getCanonicalName().compareTo(consumer.getClass().getCanonicalName()) : Integer.compare(sub.prio, prio);
    }

}
