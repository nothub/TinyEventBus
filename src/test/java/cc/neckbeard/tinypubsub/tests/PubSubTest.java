package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
@Execution(ExecutionMode.SAME_THREAD)
public class PubSubTest {

    private static final String event = "";
    private static final Bus bus = new Bus();
    private static int hits = 0;

    @Test
    void run() {

        System.out.println("com.github.nothub TinyPubSub 8c9f424f85");

        final int subs = 1_000;
        final int pubs = 1_000;
        System.out.println("subs: " + subs);
        System.out.println("pubs: " + pubs);

        List<Sub<String>> listenerContainers = new ArrayList<>();

        IntStream.range(0, subs).forEach(i -> listenerContainers.add(new Sub<>(0, s -> hits++)));

        final long start = System.nanoTime();

        IntStream
            .range(0, subs)
            .forEach(i -> bus.reg(listenerContainers.get(i)));

        IntStream
            .range(0, pubs)
            .forEach(i -> bus.pub(event));

        final long end = System.nanoTime() - start;

        System.out.println("hits: " + hits);
        System.out.printf("%,dns (%,dms)", end, end / 1000000);

    }

}
