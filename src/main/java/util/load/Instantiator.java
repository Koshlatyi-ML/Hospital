package util.load;

public class Instantiator {
    private static Instantiator instance = new Instantiator();

    private Instantiator(){}

    public static Instantiator getInstance() {
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
