package cc.neckbeard.tinyeventbus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class Bus {

    private final Map<Class<?>, ConcurrentSkipListSet<Sub<?>>> subs = new ConcurrentHashMap<>();

    public void reg(Sub<?> sub) {
        subs
            .computeIfAbsent(sub.type, clazz -> new ConcurrentSkipListSet<>())
            .add(sub);
    }

    public void del(Sub<?> sub) {
        final ConcurrentSkipListSet<Sub<?>> subs = this.subs.get(sub.type);
        if (subs == null) return;
        subs.remove(sub);
    }

    public void pub(Object obj) {
        final ConcurrentSkipListSet<Sub<?>> subs = this.subs.get(obj.getClass());
        if (subs == null) return;
        subs.forEach(sub -> {
            if (obj instanceof Cancellable && ((Cancellable) obj).isCancelled()) return;
            sub.accept(obj);
        });
    }

}
