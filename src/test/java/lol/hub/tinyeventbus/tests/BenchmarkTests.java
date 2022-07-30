package lol.hub.tinyeventbus.tests;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Execution(ExecutionMode.SAME_THREAD)
public class BenchmarkTests {

    private static Bus bus = new Bus();
    private static int hits;

    @BeforeAll
    static void beforeAll() {
        System.out.println("java runtime: " + System.getProperty("java.runtime.version") + System.lineSeparator());
    }

    void setUp() {
        bus = new Bus();
        hits = 0;
        System.gc();
    }

    @Test
    void test_0_reg() {
        reg(1_000);
        reg(10_000);
    }

    @Test
    void test_1_del() {
        del(1_000);
        del(10_000);
    }

    @Test
    void test_2_pub() {
        pub(1_000, 1_000);
        pub(100, 10_000);
        pub(10_000, 100);
    }

    void reg(int subs) {

        setUp();

        System.out.println("benchmark: bus.reg");

        System.out.println("subs: " + subs);

        List<Sub<String>> listenerContainers = new ArrayList<>();

        IntStream.range(0, subs).forEach(i -> listenerContainers.add(new Sub<>(String.class, s -> {
        })));

        final long start = System.nanoTime();

        IntStream
            .range(0, subs)
            .forEach(i -> bus.reg(listenerContainers.get(i)));

        final long end = System.nanoTime() - start;

        System.out.printf("%,dns (%,dms)" + System.lineSeparator() + System.lineSeparator(), end, end / 1000000);

    }

    void del(int subs) {

        setUp();

        System.out.println("benchmark: bus.del");

        System.out.println("subs: " + subs);

        List<Sub<String>> listenerContainers = new ArrayList<>();

        IntStream.range(0, subs).forEach(i -> listenerContainers.add(new Sub<>(String.class, s -> {
        })));

        IntStream
            .range(0, subs)
            .forEach(i -> bus.reg(listenerContainers.get(i)));

        final long start = System.nanoTime();

        IntStream
            .range(0, subs)
            .forEach(i -> bus.unreg(listenerContainers.get(i)));

        final long end = System.nanoTime() - start;

        System.out.printf("%,dns (%,dms)" + System.lineSeparator() + System.lineSeparator(), end, end / 1000000);

    }

    void pub(int pubs, int subs) {

        setUp();

        System.out.println("benchmark: bus.pub");

        System.out.println("pubs: " + pubs);
        System.out.println("subs: " + subs);

        List<Sub<String>> listenerContainers = new ArrayList<>();

        IntStream.range(0, subs).forEach(i -> listenerContainers.add(new Sub<>(String.class, s -> hits++)));

        IntStream
            .range(0, subs)
            .forEach(i -> bus.reg(listenerContainers.get(i)));

        final long start = System.nanoTime();

        IntStream
            .range(0, pubs)
            .forEach(i -> bus.pub(""));

        final long end = System.nanoTime() - start;

        Assertions.assertEquals(subs * pubs, hits);

        System.out.printf("%,dns (%,dms)" + System.lineSeparator() + System.lineSeparator(), end, end / 1000000);

    }

}
