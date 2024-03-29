# TinyEventBus

[![maven central](https://maven-badges.herokuapp.com/maven-central/lol.hub/TinyEventBus/badge.svg)](https://search.maven.org/artifact/lol.hub/TinyEventBus) [![LGTM](https://img.shields.io/lgtm/grade/java/github/nothub/TinyEventBus?label=code%20quality&logo=lgtm)](https://lgtm.com/projects/g/nothub/TinyEventBus)

Tiny and fast pubsub implementation with subscriber priorities and event canceling for Java 8, 11 and 17.

---

###### usage

```java
void run() {
    Bus bus = new Bus();
    bus.reg(Sub.of(String.class, System.out::println));
    bus.pub("Hello World!");
}
```

```java
class Listenable {
    Sub<Long> sub = Sub.of(Long.class, l -> Foo.bar(l));
    void run() {
        Bus bus = new Bus();
        bus.reg(this);
        bus.pub(42L);
    }
}
```

For more explanation, check
the [example](https://github.com/nothub/TinyEventBus/blob/master/src/test/java/lol/hub/tinyeventbus/example/Example.java)
.
