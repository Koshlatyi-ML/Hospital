package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.StuffTableInfo;

import java.util.Arrays;
import java.util.List;

public class StuffConstantTableInfo implements StuffTableInfo {

    private static final String TABLE_NAME = "stuff";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String DEPARTMENT_ID_COLUMN = "department_id";

    StuffConstantTableInfo() {
    }

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
    public String getSurnameColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? SURNAME_COLUMN
                : TABLE_NAME + "." + SURNAME_COLUMN;
    }

    @Override
    public String getDepartmentIdColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? DEPARTMENT_ID_COLUMN
                : TABLE_NAME + "." + DEPARTMENT_ID_COLUMN;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Arrays.asList(NAME_COLUMN, SURNAME_COLUMN,
                DEPARTMENT_ID_COLUMN);
    }
}
