package domain;

import java.time.Instant;
import java.util.List;

public class Medic extends Person {
    private List<Therapy> pharmacotherapies;
    private List<Therapy> physiotherapies;

    Medic() {}

    public List<Therapy> getPharmacotherapies() {
        return pharmacotherapies;
    }

    public void setPharmacotherapies(List<Therapy> pharmacotherapies) {
        this.pharmacotherapies = pharmacotherapies;
    }

    public List<Therapy> getPhysiotherapies() {
        return physiotherapies;
    }

    public void setPhysiotherapies(List<Therapy> physiotherapies) {
        this.physiotherapies = physiotherapies;
    }

    public static class Builder extends AbstractBuilder<Medic, Builder> {

        public Builder() {
            this.instance = new Medic();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }

    static abstract class AbstractBuilder<T extends Medic, B extends AbstractBuilder>
            extends Person.Builder<T, B> {

        public B setPharmacotherapies(List<Therapy> therapies) {
            instance.setPharmacotherapies(therapies);
            return getSelf();
        }

        public B setPhysiotherapies(List<Therapy> therapies) {
            instance.setPhysiotherapies(therapies);
            return getSelf();
        }

        @Override
        protected abstract B getSelf();
    }
}
