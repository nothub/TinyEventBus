package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

class Container implements Comparable<Container> {

    final int prio;
    final Class<?> type;
    final Method method;
    final Object parent;
    final boolean statik;

    Container(int prio, @NotNull Class<?> type, Method method, @NotNull Object parent, boolean statik) {
        this.prio = prio;
        this.type = type;
        this.method = method;
        this.parent = parent;
        this.statik = statik;
    }

    @Override
    public int compareTo(@NotNull Container container) {
        if (container.prio != prio) {
            return Integer.compare(container.prio, prio);
        }
        if (container.method.hashCode() != method.hashCode()) {
            return Integer.compare(container.method.hashCode(), method.hashCode());
        }
        return Integer.compare(container.parent.hashCode(), parent.hashCode());
    }

}
