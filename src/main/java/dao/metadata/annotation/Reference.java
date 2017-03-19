package dao.metadata.annotation;

import java.lang.annotation.*;

@Repeatable(value = References.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Reference {
    Class table();
}
