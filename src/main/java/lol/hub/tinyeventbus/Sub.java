package lol.hub.tinyeventbus;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Represents a registerable and unregisterable subscriber to events provided by {@link Bus}.
 * <br>
 * Can be registered/unregistered directly with {@link Bus#reg(Sub)}/{@link Bus#unreg(Sub)}
 * or as field member of an {@link Object} with {@link Bus#reg(Object)}/{@link Bus#unreg(Object)}.
 *
 * @param <T> Generically defined type of the event {@link Object} to be subscribed to.
 * @author nothub
 */
public class Sub<T> implements Comparable<Sub<T>> {

    /**
     * Priority of processing.
     * A {@link Sub} created without a specific priority value will be created with a default value of 0.
     * Can be defined in the range of {@link Integer#MAX_VALUE} to {@link Integer#MIN_VALUE}.
     */
    public final int priority;

    /**
     * Consumer to be invoked when an event is received.
     * Accepts an object of the generically defined type as event to be processed.
     */
    public final Consumer<T> consumer;

    final Class<?> eventType;

    /**
     * Creates a {@link Sub} instance with manually defined event type.
     *
     * @param consumer  Consumer to be invoked when an event is received.
     * @param priority  Priority of processing.
     * @param eventType Manually defined type of event to be subscribed to.
     * @see Sub#of(Consumer, int, Class)
     */
    public Sub(Consumer<T> consumer, int priority, Class<?> eventType) {
        this.priority = priority;
        this.consumer = consumer;
        this.eventType = eventType;
    }

    /**
     * Creates a {@link Sub} instance with manually defined event type.
     *
     * @param consumer  Consumer to be invoked when an event is received.
     * @param eventType Manually defined type of event to be subscribed to.
     * @see Sub#of(Consumer, Class)
     */
    public Sub(Consumer<T> consumer, Class<?> eventType) {
        this(consumer, 0, eventType);
    }

    /**
     * Convenience method to create a {@link Sub} instance.
     *
     * @param consumer  Consumer to be invoked when an event is received.
     * @param priority  Priority of processing.
     * @param eventType Manually defined type of event to be subscribed to.
     * @param <T>       Generically defined type of event to be subscribed to.
     * @return Created {@link Sub} instance.
     * @see Sub#Sub(Consumer, int, Class)
     */
    public static <T> Sub<T> of(Consumer<T> consumer, int priority, Class<?> eventType) {
        return new Sub<>(consumer, priority, eventType);
    }

    /**
     * Convenience method to create a {@link Sub} instance.
     *
     * @param consumer  Consumer to be invoked when an event is received.
     * @param eventType Manually defined type of event to be subscribed to.
     * @param <T>       Generically defined type of event to be subscribed to.
     * @return Created {@link Sub} instance.
     * @see Sub#Sub(Consumer, Class)
     */
    public static <T> Sub<T> of(Consumer<T> consumer, Class<?> eventType) {
        return new Sub<>(consumer, eventType);
    }

    void accept(Object event) {
        try {
            //noinspection unchecked
            consumer.accept((T) event);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     * <br>
     * Compares this and the provided {@link Sub} as specified in {@link Comparable#compareTo(Object)}
     * <br>
     * Subscribers are distinguished by comparing the following 3 characteristics in the listed order:
     * <ul>
     *     <li>{@link Sub#priority}</li>
     *     <li>{@link Sub#consumer} (hashCode)</li>
     *     <li>{@link Sub} (hashCode)</li>
     * </ul>
     *
     * @param sub the {@link Sub} to be compared.
     */
    @Override
    public int compareTo(@NotNull Sub<T> sub) {
        if (sub.priority != priority) {
            return Integer.compare(sub.priority, priority);
        }
        if (sub.consumer.hashCode() != consumer.hashCode()) {
            return Integer.compare(sub.consumer.hashCode(), consumer.hashCode());
        }
        return Integer.compare(sub.hashCode(), hashCode());
    }

}
