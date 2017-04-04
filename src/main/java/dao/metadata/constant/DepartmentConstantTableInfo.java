package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DepartmentTableInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DepartmentConstantTableInfo implements DepartmentTableInfo {

    private static final String TABLE_NAME = "departments";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    DepartmentConstantTableInfo() {}

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? ID_COLUMN
                : TABLE_NAME + "." + ID_COLUMN;
    }

    @Override
    public String getNameColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? NAME_COLUMN
                : TABLE_NAME + "." + NAME_COLUMN;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Collections.singletonList(NAME_COLUMN);
    }
}
