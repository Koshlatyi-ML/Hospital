package domain;

import dao.metadata.annotation.mapping.Id;
import util.AbstractBuilder;

import java.util.Objects;

public abstract class IdHolder {
    @Id("id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static class Builder<T extends IdHolder, B extends Builder>
            extends AbstractBuilder<T> {

        protected Builder(T idHolder) {
            instance = idHolder;
        }

        public Builder setId(long id) {
            instance.setId(id);
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdHolder)) return false;
        IdHolder idHolder = (IdHolder) o;
        return getId() == idHolder.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
