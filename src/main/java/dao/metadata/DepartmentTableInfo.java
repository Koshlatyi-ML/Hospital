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
        Class entityClass = DepartmentTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        Map<String, String> fieldColumnMap
                = TableInfoUtils.loadFieldColumnMap(entityClass);

        DepartmentTableInfo tableInfo = new DepartmentTableInfo();
        tableInfo.setTableName(TableInfoUtils.getTableName(entityClass));
        fillTableInfo(tableInfo);
        tableInfo.setNameColumn(fieldColumnMap.get("name"));
        // arranging get all cols logic

        return tableInfo;
    }
}
