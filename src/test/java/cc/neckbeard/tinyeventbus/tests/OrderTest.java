package cc.neckbeard.tinyeventbus.tests;

import cc.neckbeard.tinyeventbus.Bus;
import cc.neckbeard.tinyeventbus.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OrderTest {

    private static Bus bus;
    private static String str;

    Sub<Object> subC1 = new Sub<>(e -> str += "C");
    Sub<Object> subC2 = Sub.of(e -> str += "C");
    Sub<Object> subE = new Sub<>(e -> str += "E", -2);
    Sub<Object> subA = Sub.of(e -> str += "A", 2);
    Sub<Object> subB = Sub.of(e -> str += "B", 1);
    Sub<Object> subD = new Sub<>(e -> str += "D", -1);

    @BeforeAll
    static void setUp() {
        bus = new Bus();
        str = "";
    }

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
