package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Event;
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
    @DisplayName("order by prio")
    void order() {
        Sub<Event> a = new Sub<Event>(1) {
            @Override
            public void on(Event event) {
            }
        };
        Sub<Event> b = new Sub<Event>(0) {
            @Override
            public void on(Event event) {
            }
        };
        Sub<Event> c = new Sub<Event>(-1) {
            @Override
            public void on(Event event) {
            }
        };
        Sub<Event> d = new Sub<Event>(-2) {
            @Override
            public void on(Event event) {
            }
        };
        Sub<Event> e = new Sub<Event>(-3) {
            @Override
            public void on(Event event) {
            }
        };
        List<Sub<Event>> expected = new ArrayList<>();
        expected.add(a);
        expected.add(b);
        expected.add(c);
        expected.add(d);
        expected.add(e);
        List<Sub<Event>> random = new ArrayList<>(expected);
        Collections.shuffle(random);
        List<Sub<Event>> sorted = new ArrayList<>(new TreeSet<>(random));
        IntStream
            .range(0, 5)
            .forEach(i -> Assertions.assertEquals(expected.get(i), sorted.get(i)));
    }

}
