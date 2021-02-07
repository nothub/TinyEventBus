package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.concurrent.atomic.AtomicBoolean;

@Execution(ExecutionMode.CONCURRENT)
class RegTest {

    private Sub<BooleanEvent> fieldSub;

    @Test
    void regAll() {
        AtomicBoolean invoked = new AtomicBoolean(false);
        fieldSub = new Sub<>(BooleanEvent.class, e -> invoked.set(e.bool));
        Bus bus = new Bus();
        bus.regFields(this);
        bus.pub(new BooleanEvent(true));
        Assertions.assertTrue(invoked.get());
        bus.unregFields(this);
        bus.pub(new BooleanEvent(false));
        Assertions.assertTrue(invoked.get());
    }

    @Test
    void reg() {
        AtomicBoolean invoked = new AtomicBoolean(false);
        Sub<BooleanEvent> sub = new Sub<>(BooleanEvent.class, e -> invoked.set(e.bool));
        Bus bus = new Bus();
        bus.reg(sub);
        bus.pub(new BooleanEvent(true));
        Assertions.assertTrue(invoked.get());
        bus.unreg(sub);
        bus.pub(new BooleanEvent(false));
        Assertions.assertTrue(invoked.get());
    }

}
