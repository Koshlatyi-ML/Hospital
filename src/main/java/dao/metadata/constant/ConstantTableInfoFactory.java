package dao.metadata.constant;

import dao.metadata.*;

public class ConstantTableInfoFactory extends TableInfoFactory {
    private DepartmentConstantTableInfo departmentTableInfo;
    private StuffConstantTableInfo stuffTableInfo;
    private DoctorConstantTableInfo doctorTableInfo;
    private MedicConstantTableInfo medicTableInfo;
    private PatientConstantTableInfo patientTableInfo;
    private TherapyConstantTableInfo therapyTableInfo;

    private ConstantTableInfoFactory() {
        departmentTableInfo = new DepartmentConstantTableInfo();
        doctorTableInfo = new DoctorConstantTableInfo();
        medicTableInfo = new MedicConstantTableInfo();
        stuffTableInfo = new StuffConstantTableInfo();
        patientTableInfo = new PatientConstantTableInfo();
        therapyTableInfo = new TherapyConstantTableInfo();
    }

    @Override
    public DepartmentTableInfo getDepartmentTableInfo() {
        return departmentTableInfo;
    }

    @Override
    public StuffTableInfo getStuffTableInfo() {
        return stuffTableInfo;
    }

    @Override
    public DoctorTableInfo getDoctorTableInfo() {
        return doctorTableInfo;
    }

    @Override
    public MedicTableInfo getMedicTableInfo() {
        return medicTableInfo;
    }

    @Override
    public PatientTableInfo getPatientTableInfo() {
        return patientTableInfo;
    }

    @Override
    public TherapyTableInfo getTherapyTableInfo() {
        return therapyTableInfo;
    }
}
