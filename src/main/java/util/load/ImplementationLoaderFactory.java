package util.load;

import util.init.JndiInitializerFactory;

import java.util.Collections;

public class ImplementationLoaderFactory {
    private ImplementationLoader implementationLoader;

    private static class Holder {
        static final ImplementationLoaderFactory INSTANCE = new ImplementationLoaderFactory();
    }

    private ImplementationLoaderFactory() {
        implementationLoader = new ImplementationLoader(Collections.singletonList(
                JndiInitializerFactory.getInstance().getJndiInitializer()));
    }

    public static ImplementationLoaderFactory getInstance() {
        return Holder.INSTANCE;
    }

    public ImplementationLoader createImplementationLoader() {
        return implementationLoader;
    }
}
