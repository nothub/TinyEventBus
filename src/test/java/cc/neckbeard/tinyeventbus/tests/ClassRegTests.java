package cc.neckbeard.tinyeventbus.tests;

import cc.neckbeard.tinyeventbus.Bus;
import cc.neckbeard.tinyeventbus.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings({"InnerClassMayBeStatic"})
@Execution(ExecutionMode.SAME_THREAD)
class ClassRegTests {

    private static Bus bus = new Bus();
    private static int hits;

    private Sub<BooleanEvent> subPriv = Sub.of(e -> hits++);
    protected Sub<BooleanEvent> subProt = Sub.of(e -> hits++);
    Sub<BooleanEvent> sub = Sub.of(e -> hits++);
    private static Sub<BooleanEvent> subStaticPriv = Sub.of(e -> hits++);
    protected static Sub<BooleanEvent> subStaticProt = Sub.of(e -> hits++);
    static Sub<BooleanEvent> subStatic = Sub.of(e -> hits++);

    @BeforeEach
    void setUp() {
        hits = 0;
    }

    @Test
    void clazz() {
        bus.reg(this);
        bus.pub(new BooleanEvent(true));
        Assertions.assertEquals(6, hits);
        bus.del(this);
        bus.pub(new BooleanEvent(true));
        Assertions.assertEquals(6, hits);
    }

    @Test
    void inner() {
        InnerClassPriv innerClassPriv = new InnerClassPriv();
        InnerClassProt innerClassProt = new InnerClassProt();
        InnerClass InnerClass = new InnerClass();
        InnerFinalClass innerFinalClass = new InnerFinalClass();
        InnerStaticClassPriv innerStaticClassPriv = new InnerStaticClassPriv();
        InnerStaticClassProt innerStaticClassProt = new InnerStaticClassProt();
        bus.reg(innerClassPriv);
        bus.reg(innerClassProt);
        bus.reg(InnerClass);
        bus.reg(innerFinalClass);
        bus.reg(innerStaticClassPriv);
        bus.reg(innerStaticClassProt);
        bus.pub(new BooleanEvent(true));
        Assertions.assertEquals(18, hits);
        bus.del(innerClassPriv);
        bus.del(innerClassProt);
        bus.del(InnerClass);
        bus.del(innerFinalClass);
        bus.del(innerStaticClassPriv);
        bus.del(innerStaticClassProt);
        bus.pub(new BooleanEvent(true));
        Assertions.assertEquals(18, hits);
    }

    private class InnerClassPriv {
        private Sub<BooleanEvent> subPriv = Sub.of(e -> hits++);
        protected Sub<BooleanEvent> subProt = Sub.of(e -> hits++);
        Sub<BooleanEvent> sub = Sub.of(e -> hits++);
    }

    protected class InnerClassProt {
        private Sub<BooleanEvent> subPriv = Sub.of(e -> hits++);
        protected Sub<BooleanEvent> subProt = Sub.of(e -> hits++);
        Sub<BooleanEvent> sub = Sub.of(e -> hits++);
    }

    class InnerClass {
        private Sub<BooleanEvent> subPriv = Sub.of(e -> hits++);
        protected Sub<BooleanEvent> subProt = Sub.of(e -> hits++);
        Sub<BooleanEvent> sub = Sub.of(e -> hits++);
    }

    final class InnerFinalClass {
        private Sub<BooleanEvent> subPriv = Sub.of(e -> hits++);
        protected Sub<BooleanEvent> subProt = Sub.of(e -> hits++);
        Sub<BooleanEvent> sub = Sub.of(e -> hits++);
    }

    private static class InnerStaticClassPriv {
        private Sub<BooleanEvent> subPriv = Sub.of(e -> hits++);
        protected Sub<BooleanEvent> subProt = Sub.of(e -> hits++);
        Sub<BooleanEvent> sub = Sub.of(e -> hits++);
    }

    protected static class InnerStaticClassProt {
        private Sub<BooleanEvent> subPriv = Sub.of(e -> hits++);
        protected Sub<BooleanEvent> subProt = Sub.of(e -> hits++);
        Sub<BooleanEvent> sub = Sub.of(e -> hits++);
    }

}
