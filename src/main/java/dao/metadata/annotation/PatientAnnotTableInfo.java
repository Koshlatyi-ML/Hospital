package dao.metadata.annotation;

import dao.metadata.PatientTableInfo;
import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.mapping.OneToMany;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Doctor;
import domain.Patient;

import java.util.List;
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

    public String getDiagnosisColumn() {
        return diagnosisColumn;
    }

    public String getComplaintsColumn() {
        return complaintsColumn;
    }

    public String getStateColumn() {
        return stateColumn;
    }

    @Override
    public List<String> getEntityfulColumns() {
        return null;
    }

    void fillTableInfo() {
        super.fillTableInfo();
        Class<?> entityClass = PatientAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        tableName = AnnotTableInfos.getTableName(entityClass);

        OneToMany doctorToPatientAnnot = AnnotTableInfos
                .getOneToManyRelation(Doctor.class, tableName);
        doctorIdColumn = String.format("%s.%s", tableName, doctorToPatientAnnot.foreignKey());
        columns.add(doctorIdColumn);

        Map<String, String> fieldColumnMap = AnnotTableInfos.loadFieldColumnMap(entityClass);
        complaintsColumn = String.format("%s.%s", tableName, fieldColumnMap.get("complaints"));
        columns.add(complaintsColumn);
        diagnosisColumn = String.format("%s.%s", tableName, fieldColumnMap.get("diagnosis"));
        columns.add(diagnosisColumn);
        stateColumn = String.format("%s.%s", tableName, fieldColumnMap.get("state"));
        columns.add(stateColumn);
    }

    static PatientAnnotTableInfo createAnnotTableInfo() {
        PatientAnnotTableInfo tableInfo = new PatientAnnotTableInfo();
        tableInfo.fillTableInfo();
        return tableInfo;
    }
}
