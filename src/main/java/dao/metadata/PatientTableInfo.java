package dao.metadata;

public interface PatientTableInfo extends IdHolderTableInfo{
    String getNameColumn();
    String getSurnameColumn();
    String getDoctorIdColumn();
    String getDiagnosisColumn();
    String getComplaintsColumn();
    String getStateColumn();
}
