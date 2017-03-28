package dao.metadata.annotation;

import dao.metadata.*;

public class AnnotTableInfoFactory extends TableInfoFactory {

    private DepartmentAnnotTableInfo departmentAnnotTableInfo
            = DepartmentAnnotTableInfo.createAnnotTableInfo();
    private StuffAnnotTableInfo stuffAnnotTableInfo
            = StuffAnnotTableInfo.createAnnotTableInfo();
    private DoctorAnnotTableInfo doctorAnnotTableInfo
            = DoctorAnnotTableInfo.createAnnotTableInfo();
    private MedicAnnotTableInfo medicAnnotTableInfo
            = MedicAnnotTableInfo.createAnnotTableInfo();
    private PatientAnnotTableInfo patientAnnotTableInfo
            = PatientAnnotTableInfo.createAnnotTableInfo();
    private TherapyAnnotTableInfo therapyAnnotTableInfo
            = TherapyAnnotTableInfo.createAnnotTableInfo();

    private AnnotTableInfoFactory() {}

    @Override
    public DepartmentAnnotTableInfo getDepartmentTableInfo() {
        return departmentAnnotTableInfo;
    }

    @Override
    public StuffTableInfo getStuffTableInfo() {
        return stuffAnnotTableInfo;
    }

    @Override
    public DoctorTableInfo getDoctorTableInfo() {
        return doctorAnnotTableInfo;
    }

    @Override
    public MedicTableInfo getMedicTableInfo() {
        return medicAnnotTableInfo;
    }

    @Override
    public PatientTableInfo getPatientTableInfo() {
        return patientAnnotTableInfo;
    }

    @Override
    public TherapyTableInfo getTherapyTableInfo() {
        return therapyAnnotTableInfo;
    }
}
