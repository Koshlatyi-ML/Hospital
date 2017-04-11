package domain;

import java.util.Objects;

public class MedicDTO extends AbstractStuffDTO {

    private long credentialsId;

    private MedicDTO() {}

    public long getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(long credentialsId) {
        this.credentialsId = credentialsId;
    }

    public static class Builder extends AbstractStuffDTO.Builder<MedicDTO, Builder> {
        public Builder() {
            instance = new MedicDTO();
        }

        public Builder setCredentialsId(long credentialsId) {
            instance.setCredentialsId(credentialsId);
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
        if (!(o instanceof MedicDTO)) return false;
        MedicDTO dto = (MedicDTO) o;
        return getId() == dto.getId()
                && Objects.equals(getName(), dto.getName())
                && Objects.equals(getSurname(), dto.getSurname())
                && getDepartmentId() == dto.getDepartmentId()
                && getCredentialsId() == dto.getCredentialsId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(),
                getDepartmentId(), getCredentialsId());
    }
}
