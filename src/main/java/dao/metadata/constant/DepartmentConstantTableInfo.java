package dao.metadata.constant;

import dao.metadata.DepartmentTableInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DepartmentConstantTableInfo extends IdHolderConstantTableInfo
        implements DepartmentTableInfo {

    private static final String TABLE_NAME = "departments";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    DepartmentConstantTableInfo() {}

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
    public List<String> getColumns() {
        return Collections.singletonList(NAME_COLUMN);
    }

    @Override
    public List<String> getEntityfulColumns() {
        return Collections.singletonList(NAME_COLUMN);
    }
}
