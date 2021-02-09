package cc.neckbeard.tinyeventbus.tests;

import cc.neckbeard.tinyeventbus.Bus;
import cc.neckbeard.tinyeventbus.Sub;
import net.jodah.concurrentunit.Waiter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@Execution(ExecutionMode.CONCURRENT)
class ConcurrencyTest {

    private final Bus bus = new Bus();
    private final Waiter waiter = new Waiter();

    private int hitsA = 0;
    private int hitsB = 0;
    private int hitsC = 0;
    private int hitsD = 0;

    private final Sub<Integer> sub = Sub.of(i -> {
        waiter.assertEquals(i, hitsB);
        hitsB++;
        hitsD++;
        waiter.assertTrue(hitsC < hitsD);
        waiter.resume();
    });

    @Test
    void test() throws TimeoutException, InterruptedException {
        bus.reg(sub);
        IntStream.range(0, 1000).forEach(i -> {
            bus.pub(hitsA);
            hitsA++;
            hitsC++;
        });
        waiter.await(1000, 1000);
    }

    @Test
    void spam() {
        AtomicBoolean invoked = new AtomicBoolean(false);
        bus.reg(Sub.of(invoked::set));
        IntStream.range(0, 1000).forEach(i -> {
            bus.pub(true);
            Assertions.assertTrue(invoked.get());
            invoked.set(false);
        });
    }

}
