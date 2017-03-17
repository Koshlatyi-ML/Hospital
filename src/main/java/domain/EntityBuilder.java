package domain;

public abstract class EntityBuilder<T> {
    protected T instance;
    public T build() {
        return instance;
    }
}
