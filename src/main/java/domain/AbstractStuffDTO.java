package domain;

public abstract class AbstractStuffDTO extends AbstractPersonDTO {

    private long departmentId;

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public abstract static class Builder<T extends AbstractStuffDTO, B extends Builder>
            extends AbstractPersonDTO.Builder<T, B> {

        public B setDepartmentId(long id) {
            instance.setDepartmentId(id);
            return getSelf();
        }
    }
}
