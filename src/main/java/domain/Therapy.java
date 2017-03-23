package domain;

import dao.metadata.annotation.mapping.Column;
import dao.metadata.annotation.mapping.Table;

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
    private ZonedDateTime appointmentDateTime;
    @Column("complete_date")
    private ZonedDateTime completeDateTime;
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

        public Builder setAppointmentDateTime(ZonedDateTime time) {
            instance.setAppointmentDateTime(time);
            return getSelf();
        }

        public Builder setCompleteDateTime(ZonedDateTime time) {
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
