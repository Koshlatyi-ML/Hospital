package domain.dto;

public class DepartmentDTO extends AbstractDTO {
    private String name;

    private DepartmentDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder
            extends AbstractDTO.Builder<DepartmentDTO, DepartmentDTO.Builder> {

        public Builder() {
            instance = new DepartmentDTO();
        }

        public Builder setId(long id) {
            instance.setId(id);
            return getSelf();
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
}
