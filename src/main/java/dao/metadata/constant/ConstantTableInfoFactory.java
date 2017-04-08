package dao.metadata.constant;

import dao.metadata.*;

public class ConstantTableInfoFactory extends TableInfoFactory {
    private DepartmentConstantTableInfo departmentConstantTableInfo;
    private StuffConstantTableInfo stuffTableInfo;
    private DoctorConstantTableInfo doctorTableInfo;
    private MedicConstantTableInfo medicTableInfo;
    private PatientConstantTableInfo patientTableInfo;
    private TherapyConstantTableInfo therapyTableInfo;
    private CredentialsConstantTableInfo credentialsTableInfo;

    private ConstantTableInfoFactory() {
        departmentConstantTableInfo = new DepartmentConstantTableInfo.Builder()
                .setTableName(DepartmentConstantTableInfo.ColumnNames.TABLE_NAME)
                .setIdColumn(DepartmentConstantTableInfo.ColumnNames.ID_COLUMN)
                .setNameColumn(DepartmentConstantTableInfo.ColumnNames.NAME_COLUMN)
                .build();

        doctorTableInfo = new DoctorConstantTableInfo.Builder()
                .setTableName(DoctorConstantTableInfo.ColumnNames.TABLE_NAME)
                .setIdColumn(DoctorConstantTableInfo.ColumnNames.STUFF_ID_COLUMN)
                .setCredentialsIdColumn(DoctorConstantTableInfo.ColumnNames.CREDENTIALS_ID_COLUMN)
                .build();

        medicTableInfo = new MedicConstantTableInfo.Builder()
                .setTableName(MedicConstantTableInfo.ColumnNames.TABLE_NAME)
                .setIdColumn(MedicConstantTableInfo.ColumnNames.STUFF_ID_COLUMN)
                .setCredentialsIdColumn(MedicConstantTableInfo.ColumnNames.CREDENTIALS_ID_COLUMN)
                .build();

        stuffTableInfo = new StuffConstantTableInfo.Builder()
                .setTableName(StuffConstantTableInfo.ColumnNames.TABLE_NAME)
                .setIdColumn(StuffConstantTableInfo.ColumnNames.ID_COLUMN)
                .setNameColumn(StuffConstantTableInfo.ColumnNames.NAME_COLUMN)
                .setSurnameColumn(StuffConstantTableInfo.ColumnNames.SURNAME_COLUMN)
                .setDepartmentIdColumn(StuffConstantTableInfo.ColumnNames.DEPARTMENT_ID_COLUMN)
                .build();

        patientTableInfo = new PatientConstantTableInfo.Builder()
                .setTableName(PatientConstantTableInfo.ColumnNames.TABLE_NAME)
                .setIdColumn(PatientConstantTableInfo.ColumnNames.ID_COLUMN)
                .setNameColumn(PatientConstantTableInfo.ColumnNames.NAME_COLUMN)
                .setSurnameColumn(PatientConstantTableInfo.ColumnNames.SURNAME_COLUMN)
                .setDoctorIdColumn(PatientConstantTableInfo.ColumnNames.DOCTOR_ID_COLUMN)
                .setComplaintsColumn(PatientConstantTableInfo.ColumnNames.COMPLAINTS_COLUMN)
                .setDiagnosisColumn(PatientConstantTableInfo.ColumnNames.DIAGNOSIS_COLUMN)
                .setStateColumn(PatientConstantTableInfo.ColumnNames.STATE_COLUMN)
                .setCredentialsIdColumn(PatientConstantTableInfo.ColumnNames.CREDENTIALS_ID_COLUMN)
                .build();

        therapyTableInfo = new TherapyConstantTableInfo.Builder()
                .setTableName(TherapyConstantTableInfo.ColumnNames.TABLE_NAME)
                .setIdColumn(TherapyConstantTableInfo.ColumnNames.ID_COLUMN)
                .setTitleColumn(TherapyConstantTableInfo.ColumnNames.TITLE_COLUMN)
                .setTypeColumn(TherapyConstantTableInfo.ColumnNames.TYPE_COLUMN)
                .setDescriptionColumn(TherapyConstantTableInfo.ColumnNames.DESCRIPTION_COLUMN)
                .setAppointmentDateColumn(TherapyConstantTableInfo.ColumnNames.APPOINTMENT_DATE_COLUMN)
                .setCompleteDateColumn(TherapyConstantTableInfo.ColumnNames.COMPLETE_DATE_COLUMN)
                .setPatientIdColumn(TherapyConstantTableInfo.ColumnNames.PATIENT_ID_COLUMN)
                .setPerformerIdColumn(TherapyConstantTableInfo.ColumnNames.PERFORMER_ID_COLUMN)
                .build();

        credentialsTableInfo = new CredentialsConstantTableInfo.Builder()
                .setTableName(CredentialsConstantTableInfo.ColumnNames.TABLE_NAME)
                .setIdColumn(CredentialsConstantTableInfo.ColumnNames.ID)
                .setLoginColumn(CredentialsConstantTableInfo.ColumnNames.LOGIN)
                .setPasswordColumn(CredentialsConstantTableInfo.ColumnNames.PASSWORD)
                .setRoleColumn(CredentialsConstantTableInfo.ColumnNames.ROLE)
                .build();
    }

    @Override
    public DepartmentConstantTableInfo getDepartmentTableInfo() {
        return departmentConstantTableInfo;
    }

    @Override
    public StuffConstantTableInfo getStuffTableInfo() {
        return stuffTableInfo;
    }

    @Override
    public DoctorConstantTableInfo getDoctorTableInfo() {
        return doctorTableInfo;
    }

    @Override
    public MedicConstantTableInfo getMedicTableInfo() {
        return medicTableInfo;
    }

    @Override
    public PatientConstantTableInfo getPatientTableInfo() {
        return patientTableInfo;
    }

    @Override
    public TherapyConstantTableInfo getTherapyTableInfo() {
        return therapyTableInfo;
    }

    @Override
    public CredentialsTableInfo getCredentialsTableInfo() {
        return credentialsTableInfo;
    }
}
