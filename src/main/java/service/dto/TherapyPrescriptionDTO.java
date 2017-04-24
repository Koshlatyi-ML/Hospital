package service.dto;

import util.AbstractBuilder;

import java.sql.Timestamp;

public class TherapyPrescriptionDTO {

    private String title;
    private String type;
    private String description;
    private Timestamp appointmentDateTime;
    private long performerId;

    private TherapyPrescriptionDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(Timestamp appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public long getPerformerId() {
        return performerId;
    }

    public void setPerformerId(long performerId) {
        this.performerId = performerId;
    }

    public static class Builder extends AbstractBuilder<TherapyPrescriptionDTO> {
        public Builder() {
            instance = new TherapyPrescriptionDTO();
        }

        public Builder setTitle(String title) {
            instance.setTitle(title);
            return this;
        }

        public Builder setType(String type) {
            instance.setType(type);
            return this;
        }

        public Builder setDescription(String description) {
            instance.setDescription(description);
            return this;
        }

        public Builder setAppointmentDateTime(Timestamp appointmentDateTime) {
            instance.setAppointmentDateTime(appointmentDateTime);
            return this;
        }

        public Builder setPerformerId(long performerId) {
            instance.setPerformerId(performerId);
            return this;
        }
    }
}
