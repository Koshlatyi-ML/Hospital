//package dao.metadata.annotation;
//
//import dao.metadata.TherapyTableInfo;
//import dao.metadata.annotation.mapping.Entity;
//import dao.metadata.annotation.mapping.InheritedBy;
//import dao.metadata.annotation.mapping.OneToMany;
//import dao.metadata.annotation.util.AnnotTableInfos;
//import domain.Therapy;
//
//import java.util.List;
//import java.util.Map;
//
//@Entity(Therapy.class)
//public class TherapyAnnotTableInfo extends IdHolderAnnotTableInfo
//                                   implements TherapyTableInfo {
//    private String nameColumn;
//    private String typeColumn;
//    private String descriptionColumn;
//    private String appointmentDateColumn;
//    private String completeDateColumn;
//    private String patientIdColumn;
//    private String performerIdColumn;
//
//    private TherapyAnnotTableInfo() {}
//
//    public String getTitleColumn() {
//        return nameColumn;
//    }
//
//    public String getTypeColumn() {
//        return typeColumn;
//    }
//
//    public String getDescriptionColumn() {
//        return descriptionColumn;
//    }
//
//    public String getAppointmentDateColumn() {
//        return appointmentDateColumn;
//    }
//
//    public String getCompleteDateColumn() {
//        return completeDateColumn;
//    }
//
//    public String getPatientIdColumn() {
//        return patientIdColumn;
//    }
//
//    public String getPerformerIdColumn() {
//        return performerIdColumn;
//    }
//
//    void fillTableInfo() {
//        super.fillTableInfo();
//        Class<?> entityClass = TherapyAnnotTableInfo.class
//                .getDeclaredAnnotation(Entity.class).value();
//
//        tableName = AnnotTableInfos.getTableName(entityClass);
//
//        Map<String, String> fieldColumnMap = AnnotTableInfos
//                .loadFieldColumnMap(entityClass);
//        nameColumn = fieldColumnMap.get("name");
//        columns.add(nameColumn);
//        typeColumn = fieldColumnMap.get("type");
//        columns.add(typeColumn);
//        descriptionColumn = fieldColumnMap.get("description");
//        columns.add(descriptionColumn);
//        appointmentDateColumn = fieldColumnMap.get("appointmentDateTime");
//        columns.add(appointmentDateColumn);
//        completeDateColumn = fieldColumnMap.get("completeDateTime");
//        columns.add(completeDateColumn);
//        patientIdColumn = fieldColumnMap.get("patient");
//        columns.add(patientIdColumn);
//
//        Class<?> stuffDerivedClass = StuffAnnotTableInfo.class
//                .getDeclaredAnnotation(InheritedBy.class).value();
//        OneToMany stuffToTherapyAnnot = AnnotTableInfos
//                .getOneToManyRelation(stuffDerivedClass, tableName);
//
//        performerIdColumn = stuffToTherapyAnnot.foreignKey();
//        columns.add(performerIdColumn);
//    }
//
//    static TherapyAnnotTableInfo createAnnotTableInfo() {
//        TherapyAnnotTableInfo tableInfo = new TherapyAnnotTableInfo();
//        tableInfo.fillTableInfo();
//        return tableInfo;
//    }
//}
