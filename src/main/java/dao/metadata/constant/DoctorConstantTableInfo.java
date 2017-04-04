package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DoctorTableInfo;

import java.util.Collections;
import java.util.List;

public class DoctorConstantTableInfo implements DoctorTableInfo {

    private static final String TABLE_NAME = "doctors";
    private static final String STUFF_ID_COLUMN = "stuff_id";

    DoctorConstantTableInfo() {
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? STUFF_ID_COLUMN
                : TABLE_NAME + "." + STUFF_ID_COLUMN;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Collections.singletonList(STUFF_ID_COLUMN);
    }
}
