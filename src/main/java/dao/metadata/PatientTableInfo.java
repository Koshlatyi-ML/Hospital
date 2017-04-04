package dao.metadata;

public interface PatientTableInfo extends PersonTableInfo {
    String getDoctorIdColumn(ColumnNameStyle columnNameStyle);
    String getDiagnosisColumn(ColumnNameStyle columnNameStyle);
    String getComplaintsColumn(ColumnNameStyle columnNameStyle);
    String getStateColumn(ColumnNameStyle columnNameStyle);
}
