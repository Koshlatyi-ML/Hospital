package domain;

import java.time.Instant;

public class Therapy extends IdHolder {
    private String name;
    private Type type;
    private String description;
    private Instant appointmentDateTime;
    private Instant completeDateTime;
    private Patient patient;

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

    public static class Builder
            extends IdHolder.Builder<Therapy, Builder> {

        public Builder(){
            instance = new Therapy();
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

        public Builder setPatient(Patient patient) {
            instance.setPatient(patient);
            return getSelf();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }
}
