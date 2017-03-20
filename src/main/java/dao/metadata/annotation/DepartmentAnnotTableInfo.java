package dao.metadata.annotation;

import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Department;

import java.util.Formatter;
import java.util.Map;

@Entity(Department.class)
public class DepartmentAnnotTableInfo extends IdHolderAnnotTableInfo
                                      implements dao.metadata.DepartmentTableInfo {
    private String nameColumn;

    private DepartmentAnnotTableInfo() {}

    public String getNameColumn() {
        return nameColumn;
    }

    private void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    void fillTableInfo(DepartmentAnnotTableInfo tableInfo) {
        super.fillTableInfo(tableInfo);

        Class entityClass = DepartmentAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        tableInfo.setTableName(AnnotTableInfos.getTableName(entityClass));

        Map<String, String> fieldColumnMap
                = AnnotTableInfos.loadFieldColumnMap(entityClass);

        String column = fieldColumnMap.get("name");
        tableInfo.setNameColumn(column);
        tableInfo.getColumnNames().add(column);
    }

    static DepartmentAnnotTableInfo createAnnotTableInfo() {
        DepartmentAnnotTableInfo tableInfo
                = new DepartmentAnnotTableInfo();

        tableInfo.fillTableInfo(tableInfo);
        return tableInfo;
    }
}
