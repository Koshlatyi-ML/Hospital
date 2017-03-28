package dao.jdbc.query.supply;

import domain.*;

public class ValueSupplierFactory {
    private DepartmentValueSupplier departmentValueSupplier;
    private DoctorValueSupplier doctorValueSupplier;
    private MedicValueSupplier medicValueSupplier;
    private PatientValueSupplier patientValueSupplier;
    private TherapyValueSupplier therapyValueSupplier;

    private static final ValueSupplierFactory INSTANCE = new ValueSupplierFactory();

    private ValueSupplierFactory() {
        departmentValueSupplier = new DepartmentValueSupplier();
        doctorValueSupplier = new DoctorValueSupplier();
        medicValueSupplier = new MedicValueSupplier();
        patientValueSupplier = new PatientValueSupplier();
        therapyValueSupplier = new TherapyValueSupplier();
    }

    public static ValueSupplierFactory getInstance() {
        return INSTANCE;
    }

    public ValueSupplier<Department> getDepartamentValueSupplier() {
        return departmentValueSupplier;
    }

    public StuffValueSupplier<Doctor> getDoctorValueSupplier() {
        return doctorValueSupplier;
    }

    public StuffValueSupplier<Medic> getMedicValueSupplier() {
        return medicValueSupplier;
    }

    public ValueSupplier<Patient> getPatientValueSupplier() {
        return patientValueSupplier;
    }

    public ValueSupplier<Therapy> getTherapyValueSupplier() {
        return therapyValueSupplier;
    }
}
