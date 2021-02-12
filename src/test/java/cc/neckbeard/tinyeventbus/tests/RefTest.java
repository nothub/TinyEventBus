package cc.neckbeard.tinyeventbus.tests;

import cc.neckbeard.tinyeventbus.Bus;
import cc.neckbeard.tinyeventbus.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
class RefTest {

    private final Bus bus = new Bus();

    @Test
    void simple() {
        //noinspection Convert2MethodRef
        bus.reg(new Sub<TestEvent>(e -> e.cancel()));
        final TestEvent evA = new TestEvent();
        bus.pub(evA);
        Assertions.assertTrue(evA.isCanceled());
    }

    @Test
    void simpleOf() {
        //noinspection Convert2MethodRef
        Sub<TestEvent> sub = Sub.of(e -> e.cancel());
        bus.reg(sub);
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void simpleOfInline() {
        //noinspection Convert2MethodRef
        bus.reg(Sub.<TestEvent>of(e -> e.cancel()));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void ref() {
        bus.reg(new Sub<>(TestEvent::cancel));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refCancelableEvent() {
        bus.reg(new Sub<TestEvent>(CancelableEvent::cancel));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refOf() {
        Sub<TestEvent> sub = Sub.of(TestEvent::cancel);
        bus.reg(sub);
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refOfCancelableEvent() {
        Sub<TestEvent> sub = Sub.of(CancelableEvent::cancel);
        bus.reg(sub);
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refOfInline() {
        bus.reg(Sub.of(TestEvent::cancel));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refOfInlineCancelableEvent() {
        bus.reg(Sub.<TestEvent>of(CancelableEvent::cancel));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

}
