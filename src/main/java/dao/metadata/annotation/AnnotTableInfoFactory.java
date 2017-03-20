package dao.metadata.annotation;

import dao.metadata.*;

public class AnnotTableInfoFactory extends TableInfoFactory {

    private static final DepartmentAnnotTableInfo DEPARTMENT_ANNOT_TABLE_INFO
            = DepartmentAnnotTableInfo.createAnnotTableInfo();
    private static final StuffAnnotTableInfo STUFF_ANNOT_TABLE_INFO
            = StuffAnnotTableInfo.createAnnotTableInfo();
    private static final DoctorAnnotTableInfo DOCTOR_ANNOT_TABLE_INFO
            = DoctorAnnotTableInfo.createAnnotTableInfo();
    private static final MedicAnnotTableInfo MEDIC_ANNOT_TABLE_INFO
            = MedicAnnotTableInfo.createAnnotTableInfo();
    private static final PatientAnnotTableInfo PATIENT_ANNOT_TABLE_INFO
            = PatientAnnotTableInfo.createAnnotTableInfo();
    private static final TherapyAnnotTableInfo THERAPY_ANNOT_TABLE_INFO
            = TherapyAnnotTableInfo.createAnnotTableInfo();

    private AnnotTableInfoFactory() {
    }

    @Override
    public DepartmentAnnotTableInfo getDepartmentTableInfo() {
        return DEPARTMENT_ANNOT_TABLE_INFO;
    }

    @Override
    public StuffTableInfo getStuffTableInfo() {
        return STUFF_ANNOT_TABLE_INFO;
    }

    @Override
    public DoctorTableInfo getDoctorTableInfo() {
        return DOCTOR_ANNOT_TABLE_INFO;
    }

    @Override
    public MedicTableInfo getMedicTableInfo() {
        return MEDIC_ANNOT_TABLE_INFO;
    }

    @Override
    public PatientTableInfo getPatientTableInfo() {
        return PATIENT_ANNOT_TABLE_INFO;
    }

    @Override
    public TherapyTableInfo getTherapyTableInfo() {
        return THERAPY_ANNOT_TABLE_INFO;
    }
}
