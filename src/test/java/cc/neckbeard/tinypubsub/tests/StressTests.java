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

    Sub<Object> subA = new Sub<>(Integer.MAX_VALUE, o -> hits++);
    Sub<Object> subB = new Sub<>(42, o -> hits++);
    Sub<Object> subC = new Sub<>(0, o -> hits++);
    Sub<Object> subD = new Sub<>(-42, o -> hits++);
    Sub<Object> subE = new Sub<>(Integer.MIN_VALUE, o -> hits++);

    @Test
    @DisplayName("pub 2m")
    void pub_() {
        final int runs = 2_000_000;
        bus.reg(subA);
        bus.reg(subB);
        bus.reg(subC);
        bus.reg(subD);
        bus.reg(subE);
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
                bus.reg(subA);
                bus.reg(subB);
                bus.reg(subC);
                bus.reg(subD);
                bus.reg(subE);
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
                bus.reg(subA);
                bus.reg(subB);
                bus.reg(subC);
                bus.reg(subD);
                bus.reg(subE);
                bus.pub(new Object());
                bus.del(subA);
                bus.del(subB);
                bus.del(subC);
                bus.del(subD);
                bus.del(subE);
            });
        Assertions.assertEquals(5 * runs, hits);
    }

}
