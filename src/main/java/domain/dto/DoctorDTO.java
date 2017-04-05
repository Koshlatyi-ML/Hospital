package domain.dto;

import java.util.Objects;

public class DoctorDTO extends AbstractStuffDTO {
    private DoctorDTO() {}

    public static class Builder extends AbstractStuffDTO.Builder<DoctorDTO, Builder> {
        public Builder() {
            instance = new DoctorDTO();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorDTO)) return false;
        DoctorDTO dto = (DoctorDTO) o;
        return getId() == dto.getId()
                && Objects.equals(getName(), dto.getName())
                && Objects.equals(getSurname(), dto.getSurname())
                && getDepartmentId() == dto.getDepartmentId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getDepartmentId());
    }
}
