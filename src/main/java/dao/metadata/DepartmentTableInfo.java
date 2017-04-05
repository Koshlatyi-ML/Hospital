package dao.metadata;

public interface DepartmentTableInfo extends PlainTableInfo {
    String TABLE_NAME = "departments";
    String ID_COLUMN = "id";
    String NAME_COLUMN = "name";

    String getNameColumn(ColumnNameStyle columnNameStyle);
}
