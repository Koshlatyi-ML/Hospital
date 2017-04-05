package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DepartmentTableInfo;
import util.AbstractBuilder;

import java.util.Collections;
import java.util.List;

public class DepartmentConstantTableInfo extends AbstractTableInfo
        implements DepartmentTableInfo {
    private String nameColumn;

    DepartmentConstantTableInfo() {}

    @Override
    public String getNameColumn(ColumnNameStyle style) {
        return getColumn(nameColumn, style);
    }

    private void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Collections.singletonList(nameColumn);
    }

    public static class Builder
            extends AbstractTableInfo.Builder<DepartmentConstantTableInfo, Builder> {

        Builder setNameColumn(String nameColumn) {
            instance.setNameColumn(nameColumn);
            return getSelf();
        }

        @Override
        public Builder getSelf() {
            return getSelf();
        }
    }
}
