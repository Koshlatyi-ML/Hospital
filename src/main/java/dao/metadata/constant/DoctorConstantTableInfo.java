package dao.metadata.constant;

import dao.metadata.DoctorTableInfo;

import java.util.Collections;
import java.util.List;

public class DoctorConstantTableInfo extends AbstractTableInfo implements DoctorTableInfo {

    DoctorConstantTableInfo() {}

    @Override
    public List<String> getNonGeneratingColumns() {
        return Collections.singletonList(idColumn);
    }

    public static class Builder extends AbstractTableInfo.Builder<DoctorConstantTableInfo, Builder> {

        @Override
        public Builder getSelf() {
            return this;
        }
    }
}
