package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
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

    @Sub
    public void sub(Object ignored) {
        hits++;
    }

    @Test
    void multireg() {
        bus.reg(this);
        bus.reg(this);
        bus.pub(new Object());
        Assertions.assertEquals(1, hits);
    }

}
