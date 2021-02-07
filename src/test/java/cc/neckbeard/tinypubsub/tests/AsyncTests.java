package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.stream.IntStream;

@Execution(ExecutionMode.CONCURRENT)
class AsyncTests {

    int runs = 0;

    Sub<BooleanEvent> a = new Sub<>(0, BooleanEvent.class, e -> runs++);
    Sub<BooleanEvent> b = new Sub<>(1, BooleanEvent.class, e -> runs++);
    Sub<BooleanEvent> c = new Sub<>(2 , BooleanEvent.class, e -> runs++);

    @Test
    @DisplayName("unreg while pub")
    void unreg() throws InterruptedException {

        Bus bus = new Bus();
        bus.regFields(this);

        Thread t1 = new Thread(() -> IntStream
            .range(0, 1000000)
            .forEach(i -> bus.pub(new BooleanEvent(true))));

        Thread t2 = new Thread(() -> IntStream
            .range(0, 10000)
            .forEach(i -> {
                bus.unregFields(this);
                bus.reg(a);
            }));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        Assertions.assertTrue(1000000 * 3 > runs);

    }

}
