package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
class MultiTest {

    private static Bus bus;
    private static int hits;

    @BeforeAll
    static void setUp() {
        bus = new Bus();
        hits = 0;
    }

    @Test
    void reg() {
        bus.reg(new Listener());
        bus.reg(new Listener(), true);
        bus.pub(new BooleanEvent(true));
        Assertions.assertEquals(2, hits);
    }

    public class Listener {
        @Sub
        private void on(BooleanEvent event) {
            hits++;
        }
    }

}
