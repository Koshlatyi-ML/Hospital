package domain;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class DepartmentDTO extends AbstractDTO {

    private String name;
    private static final Logger LOG = LogManager.getLogger(DepartmentDTO.class);

    private DepartmentDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            LOG.log(Level.ERROR, "Department role attempted to set a null value");
            throw new IllegalArgumentException();
        }
        this.name = name;
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
