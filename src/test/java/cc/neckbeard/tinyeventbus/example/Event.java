package cc.neckbeard.tinyeventbus.example;

import cc.neckbeard.tinyeventbus.Cancelable;

class Event implements Cancelable {

    String str;
    boolean canceled;

    Event(String str) {
        this.str = str;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

}
