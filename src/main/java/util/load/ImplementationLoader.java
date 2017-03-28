package util.load;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ImplementationLoader {
    private static ImplementationLoader instance = new ImplementationLoader();

    private ImplementationLoader() {
    }

    public static ImplementationLoader getInstance() {
        return instance;
    }

    public <T> T loadInstance(Class<T> supertypeClass) {

        Implementation implementationAnnot = supertypeClass
                .getDeclaredAnnotation(Implementation.class);

        Class<? extends T> value = implementationAnnot.value();
        Constructor<? extends T> defaultConstructor;
        try {
            defaultConstructor = value.getDeclaredConstructor((Class<?>[]) null);
        } catch (NoSuchMethodException e) {
            throw new NoDefaultConstructorException(e);
        }

        defaultConstructor.setAccessible(true);

        try {
            return defaultConstructor.newInstance((Object[]) null);
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
