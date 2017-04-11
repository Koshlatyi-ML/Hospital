package domain;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Objects;

public class TherapyDTO extends AbstractDTO {

    private String title;
    private String type;
    private String description;
    private Timestamp appointmentDateTime;
    private Timestamp completionDateTime;
    private long patientId;
    private long performerId;
    private static final Logger LOG = LogManager.getLogger(TherapyDTO.class);


    public enum Type {
        PHARMACOTHERAPY,
        PHYSIOTHERAPY,
        SURGERY_OPERATION
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            LOG.log(Level.ERROR, "Title attempted to set a null value");
            throw new IllegalArgumentException();
        }
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(Type type) {
        if (type == null) {
            LOG.log(Level.ERROR, "Type attempted to set a null value");
            throw new IllegalArgumentException();
        }

        this.type = type.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            LOG.log(Level.ERROR, "Description attempted to set a null value");
            throw new IllegalArgumentException();
        }
        this.description = description;
    }

    public Timestamp getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(Timestamp appointmentDateTime) {
        if (appointmentDateTime == null) {
            LOG.log(Level.ERROR, "Appointment datetime attempted to set a null value");
            throw new IllegalArgumentException();
        }
        this.appointmentDateTime = appointmentDateTime;
    }

    public Timestamp getCompletionDateTime() {
        return completionDateTime;
    }

    public void setCompletionDateTime(Timestamp completionDateTime) {
        if (this.completionDateTime != null) {
            LOG.log(Level.ERROR, "Completion datetime attempted to reset a value");
            throw new IllegalStateException();
        }

        this.completionDateTime = completionDateTime;
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

        public Builder() {
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
            instance.setCompletionDateTime(time);
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
                && Objects.equals(getCompletionDateTime(), that.getCompletionDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getType(), getDescription(),
                getAppointmentDateTime(), getCompletionDateTime(), getPatientId(),
                getPerformerId());
    }
}
