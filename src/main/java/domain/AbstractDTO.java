package domain;

import util.AbstractBuilder;

public abstract class AbstractDTO {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract static class Builder<T extends AbstractDTO, B extends Builder>
            extends AbstractBuilder<T> {

        public B setId(long id) {
            instance.setId(id);
            return getSelf();
        }

        protected abstract B getSelf();
    }
}
