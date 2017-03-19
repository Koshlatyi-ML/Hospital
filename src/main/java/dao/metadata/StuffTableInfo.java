package dao.metadata;

import dao.metadata.annotation.InheritedBy;
import dao.metadata.annotation.OneToMany;
import dao.metadata.util.TableInfoUtils;
import domain.Department;
import domain.Medic;

@InheritedBy(Medic.class)
public class StuffTableInfo extends PersonTableInfo {
    private String departmentIdColumn;

    StuffTableInfo() {}

    public String getDepartmentIdColumn() {
        return departmentIdColumn;
    }

    void setDepartmentIdColumn(String departmentIdColumn) {
        this.departmentIdColumn = departmentIdColumn;
    }

    static void fillTableInfo(StuffTableInfo tableInfo) {
        PersonTableInfo.fillTableInfo(tableInfo);

        String tableName
                = TableInfoUtils.getInheritedByTableName(StuffTableInfo.class);

        OneToMany oneToManyAnnot
                = TableInfoUtils.getOneToManyRelation(Department.class, tableName);

        tableInfo.setDepartmentIdColumn(oneToManyAnnot.foreignKey());
    }

    public static StuffTableInfo createStuffTableInfo() {
        StuffTableInfo tableInfo = new StuffTableInfo();
        fillTableInfo(tableInfo);

        String tableName
                = TableInfoUtils.getInheritedByTableName(StuffTableInfo.class);
        tableInfo.setTableName(tableName);

        OneToMany departmentToStuffAnnot
                = TableInfoUtils.getOneToManyRelation(Department.class, tableName);

        String column = departmentToStuffAnnot.foreignKey();
        tableInfo.setDepartmentIdColumn(column);
        tableInfo.getColumnNames().add(column);

        return tableInfo;
    }
}
