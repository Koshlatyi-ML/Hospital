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

    abstract static class Builder<T extends Person, B extends Builder>
            extends IdHolder.Builder<T, B> {

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
