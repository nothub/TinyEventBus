package cc.neckbeard.tinyeventbus.example;

import cc.neckbeard.tinyeventbus.Bus;
import cc.neckbeard.tinyeventbus.Cancellable;
import cc.neckbeard.tinyeventbus.Sub;

public class Example {

    private static String msg = "";

    Sub<Event> first = new Sub<>( e -> msg = e.str);

    Sub<Event> second = new Sub<>(1, e -> {
        if (e.str.equals("foobar")) e.cancelled = true;
    });

    void run() {

        final Bus bus = new Bus();

        bus.reg(first);
        bus.pub(new Event("Hello World!"));
        System.out.println(msg);

        bus.reg(second);
        bus.pub(new Event("foobar"));
        System.out.println(msg);

    }

    private static class Event implements Cancellable {

        public final String str;
        public boolean cancelled;

        public Event(String str) {
            this.str = str;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

    }

    private static class Main {
        public static void main(String[] args) {
            new Example().run();
        }
    }

}
