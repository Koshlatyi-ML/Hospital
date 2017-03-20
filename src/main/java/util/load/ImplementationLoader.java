package util.load;

public class ImplementationLoader {
    private static ImplementationLoader instance = new ImplementationLoader();

    private ImplementationLoader(){}

    public static ImplementationLoader getInstance() {
        return instance;
    }

    public <T> T loadInstance(Class<T> supertypeClass) {

        Implementation implementationAnnot = supertypeClass
                .getDeclaredAnnotation(Implementation.class);

        Class<? extends T> clazz = implementationAnnot.value();
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
