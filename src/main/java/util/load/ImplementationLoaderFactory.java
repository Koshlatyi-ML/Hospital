package util.load;

import util.load.init.JndiInitializerFactory;

import java.util.Collections;

public class ImplementationLoaderFactory {
    private ImplementationLoader implementationLoader;

    private static class Holder {
        static final ImplementationLoaderFactory INSTANCE = new ImplementationLoaderFactory();
    }

    public static ImplementationLoaderFactory getInstance() {
        return Holder.INSTANCE;
    }

    private ImplementationLoaderFactory() {
        implementationLoader =
                new ImplementationLoader(
                        Collections.singletonList(
                                JndiInitializerFactory.getInstance().createJndiInitializer()));
    }

    public ImplementationLoader createImplementationLoader() {
        return implementationLoader;
    }
}
