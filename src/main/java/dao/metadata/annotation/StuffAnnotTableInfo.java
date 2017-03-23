package dao.metadata.annotation;

import dao.metadata.StuffTableInfo;
import dao.metadata.annotation.mapping.InheritedBy;
import dao.metadata.annotation.mapping.OneToMany;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Department;
import domain.Medic;

import java.util.ArrayList;
import java.util.List;

@InheritedBy(Medic.class)
public class StuffAnnotTableInfo extends PersonAnnotTableInfo
                                 implements StuffTableInfo {
    private String stuffTableName;
    private String departmentIdColumn;
    private List<String> stuffColumns;

    protected StuffAnnotTableInfo() {}

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

    @Override
    public List<String> getStuffColumns() {
        return stuffColumns;
    }

    public void setStuffColumns(List<String> stuffColumns) {
        this.stuffColumns = stuffColumns;
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
        tableInfo.getColumns().add(column); //todo?

        stuffColumns = new ArrayList<>();
        tableInfo.getColumns().forEach(col -> stuffColumns.add(col));
        tableInfo.getStuffColumns().add(column);
    }

    static StuffAnnotTableInfo createAnnotTableInfo() {
        StuffAnnotTableInfo tableInfo = new StuffAnnotTableInfo();
        tableInfo.fillTableInfo(tableInfo);
        return tableInfo;
    }
}
