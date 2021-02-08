package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
class Tests {

    private static boolean invoked;

    @Sub
    public static void onEventA(BooleanEvent e) {
        invoked = e.value;
    }

    @Sub
    public void onEventB(BooleanEvent e) {
        invoked = e.value;
    }

    @Test
    void statik() {

        Bus bus = new Bus();

        bus.reg(this);
        bus.pub(new BooleanEvent(true));
        Assertions.assertTrue(invoked);

        //bus.del(this);
        //bus.pub(new BooleanEvent(false));
        //Assertions.assertTrue(invoked);

    }

    static class BooleanEvent {

        final boolean value;

        BooleanEvent(boolean value) {
            this.value = value;
        }

    }

}
