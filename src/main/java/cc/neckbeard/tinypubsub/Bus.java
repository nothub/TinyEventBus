package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class Bus {

    private final Map<Class<?>, ConcurrentSkipListSet<Sub>> subs = new ConcurrentHashMap<>();
    private final Object lock = new Object();

    private static Set<Sub> getSubs(Object obj) {
        return Arrays
            .stream(obj.getClass().getFields())
            .filter(field -> field.getType().equals(Sub.class))
            .map(field -> {
                boolean access = field.isAccessible();
                field.setAccessible(true);
                try {
                    return field.get(0);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(access);
                }
                return null;
            })
            .filter(Objects::nonNull)
            .map(o -> (Sub) o)
            .collect(Collectors.toSet());
    }

    public void reg(@NotNull Sub sub, @NotNull Class<?> type) {
        synchronized (lock) {
            final ConcurrentSkipListSet<Sub> subs = this.subs
                .computeIfAbsent(type, c -> new ConcurrentSkipListSet<>());
            if (subs.contains(sub)) return;
            subs.add(sub);
        }
    }

    public void regAll(@NotNull Object o, @NotNull Class<?> type) {
        synchronized (lock) {
            getSubs(o).forEach(s -> reg(s, type));
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

    public void unregAll(@NotNull Object o, @NotNull Class<?> type) {
        synchronized (lock) {
            getSubs(o).forEach(s -> unreg(s, type));
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
