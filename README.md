# TinyEventBus

[![maven central](https://maven-badges.herokuapp.com/maven-central/cc.neckbeard/TinyEventBus/badge.svg)](https://search.maven.org/artifact/cc.neckbeard/TinyEventBus) [![LGTM](https://img.shields.io/lgtm/grade/java/github/nothub/TinyEventBus?label=code%20quality&logo=lgtm)](https://lgtm.com/projects/g/nothub/TinyEventBus)

Tiny and fast pubsub implementation with subscriber priorities and event canceling for Java 8, 11 and 17.

---

###### usage

```java
void run() {
    Bus bus = new Bus();
    bus.reg(Sub.of(System.out::println));
    bus.pub("Hello World!");
}
```

```java
class Listenable {
    Sub<Long> sub = Sub.of(l -> ThreadLocalRandom.current().setSeed(l));

    void run() {
        Bus bus = new Bus();
        bus.reg(this);
        bus.pub(42L);
    }
}
```

For more explanation, check the [example](https://github.com/nothub/TinyEventBus/blob/master/src/test/java/lol/hub/tinyeventbus/example/Example.java).

---

###### reference lambdas

Reference lambdas invoking a function, being derived by an interface implemented by an extended superclass of the event tend to cause type confusion.

The following example will not work as intended:
```java
abstract class CancelableEvent implements Cancelable {
    ...

class SomeEvent extends CancelableEvent {
    ...

Sub<SomeEvent> sub = Sub.of(SomeEvent::cancel);
```

To ensure the correctness of the event type, use one of the following patterns:

```java
Sub<SomeEvent> sub = Sub.of(e -> e.cancel());
Sub<SomeEvent> sub = Sub.of(SomeEvent::cancel, SomeEvent.class);
```

For more information please check: [ref-example](https://github.com/nothub/TinyEventBus/blob/master/src/test/java/lol/hub/tinyeventbus/example/RefExample.java)
