package service.dto;

import java.util.Objects;

public class StuffRegistrationDTO extends AbstractRegistrationDTO {

    private long departmentId;

    private StuffRegistrationDTO() {}

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public static class Builder
            extends AbstractRegistrationDTO.Builder<StuffRegistrationDTO, Builder> {

        public Builder() {
            instance = new StuffRegistrationDTO();
        }

        public Builder setDepartmentId(long departmentId) {
            instance.setDepartmentId(departmentId);
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
        if (!(o instanceof StuffRegistrationDTO)) return false;
        StuffRegistrationDTO that = (StuffRegistrationDTO) o;
        return departmentId == that.departmentId
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getSurname(), that.getSurname())
                && Objects.equals(getLogin(), that.getLogin())
                && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, getName(), getSurname(),
                getLogin(), getPassword());
    }

}
