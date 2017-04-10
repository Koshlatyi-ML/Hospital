package util.init;

import util.ReflectionUtils;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Arrays;

public class JndiInitializer implements Initializer {
    private Context context;

    JndiInitializer(Context context) {
        this.context = context;
    }

    @Override
    public <T> void initialize(T object) {
        Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> ReflectionUtils.hasDeclaredAnnotation(field, Jndi.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    String path = field.getDeclaredAnnotation(Jndi.class).value();
                    try {
                        field.set(object, context.lookup(path));
                    } catch (NamingException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
