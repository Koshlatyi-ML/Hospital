//package dao.metadata.annotation;
//
//import dao.metadata.PersonTableInfo;
//import dao.metadata.annotation.mapping.Entity;
//import dao.metadata.annotation.util.AnnotTableInfos;
//import domain.Person;
//
//import java.util.Map;
//
//@Entity(Person.class)
//public abstract class PersonAnnotTableInfo extends IdHolderAnnotTableInfo
//                                           implements PersonTableInfo {
//    private String nameColumn;
//    private String surnameColumn;
//
//    public String getTitleColumn() {
//        return nameColumn;
//    }
//
//    public String getSurnameColumn() {
//        return surnameColumn;
//    }
//
//    void fillTableInfo() {
//        super.fillTableInfo();
//        Class entityClass = PersonAnnotTableInfo.class
//                .getDeclaredAnnotation(Entity.class).value();
//
//        Map<String, String> fieldColumnMap =
//                AnnotTableInfos.loadFieldColumnMap(entityClass);
//        nameColumn = String.format("%s.%s", tableName, fieldColumnMap.get("name"));
//        columns.add(nameColumn);
//        surnameColumn = String.format("%s.%s", tableName, fieldColumnMap.get("surname"));
//        columns.add(surnameColumn);
//    }
//}
