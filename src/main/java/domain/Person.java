package domain;

public abstract class Person extends IdHolder {
    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static class Builder<T extends Person, B extends Builder>
            extends IdHolder.Builder<T, B> {

        protected Builder(T person) {
            instance = person;
        }

        public Builder<T, B> setId(long id) {
            instance.setId(id);
            return this;
        }

        public Builder<T, B> setName(String name) {
            instance.setName(name);
            return this;
        }

        public Builder<T, B>  setSurname(String surname) {
            instance.setSurname(surname);
            return this;
        }
    }
}
