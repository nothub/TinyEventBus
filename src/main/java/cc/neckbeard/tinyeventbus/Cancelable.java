package cc.neckbeard.tinyeventbus;

/**
 * Events implementing this interface can be canceled while processed by the {@link Bus}.
 * If an Events {@link Cancelable#isCanceled()} method returns true,
 * subsequent subscribers listening to this event type will not be invoked.
 *
 * @author nothub
 */
public interface Cancelable {

    /**
     * @return Canceling state of the Event.
     */
    boolean isCanceled();

}
