package lol.hub.tinyeventbus.example;

import lol.hub.tinyeventbus.Bus;
import lol.hub.tinyeventbus.Cancelable;
import lol.hub.tinyeventbus.Sub;

class RefExample {

    // TLDR: If TestEvent extends an implementation of Cancelable, called CancelableEvent in this example,
    // the parameter of: Sub.of(TestEvent::cancel)
    // will always be: Consumer<CancelableEvent>
    // workaround a: do not use the static ref: Sub.of(e -> e.cancel)
    // workaround b: supply the type manually: Sub.of(TestEvent::cancel, TestEvent.class)

    // This reference lambda will have a generic type of CancelableEvent at invocation.
    Sub<TestEvent> referenceLambdaSubBroken = Sub.of(TestEvent::cancel);

    // To enforce a certain type, it can be supplied as additional parameter.
    Sub<TestEvent> referenceLambdaSub = Sub.of(TestEvent::cancel, TestEvent.class);

    void run() {

        // create bus instance
        Bus bus = new Bus();

        // type breakage
        bus.reg(referenceLambdaSubBroken);
        final TestEvent evA = new TestEvent();
        bus.pub(evA);
        System.out.println(evA.canceled);
        // prints: false

        // workaround
        bus.reg(referenceLambdaSub);
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
            new RefExample().run();
        }
    }

}
