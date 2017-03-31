package domain.dto;

public class MedicDTO extends AbstractStuffDTO {
    private MedicDTO() {}

    public static class Builder extends AbstractStuffDTO.Builder<MedicDTO, Builder> {
        public Builder() {
            instance = new MedicDTO();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }
}
