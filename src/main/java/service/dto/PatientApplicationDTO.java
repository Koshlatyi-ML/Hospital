package service.dto;

import util.AbstractBuilder;

import java.util.Objects;

public class PatientApplicationDTO {

    private long doctorId;
    private String complaints;

    private PatientApplicationDTO(){}

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

    public static class Builder extends AbstractBuilder<PatientApplicationDTO> {

        public Builder() {
            instance = new PatientApplicationDTO();
        }

        public Builder setDoctorId(long id) {
            instance.setDoctorId(id);
            return this;
        }

        public Builder setComplaints(String complaints) {
            instance.setComplaints(complaints);
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientApplicationDTO)) return false;
        PatientApplicationDTO that = (PatientApplicationDTO) o;
        return getDoctorId() == that.getDoctorId() &&
                Objects.equals(getComplaints(), that.getComplaints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDoctorId(), getComplaints());
    }
}
