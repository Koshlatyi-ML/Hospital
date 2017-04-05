package domain.dto;

import java.util.Objects;
import java.util.Optional;

public class DepartmentDTO extends AbstractDTO {
    private String name;

    private DepartmentDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Optional.ofNullable(name)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static class Builder
            extends AbstractDTO.Builder<DepartmentDTO, DepartmentDTO.Builder> {

        public Builder() {
            instance = new DepartmentDTO();
        }

        public Builder setName(String name) {
            instance.setName(name);
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
        if (!(o instanceof DepartmentDTO)) return false;
        DepartmentDTO dto = (DepartmentDTO) o;
        return getId() == dto.getId()
                && Objects.equals(getName(), dto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
