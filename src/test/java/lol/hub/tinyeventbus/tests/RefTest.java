package lol.hub.tinyeventbus.tests;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Sub;
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
        bus.reg(new Sub<TestEvent>(TestEvent.class, e -> e.cancel()));
        final TestEvent evA = new TestEvent();
        bus.pub(evA);
        Assertions.assertTrue(evA.isCanceled());
    }

    @Test
    void simpleOf() {
        //noinspection Convert2MethodRef
        Sub<TestEvent> sub = Sub.of(TestEvent.class, e -> e.cancel());
        bus.reg(sub);
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void simpleOfInline() {
        //noinspection Convert2MethodRef
        bus.reg(Sub.<TestEvent>of(TestEvent.class, e -> e.cancel()));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refNonWonky() {
        bus.reg(new Sub<>(ToggleEvent.class, ToggleEvent::flip));
        final ToggleEvent ev = new ToggleEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.state);
    }

    @Test
    void refWonky() {
        bus.reg(new Sub<>(ToggleEvent.class, ToggleEvent::cancel));
        final ToggleEvent ev = new ToggleEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void ref() {
        bus.reg(new Sub<>(TestEvent.class,TestEvent::cancel));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refCancelableEvent() {
        bus.reg(new Sub<TestEvent>(TestEvent.class, CancelableEvent::cancel));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refOf() {
        Sub<TestEvent> sub = Sub.of(TestEvent.class, TestEvent::cancel);
        bus.reg(sub);
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refOfCancelableEvent() {
        Sub<TestEvent> sub = Sub.of(TestEvent.class, CancelableEvent::cancel);
        bus.reg(sub);
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refOfInline() {
        bus.reg(Sub.of(TestEvent.class, TestEvent::cancel));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

    @Test
    void refOfInlineCancelableEvent() {
        bus.reg(Sub.<TestEvent>of(TestEvent.class, CancelableEvent::cancel));
        final TestEvent ev = new TestEvent();
        bus.pub(ev);
        Assertions.assertTrue(ev.isCanceled());
    }

}
