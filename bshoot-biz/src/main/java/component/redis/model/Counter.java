package component.redis.model;

import java.lang.annotation.*;

/**
 * Created by zhou on 2016/1/14.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Counter {
    public CounterType value();
}
