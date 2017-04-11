package util.init;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ReflectionUtils;

import javax.naming.Context;
import javax.naming.NamingException;
import java.lang.reflect.Field;
import java.util.Arrays;

public class JndiInitializer implements Initializer {

    private Context context;
    private static final Logger LOG = LogManager.getLogger(JndiInitializer.class);

    JndiInitializer(Context context) {
        this.context = context;
    }

    @Override
    public <T> void initialize(T object) {
        Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> ReflectionUtils.hasDeclaredAnnotation(field, Jndi.class))
                .forEach(field -> setJndiResource(object, field));
    }

    private void setJndiResource(Object object, Field field) {
        String path = field.getDeclaredAnnotation(Jndi.class).value();
        field.setAccessible(true);
        try {
            field.set(object, context.lookup(path));
        } catch (NamingException | IllegalAccessException e) {
            LOG.log(Level.ERROR, "Can't set the JNDI resource value", e);
            throw new InitializerException(e);
        }
    }
}
