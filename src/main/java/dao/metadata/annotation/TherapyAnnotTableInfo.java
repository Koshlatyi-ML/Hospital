package dao.metadata.annotation;

import dao.metadata.TherapyTableInfo;
import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.mapping.InheritedBy;
import dao.metadata.annotation.mapping.OneToMany;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Therapy;

import java.util.Map;

@Entity(Therapy.class)
public class TherapyAnnotTableInfo extends IdHolderAnnotTableInfo
                                   implements TherapyTableInfo {
    private String nameColumn;
    private String typeColumn;
    private String descriptionColumn;
    private String appointmentDateColumn;
    private String completeDateColumn;
    private String patientIdColumn;
    private String performerIdColumn;

    private TherapyAnnotTableInfo() {}

    public String getNameColumn() {
        return nameColumn;
    }

    private void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getTypeColumn() {
        return typeColumn;
    }

    private void setTypeColumn(String typeColumn) {
        this.typeColumn = typeColumn;
    }

    public String getDescriptionColumn() {
        return descriptionColumn;
    }

    private void setDescriptionColumn(String descriptionColumn) {
        this.descriptionColumn = descriptionColumn;
    }


    public String getAppointmentDateColumn() {
        return appointmentDateColumn;
    }

    private void setAppointmentDateColumn(String appointmentDateColumn) {
        this.appointmentDateColumn = appointmentDateColumn;
    }

    public String getCompleteDateColumn() {
        return completeDateColumn;
    }

    private void setCompleteDateColumn(String completeDateColumn) {
        this.completeDateColumn = completeDateColumn;
    }

    public String getPatientIdColumn() {
        return patientIdColumn;
    }

    private void setPatientIdColumn(String patientIdColumn) {
        this.patientIdColumn = patientIdColumn;
    }

    public String getPerformerIdColumn() {
        return performerIdColumn;
    }

    private void setPerformerIdColumn(String performerIdColumn) {
        this.performerIdColumn = performerIdColumn;
    }

    void fillTableInfo(TherapyAnnotTableInfo tableInfo) {
        super.fillTableInfo(tableInfo);

        Class<?> entityClass = TherapyAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        String tableName = AnnotTableInfos.getTableName(entityClass);
        tableInfo.setTableName(AnnotTableInfos.getTableName(entityClass));

        Map<String, String> fieldColumnMap
                = AnnotTableInfos.loadFieldColumnMap(entityClass);

        String column = fieldColumnMap.get("name");
        tableInfo.setNameColumn(column);
        tableInfo.getColumns().add(column);

        column = fieldColumnMap.get("type");
        tableInfo.setTypeColumn(column);
        tableInfo.getColumns().add(column);

        column = fieldColumnMap.get("description");
        tableInfo.setDescriptionColumn(column);
        tableInfo.getColumns().add(column);

        column = fieldColumnMap.get("appointmentDateTime");
        tableInfo.setAppointmentDateColumn(column);
        tableInfo.getColumns().add(column);

        column = fieldColumnMap.get("completeDateTime");
        tableInfo.setCompleteDateColumn(column);
        tableInfo.getColumns().add(column);

        column = fieldColumnMap.get("patient");
        tableInfo.setPatientIdColumn(column);
        tableInfo.getColumns().add(column);

        Class<?> stuffDerivedClass = StuffAnnotTableInfo.class
                .getDeclaredAnnotation(InheritedBy.class).value();
        OneToMany stuffToTherapyAnnot
                = AnnotTableInfos.getOneToManyRelation(stuffDerivedClass, tableName);

        column = stuffToTherapyAnnot.foreignKey();
        tableInfo.setPerformerIdColumn(column);
        tableInfo.getColumns().add(column);
    }

    static TherapyAnnotTableInfo createAnnotTableInfo() {
        TherapyAnnotTableInfo tableInfo = new TherapyAnnotTableInfo();
        tableInfo.fillTableInfo(tableInfo);
        return tableInfo;
    }
}
