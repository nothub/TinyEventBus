package cc.neckbeard.tinypubsub.tests;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OrderTests {

    private static Bus bus;
    private static String str;

    @BeforeAll
    static void setUp() {
        bus = new Bus();
        str = "";
    }

    Sub<Object> subC1 = new Sub<>(0, e -> str += "C");
    Sub<Object> subC2 = new Sub<>(0, e -> str += "C");
    Sub<Object> subE = new Sub<>(-2, e -> str += "E");
    Sub<Object> subA = new Sub<>(2, e -> str += "A");
    Sub<Object> subB = new Sub<>(1, e -> str += "B");
    Sub<Object> subD = new Sub<>(-1, e -> str += "D");

    @Test
    void order() {
        bus.reg(subC1);
        bus.reg(subC2);
        bus.reg(subE);
        bus.reg(subA);
        bus.reg(subB);
        bus.reg(subD);
        bus.pub(new Object());
        Assertions.assertEquals("ABCCDE", str);
    }

}
