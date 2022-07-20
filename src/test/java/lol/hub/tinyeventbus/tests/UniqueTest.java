package lol.hub.tinyeventbus.tests;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
class UniqueTest {

    private static Bus bus;
    private static int hits;

    @BeforeEach
    void setUp() {
        bus = new Bus();
        hits = 0;
    }

    Sub<Object> sub = new Sub<>(o -> hits++);

    @Test
    void multireg_a() {
        bus.reg(sub);
        bus.reg(sub);
        bus.pub(new Object());
        Assertions.assertEquals(1, hits);
    }

    @Test
    void multireg_b() {
        bus.reg(new Sub<>(o -> hits++));
        bus.reg(new Sub<>(o -> hits++));
        bus.reg(Sub.of(o -> hits++));
        bus.reg(Sub.of(o -> hits++));
        bus.pub(new Object());
        Assertions.assertEquals(4, hits);
    }

}
