package domain;

import dao.metadata.annotation.mapping.Inherit;
import dao.metadata.annotation.mapping.OneToMany;
import dao.metadata.annotation.mapping.Table;

import java.util.List;

@Table("medics")
@Inherit(table = "stuff", foreignKey = "stuff_id")
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

    public static class Builder extends Person.Builder<Medic, Builder> {
        public Builder() {
            this.instance = new Medic();
        }

        public Builder setPharmacotherapies(List<Therapy> therapies) {
            instance.setPharmacotherapies(therapies);
            return getSelf();
        }

        public Builder setPhysiotherapies(List<Therapy> therapies) {
            instance.setPhysiotherapies(therapies);
            return getSelf();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }
}
