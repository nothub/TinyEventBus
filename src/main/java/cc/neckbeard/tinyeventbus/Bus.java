package cc.neckbeard.tinyeventbus;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Bus {

    private final Map<Class<?>, ConcurrentSkipListSet<Sub<?>>> subs = new ConcurrentHashMap<>();

    @NotNull
    private static Set<Sub<?>> getSubFields(Object parent) {
        return Arrays.stream(parent.getClass().getDeclaredFields())
            .filter(field -> field.getType().equals(Sub.class))
            .map(subFieldConverter(parent))
            .map(o -> (Sub<?>) o)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }

    @NotNull
    private static Function<Field, Object> subFieldConverter(Object parent) {
        return field -> {
            boolean accessible = field.isAccessible(); // jdk 9+: boolean accessible = field.canAccess(Bus.class);
            field.setAccessible(true);
            try {
                return field.get(parent);
            } catch (IllegalAccessException e) {
                return null;
            } finally {
                field.setAccessible(accessible);
            }
        };
    }

    public void reg(Sub<?> sub) {
        subs
            .computeIfAbsent(sub.type, clazz -> new ConcurrentSkipListSet<>())
            .add(sub);
    }

    public void del(Sub<?> sub) {
        final ConcurrentSkipListSet<Sub<?>> subs = this.subs.get(sub.type);
        if (subs == null) return;
        subs.remove(sub);
    }

    public void pub(Object obj) {
        final ConcurrentSkipListSet<Sub<?>> subs = this.subs.get(obj.getClass());
        if (subs == null) return;
        subs.forEach(sub -> {
            if (obj instanceof Cancellable && ((Cancellable) obj).isCancelled()) return;
            sub.accept(obj);
        });
    }

    public void reg(Object parent) {
        getSubFields(parent)
            .forEach(this::reg);
    }

    public void del(Object parent) {
        getSubFields(parent)
            .forEach(this::del);
    }

}
