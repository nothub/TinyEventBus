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
        bus.unreg(this);
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
        bus.reg(InnerStaticClassPriv2.subPriv);
        bus.reg(InnerStaticClassPriv2.subProt);
        bus.reg(InnerStaticClassPriv2.sub);
        bus.reg(InnerStaticClassProt2.subPriv);
        bus.reg(InnerStaticClassProt2.subProt);
        bus.reg(InnerStaticClassProt2.sub);
        bus.pub(new BooleanEvent(true));
        Assertions.assertEquals(24, hits);
        bus.unreg(innerClassPriv);
        bus.unreg(innerClassProt);
        bus.unreg(InnerClass);
        bus.unreg(innerFinalClass);
        bus.unreg(innerStaticClassPriv);
        bus.unreg(innerStaticClassProt);
        bus.unreg(InnerStaticClassPriv2.subPriv);
        bus.unreg(InnerStaticClassPriv2.subProt);
        bus.unreg(InnerStaticClassPriv2.sub);
        bus.unreg(InnerStaticClassProt2.subPriv);
        bus.unreg(InnerStaticClassProt2.subProt);
        bus.unreg(InnerStaticClassProt2.sub);
        bus.pub(new BooleanEvent(true));
        Assertions.assertEquals(24, hits);
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

    private static class InnerStaticClassPriv2 {
        private static final Sub<BooleanEvent> subPriv = Sub.of(e -> hits++);
        protected static Sub<BooleanEvent> subProt = Sub.of(e -> hits++);
        static Sub<BooleanEvent> sub = Sub.of(e -> hits++);
    }

    protected static class InnerStaticClassProt2 {
        private static final Sub<BooleanEvent> subPriv = Sub.of(e -> hits++);
        protected static Sub<BooleanEvent> subProt = Sub.of(e -> hits++);
        static Sub<BooleanEvent> sub = Sub.of(e -> hits++);
    }

}
