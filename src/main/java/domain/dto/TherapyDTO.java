package domain.dto;

import java.sql.Timestamp;
import java.time.Instant;

public class TherapyDTO extends AbstractDTO {
    private String name;
    private String type;
    private String description;
    private Timestamp appointmentDateTime;
    private Timestamp completeDateTime;
    private long patientId;
    private long performerId;

    public String getName() {
        return name;
    }

    public enum Type {
        PHARMACOTHERAPY,
        PHYSIOTHERAPY,
        SURGERY_OPERATION;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type.toString();
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

    public Timestamp getCompleteDateTime() {
        return completeDateTime;
    }

    public void setCompleteDateTime(Timestamp completeDateTime) {
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

        public Builder setAppointmentDateTime(Timestamp time) {
            instance.setAppointmentDateTime(time);
            return getSelf();
        }

        public Builder setCompleteDateTime(Timestamp time) {
            instance.setCompleteDateTime(time);
            return getSelf();
        }

        public Builder setPatientId(long patientId) {
            instance.setPatientId(patientId);
            return getSelf();
        }

        public Builder setPerformerId(long perfomerId) {
            instance.setPerformerId(perfomerId);
            return getSelf();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }
}
