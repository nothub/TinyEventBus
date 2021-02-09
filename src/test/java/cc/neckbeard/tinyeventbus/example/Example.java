package cc.neckbeard.tinyeventbus.example;

import cc.neckbeard.tinyeventbus.Bus;
import cc.neckbeard.tinyeventbus.Sub;

import java.util.function.Consumer;

class Example {

    // create event consumer
    final Consumer<Event> eventConsumer = e -> System.out.println(e.str);
    // create subscriber
    Sub<Event> sub = new Sub<>(eventConsumer);

    // create subscriber with inline consumer
    Sub<Event> prioSub = new Sub<>(e -> {
        if (e.str.equals("foobar")) e.canceled = true;
    }, 50); // priority is defined as integer

    // create subscriber with convenience method
    Sub<Event> highPrioSub = Sub.of(e -> e.str = ":3", 100); // higher priority comes first

    void run() {

        // create bus instance
        Bus bus = new Bus();

        // register subscriber
        bus.reg(sub);
        // publish event
        bus.pub(new Event("Hello World!"));
        // prints: Hello World!

        bus.reg(prioSub);
        bus.pub(new Event("foobar"));
        // event is canceled

        bus.reg(highPrioSub);
        bus.pub(new Event("foobar"));
        // prints: :3

    }

}
