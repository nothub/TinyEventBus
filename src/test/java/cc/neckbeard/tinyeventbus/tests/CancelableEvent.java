package cc.neckbeard.tinyeventbus.tests;

import cc.neckbeard.tinyeventbus.Cancelable;

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
