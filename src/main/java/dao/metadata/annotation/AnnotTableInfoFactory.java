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

    private static final AnnotTableInfoFactory INSTANCE
            = new AnnotTableInfoFactory();

    private AnnotTableInfoFactory() {}

    public static AnnotTableInfoFactory getInstance() {
        return INSTANCE;
    }

    public DepartmentAnnotTableInfo getDepartmentTableInfo() {
        return DEPARTMENT_ANNOT_TABLE_INFO;
    }

    public StuffTableInfo getStuffTableInfo() {
        return STUFF_ANNOT_TABLE_INFO;
    }

    public DoctorTableInfo getDoctorTableInfo() {
        return DOCTOR_ANNOT_TABLE_INFO;
    }

    public MedicTableInfo getMedicTableInfo() {
        return MEDIC_ANNOT_TABLE_INFO;
    }

    public PatientTableInfo getPatientTableInfo() {
        return PATIENT_ANNOT_TABLE_INFO;
    }

    public TherapyTableInfo getTherapyTableInfo() {
        return THERAPY_ANNOT_TABLE_INFO;
    }
}
