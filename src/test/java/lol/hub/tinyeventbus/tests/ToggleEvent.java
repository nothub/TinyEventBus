package lol.hub.tinyeventbus.tests;

public class ToggleEvent extends CancelableEvent {
    public boolean state = false;

    void flip() {
        state = !state;
    }
}
