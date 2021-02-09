A tiny and fast pubsub implementation with subscriber priorities and event canceling.

Check the
[example](https://github.com/nothub/TinyEventBus/blob/master/src/test/java/cc/neckbeard/tinyeventbus/example/Example.java)
for a usage snippet.

---

[benchmarks](https://github.com/nothub/TinyEventBus/blob/1.0.0/src/test/java/cc/neckbeard/tinyeventbus/tests/BenchmarkTests.java) done in github ci:

```
benchmark: bus.reg
subs: 1_000
6,021,031ns (6ms)

benchmark: bus.reg
subs: 10_000
12,062,863ns (12ms)

benchmark: bus.del
subs: 1_000
3,801,683ns (3ms)

benchmark: bus.del
subs: 10_000
14,857,024ns (14ms)

benchmark: bus.pub
pubs: 1_000
subs: 1_000
41,483,604ns (41ms)

benchmark: bus.pub
pubs: 100
subs: 10_000
31,109,278ns (31ms)

benchmark: bus.pub
pubs: 10_000
subs: 100
28,044,711ns (28ms)
```
