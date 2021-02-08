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

    @Sub
    public void subC1(Object ignored) {
        str += "C";
    }

    @Sub
    public void subC2(Object ignored) {
        str += "C";
    }

    @Sub(prio = -2)
    public void subE(Object ignored) {
        str += "E";
    }

    @Sub(prio = 2)
    public void subA(Object ignored) {
        str += "A";
    }

    @Sub(prio = 1)
    public void subB(Object ignored) {
        str += "B";
    }

    @Sub(prio = -1)
    public void subD(Object ignored) {
        str += "D";
    }

    @Test
    void order() {
        bus.reg(this);
        bus.pub(new Object());
        Assertions.assertEquals("ABCCDE", str);
    }

}
