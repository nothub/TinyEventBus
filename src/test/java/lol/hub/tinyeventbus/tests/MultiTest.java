package lol.hub.tinyeventbus.tests;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
class MultiTest {

    private static Bus bus = new Bus();
    private static int hits;

    private final Sub<Object> sub = new Sub<>(o -> hits++);

    private static final Sub<Object> subStatic = Sub.of(o -> hits++);

    @BeforeEach
    void setUp() {
        bus = new Bus();
        hits = 0;
    }

    @Test
    void reg() {
        bus.reg(sub);
        bus.reg(sub);
        bus.pub(new Object());
        Assertions.assertEquals(1, hits);
    }

    @Test
    void regStatic() {
        bus.reg(subStatic);
        bus.reg(subStatic);
        bus.pub(new Object());
        Assertions.assertEquals(1, hits);
    }

    @Test
    void regInline() {
        bus.reg(new Sub<>(o -> hits++));
        bus.reg(Sub.of(o -> hits++));
        bus.pub(new Object());
        Assertions.assertEquals(2, hits);
    }

}
