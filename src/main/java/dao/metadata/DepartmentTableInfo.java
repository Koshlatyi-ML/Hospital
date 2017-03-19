package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.util.TableInfoUtils;
import domain.Department;

import java.util.Map;

@Entity(Department.class)
public class DepartmentTableInfo extends IdHolderTableInfo {
    private String nameColumn;

    private DepartmentTableInfo() {
    }

    public String getNameColumn() {
        return nameColumn;
    }

    protected void setNameColumn(String nameColumn) {
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
        tableInfo.setNameColumn(fieldColumnMap.get("name"));

        // arranging get all cols logic

        return tableInfo;
    }
}
