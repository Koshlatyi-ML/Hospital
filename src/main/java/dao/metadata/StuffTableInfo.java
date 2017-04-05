package dao.metadata;

public interface StuffTableInfo extends PersonTableInfo {
    String TABLE_NAME = "stuff";
    String ID_COLUMN = "id";
    String NAME_COLUMN = "name";
    String SURNAME_COLUMN = "surname";
    String DEPARTMENT_ID_COLUMN = "department_id";

    String getDepartmentIdColumn(ColumnNameStyle columnNameStyle);
}
