package dao.metadata.annotation;

import dao.metadata.StuffTableInfo;
import dao.metadata.annotation.mapping.InheritedBy;
import dao.metadata.annotation.mapping.OneToMany;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Department;
import domain.Medic;

@InheritedBy(Medic.class)
public class StuffAnnotTableInfo extends PersonAnnotTableInfo
                                 implements StuffTableInfo {
    private String stuffTableName;
    private String departmentIdColumn;

    protected StuffAnnotTableInfo() {}

    @Override
    public String getDepartmentIdColumn() {
        return departmentIdColumn;
    }

    private void setDepartmentIdColumn(String departmentIdColumn) {
        this.departmentIdColumn = departmentIdColumn;
    }

    @Override
    public String getStuffTableName() {
        return stuffTableName;
    }

    private void setStuffTableName(String stuffTableName) {
        this.stuffTableName = stuffTableName;
    }

    void fillTableInfo(StuffAnnotTableInfo tableInfo) {
        super.fillTableInfo(tableInfo);

        String tableName
                = AnnotTableInfos.getInheritedByTableName(StuffAnnotTableInfo.class);
        tableInfo.setStuffTableName(tableName);

        OneToMany departmentToStuffAnnot
                = AnnotTableInfos.getOneToManyRelation(Department.class, tableName);

        String column = departmentToStuffAnnot.foreignKey();
        tableInfo.setDepartmentIdColumn(column);
        tableInfo.getColumnNames().add(column);
    }

    static StuffAnnotTableInfo createAnnotTableInfo() {
        StuffAnnotTableInfo tableInfo = new StuffAnnotTableInfo();
        tableInfo.fillTableInfo(tableInfo);
        return tableInfo;
    }
}
