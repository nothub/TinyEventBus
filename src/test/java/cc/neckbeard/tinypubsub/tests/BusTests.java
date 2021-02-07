package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("bus")
@Execution(ExecutionMode.CONCURRENT)
class BusTests {

    final boolean[] invoked = {false};

    Sub<TestEvent> sub = new Sub<TestEvent>() {
        @Override
        public void on(TestEvent event) {
            invoked[0] = true;
        }
    };

    @Test
    @DisplayName("invoke")
    void invoke() {
        Bus bus = new Bus();
        bus.reg(sub, TestEvent.class);
        bus.pub(new TestEvent());
        Assertions.assertTrue(invoked[0]);
    }

}
