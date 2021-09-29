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

/**
 * Central event manager, used to publish events and, register/unregister subscribers.
 * <br>
 * Any {@link Object} can be used to represent an event.
 * Events are identified by their {@link Class} type.
 * <br>
 * Multiple {@link Sub}s can be registered to listen to one event type.
 * The processing order of registered {@link Sub}s is determined by their {@link Sub#priority}.
 *
 * @author nothub
 */
public class Bus {

    private final Map<Class<?>, ConcurrentSkipListSet<Sub<?>>> subs = new ConcurrentHashMap<>();

    @NotNull
    private static Set<Sub<?>> getSubFields(Object parent) {
        return Arrays.stream(parent.getClass().getDeclaredFields())
            .filter(field -> field.getType().equals(Sub.class))
            .filter(field -> Sub.class.isAssignableFrom(field.getType()))
            .map(subFieldConverter(parent))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }

    @NotNull
    private static Function<Field, Sub<?>> subFieldConverter(Object parent) {
        return field -> {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            try {
                return (Sub<?>) field.get(parent);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            } finally {
                field.setAccessible(accessible);
            }
        };
    }

    /**
     * Publishes an event.
     * If the published event implements the {@link Cancelable} interface,
     * additional processing can be stopped prematurely by any invoked {@link Sub}.
     *
     * @param event Event to be published.
     */
    public void pub(Object event) {
        final ConcurrentSkipListSet<Sub<?>> subs = this.subs.get(event.getClass());
        if (subs == null) return;
        subs.forEach(sub -> {
            if (event instanceof Cancelable && ((Cancelable) event).isCanceled()) return;
            sub.accept(event);
        });
    }

    /**
     * Registers a {@link Sub}.
     *
     * @param sub {@link Sub} to be registered.
     * @see #reg(Object)
     */
    public void reg(Sub<?> sub) {
        subs
            .computeIfAbsent(sub.eventType, clazz -> new ConcurrentSkipListSet<>())
            .add(sub);
    }


    /**
     * Registers all {@link Sub} field members of the parent {@link Object}.
     *
     * @param parent Parent object of {@link Sub} field members to be registered.
     * @see #reg(Sub)
     */
    public void reg(Object parent) {
        getSubFields(parent)
            .forEach(this::reg);
    }


    /**
     * Unregisters a {@link Sub}.
     *
     * @param sub Subscriber to be unregistered.
     * @see #unreg(Object)
     */
    public void unreg(Sub<?> sub) {
        final ConcurrentSkipListSet<Sub<?>> subs = this.subs.get(sub.eventType);
        if (subs == null) return;
        subs.remove(sub);
    }


    /**
     * Unregisters all {@link Sub} field members of the parent {@link Object}.
     *
     * @param parent Parent object of {@link Sub} field members to be unregistered.
     * @see #unreg(Sub)
     */
    public void unreg(Object parent) {
        getSubFields(parent)
            .forEach(this::unreg);
    }

}
