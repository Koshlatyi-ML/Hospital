package domain.model;

import dao.metadata.annotation.OneToMany;
import dao.metadata.annotation.Table;

import java.util.List;

@Table("medics")
public class Medic extends Person {
    @OneToMany(table = "therapies", foreignKey = "performer_id")
    private List<Therapy> pharmacotherapies;
    @OneToMany(table = "therapies", foreignKey = "performer_id")
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

    public static class Builder<T extends Medic, B extends Builder>
            extends Person.Builder<T, B> {

        public Builder() {
            super((T) new Medic());
        }

        protected Builder(T instance) {
            super(instance);
        }

        public Builder<T, B> setPharmacotherapies(List<Therapy> therapies) {
            instance.setPharmacotherapies(therapies);
            return this;
        }

        public Builder<T, B> setPhysiotherapies(List<Therapy> therapies) {
            instance.setPhysiotherapies(therapies);
            return this;
        }
    }
}