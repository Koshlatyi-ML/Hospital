package util.load;

import util.init.Initializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ImplementationLoader {
    private List<Initializer> initializers;

    ImplementationLoader(List<Initializer> initializers) {
        this.initializers = initializers;
    }

    public <T> T loadInstance(Class<T> supertypeClass) {

        Implementation implementationAnnot = supertypeClass
                .getDeclaredAnnotation(Implementation.class);

        Class actualClass = implementationAnnot.value();
        if (!supertypeClass.isAssignableFrom(actualClass)) {
            throw new ClassCastException();
        }

        Constructor defaultConstructor;
        try {
            defaultConstructor = actualClass.getDeclaredConstructor((Class<?>[]) null);
        } catch (NoSuchMethodException e) {
            throw new NoDefaultConstructorException(e);
        }

        defaultConstructor.setAccessible(true);

        try {
            T t = (T) defaultConstructor.newInstance((Object[]) null);
            initializers.forEach(initializer -> initializer.initialize(t));
            return t;
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
