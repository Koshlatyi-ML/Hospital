package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.annotation.InheritedBy;
import dao.metadata.annotation.OneToMany;
import dao.metadata.util.TableInfoUtils;
import domain.Doctor;
import domain.Therapy;

import java.util.Map;

@Entity(Therapy.class)
public class TherapyTableInfo extends IdHolderTableInfo {
    private String nameColumn;
    private String descriptionColumn;
    private String typeColumn;
    private String appointmentDateColumn;
    private String completeDateColumn;
    private String patientIdColumn;
    private String performerIdColumn;

    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getDescriptionColumn() {
        return descriptionColumn;
    }

    public void setDescriptionColumn(String descriptionColumn) {
        this.descriptionColumn = descriptionColumn;
    }

    public String getTypeColumn() {
        return typeColumn;
    }

    public void setTypeColumn(String typeColumn) {
        this.typeColumn = typeColumn;
    }

    public String getAppointmentDateColumn() {
        return appointmentDateColumn;
    }

    public void setAppointmentDateColumn(String appointmentDateColumn) {
        this.appointmentDateColumn = appointmentDateColumn;
    }

    public String getCompleteDateColumn() {
        return completeDateColumn;
    }

    public void setCompleteDateColumn(String completeDateColumn) {
        this.completeDateColumn = completeDateColumn;
    }

    public String getPatientIdColumn() {
        return patientIdColumn;
    }

    public void setPatientIdColumn(String patientIdColumn) {
        this.patientIdColumn = patientIdColumn;
    }

    public String getPerformerIdColumn() {
        return performerIdColumn;
    }

    public void setPerformerIdColumn(String performerIdColumn) {
        this.performerIdColumn = performerIdColumn;
    }

    public static TherapyTableInfo createTherapyTableInfo() {
        TherapyTableInfo tableInfo = new TherapyTableInfo();
        fillTableInfo(tableInfo);

        Class<?> entityClass = TherapyTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        String tableName = TableInfoUtils.getTableName(entityClass);
        tableInfo.setTableName(TableInfoUtils.getTableName(entityClass));

        Map<String, String> fieldColumnMap
                = TableInfoUtils.loadFieldColumnMap(entityClass);
        tableInfo.setNameColumn(fieldColumnMap.get("name"));
        tableInfo.setDescriptionColumn(fieldColumnMap.get("description"));
        tableInfo.setTypeColumn(fieldColumnMap.get("type"));
        tableInfo.setAppointmentDateColumn(fieldColumnMap.get("appointmentDateTime"));
        tableInfo.setCompleteDateColumn(fieldColumnMap.get("completeDateTime"));
        tableInfo.setPatientIdColumn(fieldColumnMap.get("patient"));

        Class<?> stuffDerivedClass = StuffTableInfo.class
                .getDeclaredAnnotation(InheritedBy.class).value();
        OneToMany stuffToTherapyAnnot
                = TableInfoUtils.getOneToManyRelation(stuffDerivedClass, tableName);
        tableInfo.setPerformerIdColumn(stuffToTherapyAnnot.foreignKey());

        // arranging get all cols logic

        return tableInfo;
    }
}
