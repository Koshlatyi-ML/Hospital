package domain;

import dao.metadata.annotation.OneToMany;
import dao.metadata.annotation.Table;

import java.util.List;

@Table("doctors")
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

    public static class Builder extends Medic.Builder<Doctor, Builder> {
        public Builder() {
            super(new Doctor());
        }

        public Builder setPatients(List<Patient> patients) {
            instance.setPatients(patients);
            return this;
        }

        public Builder setSurgicalOperations(List<Therapy> operations) {
            instance.setSurgicalOperations(operations);
            return this;
        }
    }
}
