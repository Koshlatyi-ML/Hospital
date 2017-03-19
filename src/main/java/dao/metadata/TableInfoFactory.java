package dao.metadata;

public class TableInfoFactory {
    private DepartmentTableInfo departmentTableInfo;
    private DoctorTableInfo doctorTableInfo;
    private MedicTableInfo medicTableInfo;
    private PatientTableInfo patientTableInfo;
    private TherapyTableInfo therapyTableInfo;

    private static final TableInfoFactory INSTANCE
            = new TableInfoFactory();

    private TableInfoFactory() {}

    public static TableInfoFactory getInstance() {
        return INSTANCE;
    }

    public DepartmentTableInfo getDepartmentTableInfo() {
        return departmentTableInfo;
    }

    public DoctorTableInfo getDoctorTableInfo() {
        return doctorTableInfo;
    }

    public MedicTableInfo getMedicTableInfo() {
        return medicTableInfo;
    }

    public PatientTableInfo getPatientTableInfo() {
        return patientTableInfo;
    }

    public TherapyTableInfo getTherapyTableInfo() {
        return therapyTableInfo;
    }
}
