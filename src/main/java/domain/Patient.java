package domain;

import dao.metadata.annotation.mapping.Column;
import dao.metadata.annotation.mapping.Table;

public class Patient extends Person {
    private String complaints;
    private String diagnosis;
    private State state;

    private Patient() {
        state = State.APPLYIED;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        APPLYIED,
        ADDMITTED,
        DISCHARGED //maybe redundant
    }

    public static class Builder extends Person.Builder<Patient, Builder> {
        public Builder() {
            instance = new Patient();
        }

        public Builder setCompliants(String compliants) {
            instance.setComplaints(compliants);
            return getSelf();
        }

        public Builder setDiagnosis(String diagnosis) {
            instance.setDiagnosis(diagnosis);
            return getSelf();
        }

        public Builder setState(State state) {
            instance.setState(state);
            return getSelf();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }
}
