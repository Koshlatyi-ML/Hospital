package dao.metadata.annotation;

import dao.metadata.PatientTableInfo;
import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.mapping.OneToMany;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Doctor;
import domain.Patient;

import java.util.Map;

@Entity(Patient.class)
public class PatientAnnotTableInfo extends PersonAnnotTableInfo
                                   implements PatientTableInfo{
    private String doctorIdColumn;
    private String diagnosisColumn;
    private String complaintsColumn;
    private String stateColumn;

    private PatientAnnotTableInfo() {}

    public String getDoctorIdColumn() {
        return doctorIdColumn;
    }

    private void setDoctorIdColumn(String doctorIdColumn) {
        this.doctorIdColumn = doctorIdColumn;
    }

    public String getDiagnosisColumn() {
        return diagnosisColumn;
    }

    private void setDiagnosisColumn(String diagnosisColumn) {
        this.diagnosisColumn = diagnosisColumn;
    }

    public String getComplaintsColumn() {
        return complaintsColumn;
    }

    private void setComplaintsColumn(String complaintsColumn) {
        this.complaintsColumn = complaintsColumn;
    }

    public String getStateColumn() {
        return stateColumn;
    }

    private void setStateColumn(String stateColumn) {
        this.stateColumn = stateColumn;
    }

    void fillTableInfo(PatientAnnotTableInfo tableInfo) {
        super.fillTableInfo(tableInfo);

        Class<?> entityClass = PatientAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        String tableName = AnnotTableInfos.getTableName(entityClass);
        tableInfo.setTableName(AnnotTableInfos.getTableName(entityClass));

        OneToMany doctorToPatientAnnot
                = AnnotTableInfos.getOneToManyRelation(Doctor.class, tableName);

        String column = doctorToPatientAnnot.foreignKey();
        tableInfo.setDoctorIdColumn(column);
        tableInfo.getColumns().add(column);

        Map<String, String> fieldColumnMap
                = AnnotTableInfos.loadFieldColumnMap(entityClass);

        column = fieldColumnMap.get("complaints");
        tableInfo.setComplaintsColumn(column);
        tableInfo.getColumns().add(column);

        column = fieldColumnMap.get("diagnosis");
        tableInfo.setDiagnosisColumn(column);
        tableInfo.getColumns().add(column);

        column = fieldColumnMap.get("state");
        tableInfo.setStateColumn(column);
        tableInfo.getColumns().add(column);
    }

    static PatientAnnotTableInfo createAnnotTableInfo() {
        PatientAnnotTableInfo tableInfo = new PatientAnnotTableInfo();
        tableInfo.fillTableInfo(tableInfo);
        return tableInfo;
    }
}
