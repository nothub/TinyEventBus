package cc.neckbeard.tinyeventbus.benchmark;

import cc.neckbeard.tinyeventbus.Bus;
import cc.neckbeard.tinyeventbus.Sub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Execution(ExecutionMode.SAME_THREAD)
public class DelBenchmark {

    private static final Bus bus = new Bus();

    @Test
    void benchmark() {

        System.out.println("del");

        final int subs = 1_000;
        System.out.println("subs: " + subs);

        List<Sub<String>> listenerContainers = new ArrayList<>();

        IntStream.range(0, subs).forEach(i -> listenerContainers.add(new Sub<>(0, s -> {})));

        IntStream
            .range(0, subs)
            .forEach(i -> bus.reg(listenerContainers.get(i)));

        final long start = System.nanoTime();

        IntStream
            .range(0, subs)
            .forEach(i -> bus.del(listenerContainers.get(i)));

        final long end = System.nanoTime() - start;

        System.out.printf("%,dns (%,dms)\n", end, end / 1000000);

    }

}
