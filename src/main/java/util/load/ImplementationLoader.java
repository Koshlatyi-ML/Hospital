package util.load;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.init.Initializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ImplementationLoader {

    private List<Initializer> initializers;
    private static final Logger LOG = LogManager.getLogger(ImplementationLoader.class);

    ImplementationLoader(List<Initializer> initializers) {
        this.initializers = initializers;
    }

    public <T> T loadInstance(Class<T> supertypeClass) {
        Class<T> actualClass = getVerifiedActualClass(supertypeClass);
        Constructor<T> defaultConstructor = getAccessibleDefaultConstructor(actualClass);
        try {
            T t = defaultConstructor.newInstance((Object[]) null);
            initializers.forEach(initializer -> initializer.initialize(t));
            return t;
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException e) {

            LOG.log(Level.ERROR, "Can't create implementation instance " +
                    "via it's default constructor");
            throw new ImplementationLoaderException(e);
        }
    }

    private Class getVerifiedActualClass(Class<?> annotationHolder) {
        Class actualClass = annotationHolder
                .getDeclaredAnnotation(Implementation.class)
                .value();

        if (!annotationHolder.isAssignableFrom(actualClass)) {
            LOG.log(Level.ERROR, "Can't cast class defined in Implementation annotation " +
                    "to the declared supertype class");
            throw new ImplementationLoaderException(new ClassCastException());
        }

        return actualClass;
    }

    private <T> Constructor<T> getAccessibleDefaultConstructor(Class<T> clazz) {
        Constructor<T> defaultConstructor;
        try {
            defaultConstructor = clazz.getDeclaredConstructor((Class<?>[]) null);
        } catch (NoSuchMethodException e) {
            LOG.log(Level.ERROR, "There no default constructor present in class, " +
                    "specified in Implementation annotation");
            throw new ImplementationLoaderException(e);
        }

        defaultConstructor.setAccessible(true);
        return defaultConstructor;
    }
}
