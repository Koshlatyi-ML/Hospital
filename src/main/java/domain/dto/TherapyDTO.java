package domain.dto;

import domain.IdHolder;
import domain.Patient;
import domain.Therapy;

import java.time.Instant;

public class TherapyDTO extends AbstractDTO {
    private String name;
    private Type type;
    private String description;
    private Instant appointmentDateTime;
    private Instant completeDateTime;
    private long patientId;
    private long performerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(Instant appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public Instant getCompleteDateTime() {
        return completeDateTime;
    }

    public void setCompleteDateTime(Instant completeDateTime) {
        this.completeDateTime = completeDateTime;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getPerformerId() {
        return performerId;
    }

    public void setPerformerId(long performerId) {
        this.performerId = performerId;
    }

    public enum Type {
        PHARMACOTHERAPY,
        PHYSIOTHERAPY,
        SURGERY_OPERATION;
    }

    public static class Builder extends AbstractDTO.Builder<TherapyDTO, Builder> {

        public Builder(){
            instance = new TherapyDTO();
        }

        public Builder setName(String name) {
            instance.setName(name);
            return getSelf();
        }

        public Builder setDescription(String description) {
            instance.setDescription(description);
            return getSelf();
        }

        public Builder setType(Type type) {
            instance.setType(type);
            return getSelf();
        }

        public Builder setAppointmentDateTime(Instant time) {
            instance.setAppointmentDateTime(time);
            return getSelf();
        }

        public Builder setCompleteDateTime(Instant time) {
            instance.setCompleteDateTime(time);
            return getSelf();
        }

        public Builder setPatientId(long patientId) {
            instance.setPatientId(patientId);
            return getSelf();
        }

        public Builder setPerfomerId(long perfomerId) {
            instance.setPatientId(perfomerId);
            return getSelf();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }
}
