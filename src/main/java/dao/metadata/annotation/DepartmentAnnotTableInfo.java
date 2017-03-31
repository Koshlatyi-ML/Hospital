package dao.metadata.annotation;

import dao.metadata.DepartmentTableInfo;
import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Department;

import java.util.List;
import java.util.Map;

@Entity(Department.class)
public class DepartmentAnnotTableInfo extends IdHolderAnnotTableInfo
                                      implements DepartmentTableInfo {
    private String nameColumn;

    private DepartmentAnnotTableInfo() {}

    public String getNameColumn() {
        return nameColumn;
    }

    void fillTableInfo() {
        super.fillTableInfo();
        Class entityClass = DepartmentAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        tableName = AnnotTableInfos.getTableName(entityClass);

        Map<String, String> fieldColumnMap =
                AnnotTableInfos.loadFieldColumnMap(entityClass);
        nameColumn = String.format("%s.%s", tableName, fieldColumnMap.get("name"));
        columns.add(nameColumn);
    }

    static DepartmentAnnotTableInfo createAnnotTableInfo() {
        DepartmentAnnotTableInfo tableInfo = new DepartmentAnnotTableInfo();
        tableInfo.fillTableInfo();
        return tableInfo;
    }
}
