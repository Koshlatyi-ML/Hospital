package dao.metadata.constant;

import dao.metadata.MedicTableInfo;

import java.util.Collections;
import java.util.List;

public class MedicConstantTableInfo extends IdHolderConstantTableInfo
        implements MedicTableInfo {

    private static final String TABLE_NAME= "medics";
    private static final String STUFF_ID_COLUMN = "stuff_id";

    MedicConstantTableInfo() {}

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumn() {
        return STUFF_ID_COLUMN;
    }

    @Override
    public List<String> getColumns() {
        return null;
    }
}
