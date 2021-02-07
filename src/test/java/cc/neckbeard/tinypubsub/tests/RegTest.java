package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Execution(ExecutionMode.CONCURRENT)
class RegTest {

    private Sub<BooleanEvent> fieldSub;

    @Test
    void regFields() {
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
    void regUnique() {
        AtomicInteger invoked = new AtomicInteger(0);
        Sub<BooleanEvent> sub1 = new Sub<>(BooleanEvent.class, e -> invoked.set(invoked.get() + 1));
        Sub<BooleanEvent> sub2 = new Sub<>(BooleanEvent.class, e -> invoked.set(invoked.get() + 1));
        Sub<BooleanEvent> sub3 = new Sub<>(BooleanEvent.class, e -> invoked.set(invoked.get() + 1));
        Bus bus = new Bus();
        bus.reg(sub1);
        bus.reg(sub2);
        bus.reg(sub2);
        bus.reg(sub3);
        bus.reg(sub3);
        bus.reg(sub3);
        bus.pub(new BooleanEvent(true));
        Assertions.assertEquals(3, invoked.get());
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
