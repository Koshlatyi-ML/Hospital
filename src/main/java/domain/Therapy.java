package domain;

import java.time.ZonedDateTime;

public class Therapy extends IdHolder {
    private String name;
    private String description;
    private Type type;
    private ZonedDateTime appointmentDateTime;
    private ZonedDateTime completeDateTime;
    private Patient patient;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ZonedDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(ZonedDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public ZonedDateTime getCompleteDateTime() {
        return completeDateTime;
    }

    public void setCompleteDateTime(ZonedDateTime completeDateTime) {
        this.completeDateTime = completeDateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public enum Type {
        PHARMACOTHERAPY,
        PHYSIOTHERAPY,
        SURGERY_OPERATION;
    }

    public static class Builder<T extends Therapy, B extends Builder>
            extends IdHolder.Builder<T, B> {

        public Builder<T, B> setName(String name) {
            instance.setName(name);
            return this;
        }

        public Builder<T, B> setDescription(String description) {
            instance.setDescription(description);
            return this;
        }

        public Builder<T, B> setType(Type type) {
            instance.setType(type);
            return this;
        }

        public Builder<T, B> setAppointmentDateTime(ZonedDateTime time) {
            instance.setAppointmentDateTime(time);
            return this;
        }

        public Builder<T, B> setCompleteDateTime(ZonedDateTime time) {
            instance.setCompleteDateTime(time);
            return this;
        }

        public Builder<T, B> setPatient(Patient patient) {
            instance.setPatient(patient);
            return this;
        }
    }
}
