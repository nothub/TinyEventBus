#### TinyEventBus

[![maven central](https://maven-badges.herokuapp.com/maven-central/cc.neckbeard/TinyEventBus/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cc.neckbeard/TinyEventBus)

A tiny and fast pubsub implementation with subscriber priorities and event canceling.

---

###### use

```java
void run() {
    Bus bus=new Bus();
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
[example](https://github.com/nothub/TinyEventBus/blob/master/src/test/java/cc/neckbeard/tinyeventbus/example/Example.java).

---

###### get

```xml
<repositories>
    <repository>
        <id>neckbeard</id>
        <url>https://maven.neckbeard.cc/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>cc.neckbeard</groupId>
        <artifactId>TinyEventBus</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```

Or use [jitpack](https://jitpack.io/#nothub/TinyEventBus).
