package lol.hub.tinyeventbus.example;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Cancelable;
import lol.hub.tinyeventbus.Sub;

class ReferenceLambdaExample {

    Sub<TestEvent> consumer = Sub.of(TestEvent.class, TestEvent::cancel);

    void run() {

        // create bus instance
        Bus bus = new Bus();

        bus.reg(consumer);
        final TestEvent evB = new TestEvent();
        bus.pub(evB);

        System.out.println(evB.canceled);
        // prints: true

    }

    abstract static class CancelableEvent implements Cancelable {

        boolean canceled;

        CancelableEvent() {
        }

        public void cancel() {
            this.canceled = true;
        }

        @Override
        public boolean isCanceled() {
            return canceled;
        }

    }

    public static class TestEvent extends CancelableEvent {
    }

    static class Main {
        public static void main(String[] args) {
            new ReferenceLambdaExample().run();
        }
    }
}
