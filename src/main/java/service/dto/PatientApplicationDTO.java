package service.dto;

import util.AbstractBuilder;

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
}
