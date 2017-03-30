package dao.metadata.constant;

import dao.metadata.StuffTableInfo;

import java.util.Arrays;
import java.util.List;

public class StuffConstantTableInfo extends PersonConstantTableInfo
        implements StuffTableInfo {

    private String departmentIdColumn;

    private static final String TABLE_NAME = "stuff";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String DEPARTMENT_ID_COLUMN = "department_id";

    StuffConstantTableInfo() {}

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumn() {
        return ID_COLUMN;
    }

    @Override
    public String getNameColumn() {
        return NAME_COLUMN;
    }

    @Override
    public String getSurnameColumn() {
        return SURNAME_COLUMN;
    }

    @Override
    public String getDepartmentIdColumn() {
        return DEPARTMENT_ID_COLUMN;
    }

    @Override
    public List<String> getColumns() {
        return Arrays.asList(ID_COLUMN, NAME_COLUMN, SURNAME_COLUMN,
                DEPARTMENT_ID_COLUMN);
    }
}
