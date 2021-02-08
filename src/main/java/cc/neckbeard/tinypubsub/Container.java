package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;

public class Container implements Comparable<Container> {

    protected final int prio;
    protected final Class<?> type;
    protected final MethodHandle handle;

    protected Container(int prio, Class<?> type, MethodHandle handle) {
        this.prio = prio;
        this.type = type;
        this.handle = handle;
    }

    @Override
    public int compareTo(@NotNull Container container) {
        return container.prio == prio ? Integer.compare(container.handle.hashCode(), handle.hashCode()) : Integer.compare(container.prio, prio);
    }

}
