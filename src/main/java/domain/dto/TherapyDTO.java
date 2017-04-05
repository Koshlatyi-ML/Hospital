package domain.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class TherapyDTO extends AbstractDTO {
    private String title;
    private String type;
    private String description;
    private Timestamp appointmentDateTime;
    private Timestamp completeDateTime;
    private long patientId;
    private long performerId;

    public String getTitle() {
        return title;
    }

    public enum Type {
        PHARMACOTHERAPY,
        PHYSIOTHERAPY,
        SURGERY_OPERATION;
    }

    public void setTitle(String title) {
        this.title = title;
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

        public Builder setTitle(String title) {
            instance.setTitle(title);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TherapyDTO)) return false;
        TherapyDTO that = (TherapyDTO) o;
        return getId() == that.getId()
                && getPatientId() == that.getPatientId()
                && getPerformerId() == that.getPerformerId()
                && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getType(), that.getType())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getAppointmentDateTime(), that.getAppointmentDateTime())
                && Objects.equals(getCompleteDateTime(), that.getCompleteDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getType(), getDescription(),
                getAppointmentDateTime(),getCompleteDateTime(), getPatientId(),
                getPerformerId());
    }
}
