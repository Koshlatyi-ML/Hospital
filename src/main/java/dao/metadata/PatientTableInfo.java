package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.annotation.OneToMany;
import dao.metadata.util.TableInfoUtils;
import domain.Department;
import domain.Doctor;
import domain.Patient;

import java.util.Map;

@Entity(Patient.class)
public class PatientTableInfo extends PersonTableInfo {
    private String doctorIdColumn;
    private String diagnosisColumn;
    private String complaintsColumn;
    private String stateColumn;

    public PatientTableInfo() {}

    public String getDoctorIdColumn() {
        return doctorIdColumn;
    }

    public void setDoctorIdColumn(String doctorIdColumn) {
        this.doctorIdColumn = doctorIdColumn;
    }

    public String getDiagnosisColumn() {
        return diagnosisColumn;
    }

    public void setDiagnosisColumn(String diagnosisColumn) {
        this.diagnosisColumn = diagnosisColumn;
    }

    public String getComplaintsColumn() {
        return complaintsColumn;
    }

    public void setComplaintsColumn(String complaintsColumn) {
        this.complaintsColumn = complaintsColumn;
    }

    public String getStateColumn() {
        return stateColumn;
    }

    public void setStateColumn(String stateColumn) {
        this.stateColumn = stateColumn;
    }

    public static PatientTableInfo createPatientTableInfo() {
        PatientTableInfo tableInfo = new PatientTableInfo();
        fillTableInfo(tableInfo);

        Class<?> entityClass = PatientTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        String tableName = TableInfoUtils.getTableName(entityClass);
        tableInfo.setTableName(TableInfoUtils.getTableName(entityClass));

        Map<String, String> fieldColumnMap
                = TableInfoUtils.loadFieldColumnMap(entityClass);
        tableInfo.setComplaintsColumn(fieldColumnMap.get("complaints"));
        tableInfo.setDiagnosisColumn(fieldColumnMap.get("diagnosis"));
        tableInfo.setStateColumn(fieldColumnMap.get("state"));

        OneToMany oneToManyAnnot
                = TableInfoUtils.getOneToManyRelation(Doctor.class, tableName);
        tableInfo.setDoctorIdColumn(oneToManyAnnot.foreignKey());

        // arranging get all cols logic

        return tableInfo;
    }
}
