package domain;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class PatientDTO extends AbstractPersonDTO {

    private long doctorId;
    private String complaints;
    private String diagnosis;
    private String state;
    private long credentialsId;
    private static final Logger LOG = LogManager.getLogger(PatientDTO.class);


    private PatientDTO() {
        state = State.REGISTERED.toString();
    }

    public enum State {
        REGISTERED,
        APPLIED,
        TREATED,
        DISCHARGED
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
        if (!state.equals(State.REGISTERED.toString()) && complaints == null) {
            throw new IllegalArgumentException("Complaints attempted to set " +
                    "a null value in inappropriate patient state");
        }

        this.complaints = complaints;
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
        if (state == null) {
            LOG.log(Level.ERROR, "State attempted to set a null value");
            throw new IllegalArgumentException();
        }

        if (state == State.DISCHARGED && diagnosis == null) {
            LOG.log(Level.ERROR, "State attempted to set discharged in inappropriate patient state");
            throw new IllegalStateException();
        }

        this.state = state.toString();
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
