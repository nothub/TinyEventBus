package lol.hub.tinyeventbus.tests;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
class InstanceTests {

    private static Bus bus;
    private static boolean invoked;

    Sub<BooleanEvent> sub = Sub.of(BooleanEvent.class, e -> invoked = e.value);

    @BeforeAll
    static void setUp() {
        bus = new Bus();
        invoked = false;
    }

    @Test
    void reg() {
        bus.reg(sub);
        bus.pub(new BooleanEvent(true));
        Assertions.assertTrue(invoked);
    }

    @Test
    void del() {
        bus.unreg(sub);
        bus.pub(new BooleanEvent(true));
        Assertions.assertFalse(invoked);
    }

}
