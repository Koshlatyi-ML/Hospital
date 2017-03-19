package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.util.TableInfoUtils;
import domain.Department;

import java.util.Map;

@Entity(Department.class)
public class DepartmentTableInfo extends IdHolderTableInfo {
    private String nameColumn;

    DepartmentTableInfo() {}

    public String getNameColumn() {
        return nameColumn;
    }

    void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public static DepartmentTableInfo createDepartmentTableInfo() {
        DepartmentTableInfo tableInfo = new DepartmentTableInfo();
        fillTableInfo(tableInfo);

        Class entityClass = DepartmentTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        tableInfo.setTableName(TableInfoUtils.getTableName(entityClass));

        Map<String, String> fieldColumnMap
                = TableInfoUtils.loadFieldColumnMap(entityClass);

        String column = fieldColumnMap.get("name");
        tableInfo.setNameColumn(column);
        tableInfo.getColumnNames().add(column);

        return tableInfo;
    }
}
