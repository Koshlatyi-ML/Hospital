package domain;

import java.util.List;

public class Doctor extends Medic {
    private List<Patient> patients;
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

    public static class Builder extends Person.Builder<Doctor, Builder> {
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
