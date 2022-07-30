package lol.hub.tinyeventbus.example;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Cancelable;
import lol.hub.tinyeventbus.Sub;

import java.util.function.Consumer;

class Example {

    // create event consumer
    final Consumer<Event> eventConsumer = e -> System.out.println(e.str);
    // create subscriber
    Sub<Event> sub = new Sub<>(Event.class, eventConsumer);
    // create subscriber with inline consumer
    Sub<Event> prioSub = new Sub<>(Event.class, e -> {
        if (e.str.equals("foobar")) e.canceled = true;
    }, 50); // priority is defined as integer
    // create subscriber with convenience method
    Sub<Event> highPrioSub = Sub.of(Event.class, e -> e.str = ":3", 100); // higher priority comes first

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

    static class Event implements Cancelable {

        String str;
        boolean canceled;

        Event(String str) {
            this.str = str;
        }

        @Override
        public boolean isCanceled() {
            return canceled;
        }

    }

    static class Main {
        public static void main(String[] args) {
            new Example().run();
        }
    }
}
