package lol.hub.tinyeventbus.tests;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Sub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OrderTest {

    private static Bus bus;
    private static String str;

    Sub<Object> subC1 = new Sub<>(Object.class, e -> str += "C");
    Sub<Object> subC2 = Sub.of(Object.class, e -> str += "C");
    Sub<Object> subE = new Sub<>(Object.class, e -> str += "E", -2);
    Sub<Object> subA = Sub.of(Object.class, e -> str += "A", 2);
    Sub<Object> subB = Sub.of(Object.class, e -> str += "B", 1);
    Sub<Object> subD = new Sub<>(Object.class, e -> str += "D", -1);

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
