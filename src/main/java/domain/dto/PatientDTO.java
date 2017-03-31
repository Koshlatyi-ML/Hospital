package domain.dto;

public class PatientDTO extends AbstractPersonDTO {
    private long doctorId;
    private String complaints;
    private String diagnosis;
    private State state;

    private PatientDTO() {
        state = State.APPLYIED;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
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

    public static class Builder extends AbstractPersonDTO.Builder<PatientDTO, Builder> {
        public Builder() {
            instance = new PatientDTO();
        }

        public Builder setDoctorId(long doctorId) {
            instance.setDoctorId(doctorId);
            return getSelf();
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
