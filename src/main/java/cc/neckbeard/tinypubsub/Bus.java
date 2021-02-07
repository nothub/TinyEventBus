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

    private final Map<Class<?>, Object> locks = new ConcurrentHashMap<>();
    private final Map<Class<?>, ConcurrentSkipListSet<Sub>> subs = new ConcurrentHashMap<>();

    private static Set<Sub> getSubs(Object obj) {
        return Arrays
            .stream(obj.getClass().getDeclaredFields())
            .filter(field -> field.getType().equals(Sub.class))
            .map(field -> {
                boolean access = field.isAccessible();
                field.setAccessible(true);
                try {
                    return field.get(obj);
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

    public void reg(@NotNull Sub sub) {
        synchronized (locks.computeIfAbsent(sub.type, c -> new Object())) {
            if (this.subs.computeIfAbsent(sub.type, c -> new ConcurrentSkipListSet<>()).contains(sub)) return;
            this.subs.get(sub.type).add(sub);
        }
    }

    public void regFields(@NotNull Object o) {
        getSubs(o).forEach(this::reg);
    }

    public void unreg(@NotNull Sub sub) {
        synchronized (locks.computeIfAbsent(sub.type, c -> new Object())) {
            if (this.subs.get(sub.type) == null) return;
            this.subs.get(sub.type).removeIf(s -> s.equals(sub));
        }
    }

    public void unregFields(@NotNull Object o) {
        getSubs(o).forEach(this::unreg);
    }

    public void pub(@NotNull Event e) {
        synchronized (locks.computeIfAbsent(e.getClass(), c -> new Object())) {
            this.subs.computeIfAbsent(e.getClass(), c -> new ConcurrentSkipListSet<>())
                .forEach(sub -> {
                    if (e.isCancelled()) return;
                    sub.on(e);
                });
        }
    }

}
