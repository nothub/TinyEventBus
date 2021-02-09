A tiny and fast pubsub implementation with priorities and canceling.

Check the
[example](https://github.com/nothub/TinyEventBus/blob/master/src/test/java/cc/neckbeard/tinypubsub/example/Example.java)
for a usage snippet.

---

[benchmarks](https://github.com/nothub/TinyEventBus/tree/master/src/test/java/cc/neckbeard/tinypubsub/benchmark) (
openjdk 8, xeon e3-1230 3.30ghz):

```
pub
subs: 1_000
pubs: 1_000
47,681,726ns (47ms)
```

```
pub
subs: 100
pubs: 100_000
58,357,624ns (58ms)
```

```
reg
subs: 1_000
2,740,465ns (2ms)
```

```
del
subs: 1_000
7,297,579ns (7ms)
```
