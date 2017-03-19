package util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public class ReflectionUtils {
    public static <T extends Annotation> boolean hasDeclaredAnnotation(
            Field field, Class<T> annotation) {

        return Stream.of(field.getDeclaredAnnotations())
                .anyMatch(a -> annotation.isAssignableFrom(a.getClass()));

    }
}
