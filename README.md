#### TinyEventBus

[![maven central](https://maven-badges.herokuapp.com/maven-central/cc.neckbeard/TinyEventBus/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cc.neckbeard/TinyEventBus)

A tiny and fast pubsub implementation with subscriber priorities and event canceling for Java 8 and 11.

---

###### usage

```java
class Listenable {
    void run() {
        Bus bus = new Bus();
        bus.reg(Sub.of(System.out::println));
        bus.pub("Hello World!");
    }
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

For more explanation, check the [example](https://github.com/nothub/TinyEventBus/blob/master/src/test/java/cc/neckbeard/tinyeventbus/example/Example.java).

---

###### reference lambdas

```java
class Listenable {
    Sub<Long> sub = Sub.of(TestEvent::cancel);
}
```

Reference lambdas invoking a function of an extended superclass of the event tend to cause type confusion, for more information please check: [ref-example](https://github.com/nothub/TinyEventBus/blob/master/src/test/java/cc/neckbeard/tinyeventbus/example/RefExample.java)
