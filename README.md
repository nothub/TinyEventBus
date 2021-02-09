#### TinyEventBus

A tiny and fast pubsub implementation with sub priorities and event canceling.

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

Check the
[example](https://github.com/nothub/TinyEventBus/blob/master/src/test/java/cc/neckbeard/tinyeventbus/example/Example.java)
.
