//package dao.metadata.annotation;
//
//import dao.metadata.StuffTableInfo;
//import dao.metadata.annotation.mapping.InheritedBy;
//import dao.metadata.annotation.mapping.OneToMany;
//import dao.metadata.annotation.util.AnnotTableInfos;
//import domain.Department;
//import domain.Medic;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@InheritedBy(Medic.class)
//public class StuffAnnotTableInfo extends PersonAnnotTableInfo
//                                 implements StuffTableInfo {
//    private String stuffTableName;
//    private String departmentIdColumn;
//    private List<String> stuffColumns;
//
//    StuffAnnotTableInfo() {}
//
//    public String getDepartmentIdColumn() {
//        return departmentIdColumn;
//    }
//
//    public String getStuffTableName() {
//        return stuffTableName;
//    }
//
//    public List<String> getStuffColumns() {
//        return stuffColumns;
//    }
//
//    void fillTableInfo() {
//        super.fillTableInfo();
//
//        stuffTableName = AnnotTableInfos
//                .getInheritedByTableName(StuffAnnotTableInfo.class);
//
//        OneToMany departmentToStuffAnnot = AnnotTableInfos
//                .getOneToManyRelation(Department.class, stuffTableName);
//
//        departmentIdColumn = departmentToStuffAnnot.foreignKey();
//        columns.add(departmentIdColumn);
//
//        stuffColumns = new ArrayList<>();
//        stuffColumns.addAll(columns);
//    }
//
//    static StuffAnnotTableInfo createAnnotTableInfo() {
//        StuffAnnotTableInfo tableInfo = new StuffAnnotTableInfo();
//        tableInfo.fillTableInfo();
//        return tableInfo;
//    }
//
//
//}
