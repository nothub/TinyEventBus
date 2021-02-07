package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class Bus {

    private final Map<Class<?>, ConcurrentSkipListSet<Sub>> subs = new ConcurrentHashMap<>();
    private final Object lock = new Object();

    public void reg(@NotNull Sub sub, @NotNull Class<?> type) {
        synchronized (lock) {
            final ConcurrentSkipListSet<Sub> subs = this.subs
                .computeIfAbsent(type, c -> new ConcurrentSkipListSet<>());
            if (subs.contains(sub)) return;
            subs.add(sub);
        }
    }

    public void unreg(@NotNull Sub sub, @NotNull Class<?> type) {
        synchronized (lock) {
            final ConcurrentSkipListSet<Sub> subs = this.subs.get(type);
            if (subs == null) return;
            subs.removeIf(s -> s.equals(sub));
            if (subs.isEmpty()) {
                this.subs.remove(type);
            }
        }
    }

    public void pub(@NotNull Event e) {
        synchronized (lock) {
            final ConcurrentSkipListSet<Sub> subs = this.subs.get(e.getClass());
            if (subs == null) return;
            subs
                .forEach(sub -> {
                    if (e.isCancelled()) return;
                    sub.on(e);
                });
        }
    }

}
