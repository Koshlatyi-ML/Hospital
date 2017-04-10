package util.init;

@FunctionalInterface
public interface Initializer {
    <T> void initialize(T object);
}
