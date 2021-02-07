package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.stream.IntStream;

@DisplayName("async")
@Execution(ExecutionMode.CONCURRENT)
class AsyncTests {

    int runs = 0;

    Sub<BooleanEvent> a = new Sub<BooleanEvent>(2, BooleanEvent.class) {
        @Override
        public void on(BooleanEvent event) {
            runs++;
        }
    };
    Sub<BooleanEvent> b = new Sub<BooleanEvent>(1, BooleanEvent.class) {
        @Override
        public void on(BooleanEvent event) {
            runs++;
        }
    };
    Sub<BooleanEvent> c = new Sub<BooleanEvent>(BooleanEvent.class) {
        @Override
        public void on(BooleanEvent event) {
            runs++;
        }
    };

    @Test
    @DisplayName("unreg while pushing")
    void unreg() throws InterruptedException {

        Bus bus = new Bus();
        bus.regAll(this);

        Thread t1 = new Thread(() -> IntStream
            .range(0, 10500)
            .forEach(i -> bus.pub(new BooleanEvent(true))));

        Thread t2 = new Thread(() -> IntStream
            .range(0, 10)
            .forEach(i -> {
                bus.unregAll(this);
                bus.reg(a);
            }));

        t1.start();
        t2.start();

        t1.join(100000);
        t2.join(100000);

        Assertions.assertNotEquals(10500 * 3, runs);

    }

}
