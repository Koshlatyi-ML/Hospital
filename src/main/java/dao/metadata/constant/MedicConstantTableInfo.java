package dao.metadata.constant;

import dao.metadata.MedicTableInfo;

import java.util.Collections;
import java.util.List;

public class MedicConstantTableInfo extends AbstractTableInfo implements MedicTableInfo {

    MedicConstantTableInfo() {}

    @Override
    public List<String> getNonGeneratingColumns() {
        return Collections.singletonList(idColumn);
    }

    public static class Builder extends AbstractTableInfo.Builder<MedicConstantTableInfo, Builder> {

        public Builder() {
            instance = new MedicConstantTableInfo();
        }

        @Override
        public Builder getSelf() {
            return this;
        }
    }
}
