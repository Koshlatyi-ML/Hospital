package domain;

import dao.metadata.annotation.mapping.Column;
import dao.metadata.annotation.mapping.OneToOne;
import dao.metadata.annotation.mapping.Table;

import java.time.Instant;
import java.time.ZonedDateTime;

@Table("therapies")
public class Therapy extends IdHolder {
    @Column("name")
    private String name;
    @Column("description")
    private String description;
    @Column("type")
    private Type type;
    @Column("appointment_date")
    private Instant appointmentDateTime;
    @Column("complete_date")
    private Instant completeDateTime;
    @Column("patient_id")
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
