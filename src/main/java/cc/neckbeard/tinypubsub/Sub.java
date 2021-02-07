package cc.neckbeard.tinypubsub;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Sub {
    int prio() default 0;
}
