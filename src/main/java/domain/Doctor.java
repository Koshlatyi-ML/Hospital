package domain;

import dao.jdbc.metadata.OneToMany;

import java.util.List;

public class Doctor extends Medic {
    @OneToMany("doctor_id")
    private List<Patient> patients;
    @OneToMany("performer_id")
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
