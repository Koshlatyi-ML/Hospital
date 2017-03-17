package domain;

import dao.metadata.Column;

public class Patient extends Person {
    @Column("compliants")
    private String complaints;
    @Column("diagnosis")
    private String diagnosis;
    //    private Doctor doctor;
    @Column("state")
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
            super(new Patient());
        }

        public Builder setCompliants(String compliants) {
            instance.setComplaints(compliants);
            return this;
        }

        public Builder setDiagnosis(String diagnosis) {
            instance.setDiagnosis(diagnosis);
            return this;
        }
    }
}
