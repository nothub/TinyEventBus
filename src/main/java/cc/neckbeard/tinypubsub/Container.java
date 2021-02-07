package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;

class Container implements Comparable<Container> {

    final int prio;
    final Class<?> type;
    final MethodHandle handle;
    final Object parent;
    final boolean statik;

    Container(int prio, @NotNull Class<?> type, @NotNull MethodHandle handle, @NotNull Object parent, boolean statik) {
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
