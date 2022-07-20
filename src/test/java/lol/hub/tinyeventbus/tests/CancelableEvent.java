package lol.hub.tinyeventbus.tests;

import lol.hub.tinyeventbus.Cancelable;

abstract class CancelableEvent extends Event implements Cancelable {

    boolean canceled;

    CancelableEvent() {
    }

    public void cancel() {
        this.canceled = true;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

}
