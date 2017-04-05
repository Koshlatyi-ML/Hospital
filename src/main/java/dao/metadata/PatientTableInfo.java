package dao.metadata;

public interface PatientTableInfo extends PersonTableInfo {
    String TABLE_NAME = "patients";
    String ID_COLUMN = "id";
    String NAME_COLUMN = "name";
    String SURNAME_COLUMN = "surname";
    String DOCTOR_ID_COLUMN = "doctor_id";
    String COMPLAINTS_COLUMN = "complaints";
    String DIAGNOSIS_COLUMN = "diagnosis";
    String STATE_COLUMN = "sate";

    String getDoctorIdColumn(ColumnNameStyle columnNameStyle);
    String getDiagnosisColumn(ColumnNameStyle columnNameStyle);
    String getComplaintsColumn(ColumnNameStyle columnNameStyle);
    String getStateColumn(ColumnNameStyle columnNameStyle);
}
