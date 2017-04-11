package domain;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractPersonDTO extends AbstractDTO {

    private String name;
    private String surname;
    private static final Logger LOG = LogManager.getLogger(AbstractPersonDTO.class);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            LOG.log(Level.ERROR, "Name attempted to set a null value");
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null) {
            LOG.log(Level.ERROR, "Surname attempted to set a null value");
            throw new IllegalArgumentException();
        }
        this.surname = surname;
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
