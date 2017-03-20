package dao.metadata;

public interface PatientTableInfo extends PersonTableInfo {
    String getDoctorIdColumn();
    String getDiagnosisColumn();
    String getComplaintsColumn();
    String getStateColumn();
}
