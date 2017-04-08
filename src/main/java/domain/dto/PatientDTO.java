package domain.dto;

import java.util.Objects;
import java.util.Optional;

public class PatientDTO extends AbstractPersonDTO {

    private long doctorId;
    private String complaints;
    private String diagnosis;
    private String state;
    private long credentialsId;


    private PatientDTO() {
    }

    public enum State {
        APPLYIED,
        ADDMITTED,
        DISCHARGED //maybe redundant
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
        this.complaints = Optional.ofNullable(complaints)
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getState() {
        return state;
    }

    public void setState(State state) {
        this.state = Optional.ofNullable(state)
                .orElseThrow(IllegalArgumentException::new)
                .toString();
    }

    public long getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(long credentialsId) {
        this.credentialsId = credentialsId;
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

        public Builder setCredentialsId(long credentialsId) {
            instance.setCredentialsId(credentialsId);
            return getSelf();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientDTO)) return false;
        PatientDTO that = (PatientDTO) o;
        return getId() == that.getId()
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getSurname(), that.getSurname())
                && getDoctorId() == that.getDoctorId()
                && Objects.equals(getComplaints(), that.getComplaints())
                && Objects.equals(getDiagnosis(), that.getDiagnosis())
                && Objects.equals(getState(), that.getState())
                && getCredentialsId() == that.getCredentialsId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getDoctorId(),
                getComplaints(), getDiagnosis(), getState(), getCredentialsId());
    }
}
