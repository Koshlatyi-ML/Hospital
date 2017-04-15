package service.dto;

import java.util.Objects;

public class PatientRegistrationDTO extends AbstractRegistrationDTO {

    private PatientRegistrationDTO() {}

    public static class Builder
            extends AbstractRegistrationDTO.Builder<PatientRegistrationDTO, Builder> {

        public Builder() {
            instance = new PatientRegistrationDTO();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientRegistrationDTO)) return false;
        PatientRegistrationDTO that = (PatientRegistrationDTO) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSurname(), that.getSurname()) &&
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getLogin(), getPassword());
    }
}
