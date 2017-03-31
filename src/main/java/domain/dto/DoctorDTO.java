package domain.dto;

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
}
