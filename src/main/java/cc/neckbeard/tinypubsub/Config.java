package cc.neckbeard.tinypubsub;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// TODO: @DarkiBoi

@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    int prio() default 0;
    Class<? extends Event> type();
}
