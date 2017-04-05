package domain.dto;

import java.util.Optional;

public abstract class AbstractPersonDTO extends AbstractDTO {
    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Optional.ofNullable(name)
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = Optional.ofNullable(surname)
                .orElseThrow(IllegalArgumentException::new);
    }

    public abstract static class Builder<T extends AbstractPersonDTO, B extends Builder>
            extends AbstractDTO.Builder<T, B> {

        public B setName(String name) {
            instance.setName(name);
            return getSelf();
        }

        public B setSurname(String surname) {
            instance.setSurname(surname);
            return getSelf();
        }

        @Override
        protected abstract B getSelf();
    }
}
