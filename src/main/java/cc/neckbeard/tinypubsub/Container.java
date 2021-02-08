package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;

public class Container implements Comparable<Container> {

    protected final int prio;
    protected final Class<?> type;
    protected final MethodHandle handle;
    protected final Object parent;
    protected final boolean statik;

    protected Container(int prio, @NotNull Class<?> type, @NotNull MethodHandle handle, @NotNull Object parent, boolean statik) {
        this.prio = prio;
        this.type = type;
        this.handle = handle;
        this.parent = parent;
        this.statik = statik;
    }

    @Override
    public int compareTo(@NotNull Container container) {
        return container.prio == prio ? Integer.compare(container.handle.hashCode(), handle.hashCode()) : Integer.compare(container.prio, prio);
    }

}
