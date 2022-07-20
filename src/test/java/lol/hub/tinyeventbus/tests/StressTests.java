package lol.hub.tinyeventbus.tests;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Sub;
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

    Sub<Object> subA = Sub.of(o -> hits++, Integer.MAX_VALUE);
    Sub<Object> subB = new Sub<>(o -> hits++, 42);
    Sub<Object> subC = Sub.of(o -> hits++);
    Sub<Object> subD = new Sub<>(o -> hits++, -42);
    Sub<Object> subE = Sub.of(o -> hits++, Integer.MIN_VALUE);

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
                bus.unreg(subA);
                bus.unreg(subB);
                bus.unreg(subC);
                bus.unreg(subD);
                bus.unreg(subE);
            });
        Assertions.assertEquals(5 * runs, hits);
    }

}
