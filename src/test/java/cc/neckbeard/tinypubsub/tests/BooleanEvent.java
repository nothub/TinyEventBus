package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Event;

class BooleanEvent extends Event {

    public boolean bool;

    public BooleanEvent(boolean bool) {
        this.bool = bool;
    }

}
