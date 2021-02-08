package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
class MultiTest {

    private static Bus bus = new Bus();
    private static int hits;

    private final Sub<Object> sub = new Sub<>(0, o -> hits++);

    private static final Sub<Object> subStatic = new Sub<>(0, o -> hits++);

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
        bus.reg(new Sub<>(0, o -> hits++));
        bus.reg(new Sub<>(0, o -> hits++));
        bus.pub(new Object());
        Assertions.assertEquals(2, hits);
    }

}
