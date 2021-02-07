package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.IntStream;

@Execution(ExecutionMode.CONCURRENT)
class SortingTests {

    @Test
    void shuffle() {
        IntStream
            .range(0, 100)
            .forEach(v -> {
                List<Sub<BooleanEvent>> expected = new ArrayList<>();
                expected.add(new Sub<>(2, BooleanEvent.class, e -> {}));
                expected.add(new Sub<>(1, BooleanEvent.class, e -> {}));
                expected.add(new Sub<>(0, BooleanEvent.class, e -> {}));
                expected.add(new Sub<>(-1, BooleanEvent.class, e -> {}));
                List<Sub<BooleanEvent>> random = new ArrayList<>(expected);
                Collections.shuffle(random);
                List<Sub<BooleanEvent>> sorted = new ArrayList<>(new ConcurrentSkipListSet<>(random));
                IntStream
                    .range(0, 4)
                    .forEach(i -> Assertions.assertEquals(expected.get(i), sorted.get(i)));
            });
    }

}
