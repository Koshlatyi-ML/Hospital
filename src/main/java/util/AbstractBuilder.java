package util;

public abstract class AbstractBuilder<T> {
    protected T instance;
    public T build() {
        return instance;
    }
}
