package dao.metadata.constant;

import dao.metadata.*;

public class ConstantTableInfoFactory extends TableInfoFactory {
    private DepartmentConstantTableInfo departmentConstantTableInfo;
    private StuffConstantTableInfo stuffTableInfo;
    private DoctorConstantTableInfo doctorTableInfo;
    private MedicConstantTableInfo medicTableInfo;
    private PatientConstantTableInfo patientTableInfo;
    private TherapyConstantTableInfo therapyTableInfo;

    private ConstantTableInfoFactory() {
        departmentConstantTableInfo = new DepartmentConstantTableInfo.Builder()
                .setTableName(DepartmentTableInfo.TABLE_NAME)
                .setIdColumn(DepartmentTableInfo.ID_COLUMN)
                .setNameColumn(DepartmentTableInfo.NAME_COLUMN)
                .build();

        doctorTableInfo = new DoctorConstantTableInfo.Builder()
                .setTableName(DoctorTableInfo.TABLE_NAME)
                .setIdColumn(DoctorTableInfo.STUFF_ID_COLUMN)
                .build();

        medicTableInfo = new MedicConstantTableInfo.Builder()
                .setTableName(MedicTableInfo.TABLE_NAME)
                .setIdColumn(MedicTableInfo.STUFF_ID_COLUMN)
                .build();

        stuffTableInfo = new StuffConstantTableInfo.Builder()
                .setTableName(StuffTableInfo.TABLE_NAME)
                .setIdColumn(StuffTableInfo.ID_COLUMN)
                .setNameColumn(StuffTableInfo.NAME_COLUMN)
                .setSurnameColumn(StuffTableInfo.SURNAME_COLUMN)
                .setDepartmentIdColumn(StuffTableInfo.DEPARTMENT_ID_COLUMN)
                .build();

        patientTableInfo = new PatientConstantTableInfo.Builder()
                .setTableName(PatientTableInfo.TABLE_NAME)
                .setIdColumn(PatientTableInfo.ID_COLUMN)
                .setNameColumn(PatientTableInfo.NAME_COLUMN)
                .setSurnameColumn(PatientTableInfo.SURNAME_COLUMN)
                .setDoctorIdColumn(PatientTableInfo.DOCTOR_ID_COLUMN)
                .setComplaintsColumn(PatientTableInfo.COMPLAINTS_COLUMN)
                .setDiagnosisColumn(PatientTableInfo.DIAGNOSIS_COLUMN)
                .setStateColumn(PatientTableInfo.STATE_COLUMN)
                .build();

        therapyTableInfo = new TherapyConstantTableInfo.Builder()
                .setTableName(TherapyTableInfo.TABLE_NAME)
                .setIdColumn(TherapyTableInfo.ID_COLUMN)
                .setTitleColumn(TherapyTableInfo.TITLE_COLUMN)
                .setTypeColumn(TherapyTableInfo.TYPE_COLUMN)
                .setDescriptionColumn(TherapyTableInfo.DESCRIPTION_COLUMN)
                .setAppointmentDateColumn(TherapyTableInfo.APPOINTMENT_DATE_COLUMN)
                .setCompleteDateColumn(TherapyTableInfo.COMPLETE_DATE_COLUMN)
                .setPatientIdColumn(TherapyTableInfo.PATIENT_ID_COLUMN)
                .setPerformerIdColumn(TherapyTableInfo.PERFORMER_ID_COLUMN)
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
}
