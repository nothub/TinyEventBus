package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bus {

    private final Map<Class<?>, ConcurrentSkipListSet<Container>> subs = new ConcurrentHashMap<>();

    public void pub(@NotNull Object e) {
        subs.computeIfAbsent(e.getClass(), dfault -> new ConcurrentSkipListSet<>())
            .forEach(sub -> {
                if (e instanceof Cancellable && ((Cancellable) e).isCancelled()) return;
                try {
                    if (sub.statik) {
                        sub.method.invoke(null, e);
                    } else {
                        sub.method.invoke(sub.parent, e);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            });
    }

    public void reg(@NotNull Object parent) {
        reg(parent, false);
    }

    public void reg(@NotNull Object parent, boolean duplicates) {
        parentSubs(parent)
            .collect(Collectors.toMap(m -> m, m -> m.getParameterTypes()[0]))
            .forEach((m, t) -> reg(m, t, parent));
    }

    private void reg(Method method, Class<?> type, Object parent) {
        method.setAccessible(true);
        subs
            .computeIfAbsent(type, dfault -> new ConcurrentSkipListSet<>())
            .add(
                new Container(
                    method.getAnnotation(Sub.class).prio(),
                    type,
                    method,
                    parent,
                    Modifier.isStatic(method.getModifiers())));
    }

    public void del(@NotNull Object parent) {
        parentSubs(parent)
            .map(m -> m.getParameterTypes()[0])
            .forEach(t -> del(t, parent));
    }

    private void del(Class<?> type, Object parent) {
        if (subs.get(type) == null) return;
        subs.get(type).removeIf(container -> container.parent.equals(parent));
    }

    @NotNull
    private Stream<Method> parentSubs(@NotNull Object parent) {
        return Arrays
            .stream(parent.getClass().getDeclaredMethods())
            .filter(m -> m.isAnnotationPresent(Sub.class))
            .filter(m -> m.getParameterCount() == 1);
    }

    private boolean isRegistered(Class<?> type, @NotNull Object parent, Method m) {
        return subs.get(type) != null && subs
            .get(type)
            .stream()
            .anyMatch(container -> container.parent.equals(parent) && container.method.equals(m));
    }

}
