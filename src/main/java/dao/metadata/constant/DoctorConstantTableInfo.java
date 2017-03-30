package dao.metadata.constant;

import dao.metadata.DoctorTableInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoctorConstantTableInfo extends IdHolderConstantTableInfo
        implements DoctorTableInfo {

    private static final String TABLE_NAME = "doctors";
    private static final String STUFFID_COLUMN = "stuff_id";

    DoctorConstantTableInfo() {}

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumn() {
        return STUFFID_COLUMN;
    }

    @Override
    public List<String> getColumns() {
        return Collections.singletonList(STUFFID_COLUMN);
    }

    @Override
    public List<String> getEntityfulColumns() {
        return Collections.singletonList(STUFFID_COLUMN);
    }
}
