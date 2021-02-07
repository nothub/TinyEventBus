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
class RegAllTests {

    boolean invoked = false;

    Sub<BooleanEvent> sub = new Sub<>(BooleanEvent.class, e -> invoked = e.bool);

    @Test
    @DisplayName("invoke")
    void invoke() {
        Bus bus = new Bus();
        bus.regAll(this);
        bus.pub(new BooleanEvent(true));
        Assertions.assertTrue(invoked);
    }

}
