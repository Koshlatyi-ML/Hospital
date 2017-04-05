package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.StuffTableInfo;

import java.util.Arrays;
import java.util.List;

public class StuffConstantTableInfo extends AbstractTableInfo implements StuffTableInfo {
    private String nameColumn;
    private String surnameColumn;
    private String departmentIdColumn;

    StuffConstantTableInfo() {}

    @Override
    public String getNameColumn(ColumnNameStyle style) {
        return getColumn(nameColumn, style);
    }

    private void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    @Override
    public String getSurnameColumn(ColumnNameStyle style) {
        return getColumn(surnameColumn, style);
    }

    private void setSurnameColumn(String surnameColumn) {
        this.surnameColumn = surnameColumn;
    }

    @Override
    public String getDepartmentIdColumn(ColumnNameStyle style) {
        return getColumn(departmentIdColumn, style);
    }

    private void setDepartmentIdColumn(String departmentIdColumn) {
        this.departmentIdColumn = departmentIdColumn;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Arrays.asList(nameColumn, surnameColumn,
                departmentIdColumn);
    }

    public static class Builder
            extends AbstractTableInfo.Builder<StuffConstantTableInfo, Builder> {

        Builder setNameColumn(String nameColumn) {
            instance.setNameColumn(nameColumn);
            return getSelf();
        }

        Builder setSurnameColumn(String surnameColumn) {
            instance.setSurnameColumn(surnameColumn);
            return getSelf();
        }

        Builder setDepartmentIdColumn(String departmentIdColumn) {
            instance.setDepartmentIdColumn(departmentIdColumn);
            return getSelf();
        }

        @Override
        public Builder getSelf() {
            return this;
        }
    }
}
