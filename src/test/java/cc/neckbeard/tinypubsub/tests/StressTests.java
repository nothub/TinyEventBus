package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.stream.IntStream;

@Execution(ExecutionMode.SAME_THREAD)
class StressTests {

    private static Bus bus;
    private static int hits;

    @BeforeEach
    void setUp() {
        bus = new Bus();
        hits = 0;
    }

    @Sub(prio = Integer.MAX_VALUE)
    public void subA(Object ignored) {
        hits++;
    }

    @Sub(prio = 42)
    public void subB(Object ignored) {
        hits++;
    }

    @Sub
    public void subC(Object ignored) {
        hits++;
    }

    @Sub(prio = -42)
    public void subD(Object ignored) {
        hits++;
    }

    @Sub(prio = Integer.MIN_VALUE)
    public void subE(Object ignored) {
        hits++;
    }

    @Test
    @DisplayName("pub 2m")
    void pub_() {
        final int runs = 2_000_000;
        bus.reg(this);
        IntStream
            .range(0, runs)
            .forEach(i -> bus.pub(new Object()));
        Assertions.assertEquals(5 * runs, hits);
    }

    @Test
    @DisplayName("reg+pub 100k")
    void regpub() {
        final int runs = 100_000;
        IntStream
            .range(0, runs)
            .forEach(i -> {
                bus.reg(this);
                bus.pub(new Object());
            });
        Assertions.assertEquals(5 * runs, hits);
    }

    @Test
    @DisplayName("reg+pub+del 10k")
    void regdelpub() {
        final int runs = 10_000;
        IntStream
            .range(0, runs)
            .forEach(i -> {
                bus.reg(this);
                bus.pub(new Object());
                bus.del(this);
            });
        Assertions.assertEquals(5 * runs, hits);
    }

}
