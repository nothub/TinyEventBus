package cc.neckbeard.tinypubsub.example;

import cc.neckbeard.tinypubsub.Bus;
import cc.neckbeard.tinypubsub.Cancellable;
import cc.neckbeard.tinypubsub.Sub;

public class Example {

    private static String msg = "";

    @Sub(prio = 100)
    public void first(Event e) {
        if (e.str.equalsIgnoreCase("foobar")) e.cancelled = true;
    }

    @Sub
    public void second(Event e) {
        msg = e.str;
    }

    void run() {

        final Bus bus = new Bus();

        bus.reg(this);
        bus.pub(new Event("Hello World!"));
        System.out.println(msg);

        bus.del(this);
        bus.pub(new Event("foobar"));
        System.out.println(msg);

    }

    private static class Main {
        public static void main(String[] args) {
            new Example().run();
        }
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

}
