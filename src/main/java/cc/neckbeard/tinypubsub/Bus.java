package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class Bus {

    private final Map<Class<?>, ConcurrentSkipListSet<Sub>> subs = new ConcurrentHashMap<>();
    private final Object lock = new Object();

    public void register(@NotNull Sub sub, @NotNull Class<?> clazz) {
        synchronized (lock) {
            subs.computeIfAbsent(clazz, c -> new ConcurrentSkipListSet<>()).add(sub);
        }
    }

    public void unregister(@NotNull Sub sub, @NotNull Class<?> clazz) {
        synchronized (lock) {
            subs
                .get(clazz)
                .stream()
                .filter(s -> s.getClass().equals(sub.getClass()))
                .forEach(s -> subs.get(clazz).remove(s));
        }
    }

    public void post(@NotNull Event e) {
        synchronized (lock) {
            subs
                .get(e.getClass())
                .forEach(sub -> sub.on(e));
        }
    }

}
