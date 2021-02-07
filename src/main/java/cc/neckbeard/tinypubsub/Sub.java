package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Sub<E extends Event> implements Comparable<Sub<?>> {

    public final int prio;
    public final Class<?> type;
    private final Consumer<E> consumer;

    public Sub(int prio, Class<?> type, Consumer<E> consumer) {
        this.prio = prio;
        this.type = type;
        this.consumer = consumer;
    }

    public Sub(Class<?> type, Consumer<E> consumer) {
        this(0, type, consumer);
    }

    protected void on(E event) {
        consumer.accept(event);
    }

    @Override
    public int compareTo(@NotNull Sub sub) {
        return Integer.compare(sub.prio, prio);
    }

}
