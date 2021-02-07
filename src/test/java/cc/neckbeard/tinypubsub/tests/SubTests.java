package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;

@DisplayName("subscribers")
@Execution(ExecutionMode.CONCURRENT)
class SubTests {

    @Test
    @DisplayName("order")
    void order() {
        Sub<BooleanEvent> a = new Sub<>(2 , BooleanEvent.class, e -> {});
        Sub<BooleanEvent> b = new Sub<>(1, BooleanEvent.class, e -> {});
        Sub<BooleanEvent> c = new Sub<>(0, BooleanEvent.class, e -> {});
        Sub<BooleanEvent> d = new Sub<>(-1, BooleanEvent.class, e -> {});
        List<Sub<BooleanEvent>> expected = new ArrayList<>();
        expected.add(a);
        expected.add(b);
        expected.add(c);
        expected.add(d);
        List<Sub<BooleanEvent>> random = new ArrayList<>(expected);
        Collections.shuffle(random);
        List<Sub<BooleanEvent>> sorted = new ArrayList<>(new TreeSet<>(random));
        IntStream
            .range(0, 4)
            .forEach(i -> Assertions.assertEquals(expected.get(i), sorted.get(i)));
    }

}
