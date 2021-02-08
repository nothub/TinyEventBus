package cc.neckbeard.tinypubsub;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class Bus {

    private final Map<Class<?>, ConcurrentSkipListSet<Container>> subs = new ConcurrentHashMap<>();

    public void reg(@NotNull Object parent) {

        List<Method> declared = Arrays.asList(parent.getClass().getDeclaredMethods());

        Map<Method, Class<?>> methods = declared.stream()
            .filter(m -> m.isAnnotationPresent(Sub.class))
            .collect(Collectors.toSet()).stream()
            .filter(m -> !Modifier.isStatic(m.getModifiers()))
            .collect(Collectors.toSet()).stream()
            .filter(m -> m.getParameterCount() == 1)
            .collect(Collectors.toSet()).stream()
            .collect(Collectors.toMap(m -> m, m -> m.getParameterTypes()[0]));

        methods.forEach((m, c) -> {
            m.setAccessible(true);
            try {
                subs
                    .computeIfAbsent(c, dfault -> new ConcurrentSkipListSet<>())
                    .add(
                        new Container(
                            m.getAnnotation(Sub.class).prio(),
                            c,
                            MethodHandles.lookup().unreflect(m)
                        )
                    );
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e.getMessage());
            }
        });

    }

    public void pub(@NotNull Object e) {
        subs.computeIfAbsent(e.getClass(), dfault -> new ConcurrentSkipListSet<>())
            .forEach(sub -> {
                if (e instanceof Cancellable && ((Cancellable) e).isCancelled()) return;
                try {
                    sub.handle.invoke(e);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
    }

}
