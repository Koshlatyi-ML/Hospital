package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.annotation.OneToMany;
import dao.metadata.util.TableInfoUtils;
import domain.Doctor;
import domain.Patient;

import java.util.Map;

@Entity(Patient.class)
public class PatientTableInfo extends PersonTableInfo {
    private String doctorIdColumn;
    private String diagnosisColumn;
    private String complaintsColumn;
    private String stateColumn;

    PatientTableInfo() {}

    public String getDoctorIdColumn() {
        return doctorIdColumn;
    }

    void setDoctorIdColumn(String doctorIdColumn) {
        this.doctorIdColumn = doctorIdColumn;
    }

    public String getDiagnosisColumn() {
        return diagnosisColumn;
    }

    void setDiagnosisColumn(String diagnosisColumn) {
        this.diagnosisColumn = diagnosisColumn;
    }

    public String getComplaintsColumn() {
        return complaintsColumn;
    }

    void setComplaintsColumn(String complaintsColumn) {
        this.complaintsColumn = complaintsColumn;
    }

    public String getStateColumn() {
        return stateColumn;
    }

    void setStateColumn(String stateColumn) {
        this.stateColumn = stateColumn;
    }

    public static PatientTableInfo createPatientTableInfo() {
        PatientTableInfo tableInfo = new PatientTableInfo();
        fillTableInfo(tableInfo);

        Class<?> entityClass = PatientTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        String tableName = TableInfoUtils.getTableName(entityClass);
        tableInfo.setTableName(TableInfoUtils.getTableName(entityClass));

        OneToMany doctorToPatientAnnot
                = TableInfoUtils.getOneToManyRelation(Doctor.class, tableName);

        String column = doctorToPatientAnnot.foreignKey();
        tableInfo.setDoctorIdColumn(column);
        tableInfo.getColumnNames().add(column);

        Map<String, String> fieldColumnMap
                = TableInfoUtils.loadFieldColumnMap(entityClass);

        column = fieldColumnMap.get("complaints");
        tableInfo.setComplaintsColumn(column);
        tableInfo.getColumnNames().add(column);

        column = fieldColumnMap.get("diagnosis");
        tableInfo.setDiagnosisColumn(column);
        tableInfo.getColumnNames().add(column);

        column = fieldColumnMap.get("state");
        tableInfo.setStateColumn(column);
        tableInfo.getColumnNames().add(column);

        return tableInfo;
    }
}
