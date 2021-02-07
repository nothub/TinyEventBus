###### register event consumer

```java
class Example {
    void example() {
        Consumer<BooleanEvent> consumer = System.out::println;
        Sub<BooleanEvent> sub = new Sub<>(BooleanEvent.class, consumer);
        Bus bus = new Bus();
        bus.reg(sub);
        bus.pub(new BooleanEvent(true));
    }
}
```

---

###### register consumer fields

```java
class Example {
    boolean invoked = false;
    Sub<BooleanEvent> sub = new Sub<>(BooleanEvent.class, e -> invoked = e.bool);

    void example() {
        Bus bus = new Bus();
        bus.regFields(this);
        bus.pub(new BooleanEvent(true));
    }
}
```

---

###### listen to canceled events

```java
public class EventCanceledEvent extends Event {
    Event e;

    public EventCanceledEvent(Event e) {
        this.e = e;
    }
}
```
