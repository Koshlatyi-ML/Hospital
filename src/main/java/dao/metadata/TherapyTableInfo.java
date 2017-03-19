package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.annotation.InheritedBy;
import dao.metadata.annotation.OneToMany;
import dao.metadata.util.TableInfoUtils;
import domain.Therapy;

import java.util.Map;

@Entity(Therapy.class)
public class TherapyTableInfo extends IdHolderTableInfo {
    private String nameColumn;
    private String typeColumn;
    private String descriptionColumn;
    private String appointmentDateColumn;
    private String completeDateColumn;
    private String patientIdColumn;
    private String performerIdColumn;

    TherapyTableInfo() {}

    public String getNameColumn() {
        return nameColumn;
    }

    void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getTypeColumn() {
        return typeColumn;
    }

    void setTypeColumn(String typeColumn) {
        this.typeColumn = typeColumn;
    }

    public String getDescriptionColumn() {
        return descriptionColumn;
    }

    void setDescriptionColumn(String descriptionColumn) {
        this.descriptionColumn = descriptionColumn;
    }


    public String getAppointmentDateColumn() {
        return appointmentDateColumn;
    }

    void setAppointmentDateColumn(String appointmentDateColumn) {
        this.appointmentDateColumn = appointmentDateColumn;
    }

    public String getCompleteDateColumn() {
        return completeDateColumn;
    }

    void setCompleteDateColumn(String completeDateColumn) {
        this.completeDateColumn = completeDateColumn;
    }

    public String getPatientIdColumn() {
        return patientIdColumn;
    }

    void setPatientIdColumn(String patientIdColumn) {
        this.patientIdColumn = patientIdColumn;
    }

    public String getPerformerIdColumn() {
        return performerIdColumn;
    }

    void setPerformerIdColumn(String performerIdColumn) {
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

        String column = fieldColumnMap.get("name");
        tableInfo.setNameColumn(column);
        tableInfo.getColumnNames().add(column);

        column = fieldColumnMap.get("type");
        tableInfo.setTypeColumn(column);
        tableInfo.getColumnNames().add(column);

        column = fieldColumnMap.get("description");
        tableInfo.setDescriptionColumn(column);
        tableInfo.getColumnNames().add(column);

        column = fieldColumnMap.get("appointmentDateTime");
        tableInfo.setAppointmentDateColumn(column);
        tableInfo.getColumnNames().add(column);

        column = fieldColumnMap.get("completeDateTime");
        tableInfo.setCompleteDateColumn(column);
        tableInfo.getColumnNames().add(column);

        column = fieldColumnMap.get("patient");
        tableInfo.setPatientIdColumn(column);
        tableInfo.getColumnNames().add(column);

        Class<?> stuffDerivedClass = StuffTableInfo.class
                .getDeclaredAnnotation(InheritedBy.class).value();
        OneToMany stuffToTherapyAnnot
                = TableInfoUtils.getOneToManyRelation(stuffDerivedClass, tableName);

        column = stuffToTherapyAnnot.foreignKey();
        tableInfo.setPerformerIdColumn(column);
        tableInfo.getColumnNames().add(column);

        return tableInfo;
    }
}
