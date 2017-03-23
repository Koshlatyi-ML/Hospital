package domain;

import dao.metadata.annotation.mapping.Inherit;
import dao.metadata.annotation.mapping.OneToMany;
import dao.metadata.annotation.mapping.Table;

import java.util.List;

@Table("doctors")
@Inherit(table = "stuff", foreignKey = "stuff_id")
public class Doctor extends Medic {
    @OneToMany(table = "patients", foreignKey = "doctor_id")
    private List<Patient> patients;
    @OneToMany(table = "therapies", foreignKey = "performer_id")
    private List<Therapy> surgicalOperations;

    private Doctor() {}

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Therapy> getSurgicalOperations() {
        return surgicalOperations;
    }

    public void setSurgicalOperations(List<Therapy> surgicalOperations) {
        this.surgicalOperations = surgicalOperations;
    }

    public static class Builder extends Medic.AbstractBuilder<Doctor, Builder> {
        public Builder() {
            instance = new Doctor();
        }

        public Builder setPatients(List<Patient> patients) {
            instance.setPatients(patients);
            return getSelf();
        }

        public Builder setSurgicalOperations(List<Therapy> operations) {
            instance.setSurgicalOperations(operations);
            return getSelf();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }
}
