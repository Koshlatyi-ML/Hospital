package dao.jdbc.query.retrieve;

import dao.metadata.TableInfoFactory;
import domain.*;

public class EntityRetrieverFactory {
    private DepartmentEntityRetriever departmentEntityRetriever;
    private DoctorEntityRetriever doctorEntityRetriever;
    private MedicEntityRetriever medicEntityRetriever;
    private PatientEntityRetriever patientEntityRetriever;
    private TherapyEntityRetriever therapyEntityRetriever;

    private static final EntityRetrieverFactory INSTANCE = new EntityRetrieverFactory();

    private EntityRetrieverFactory() {
        TableInfoFactory tableInfoFactory = TableInfoFactory.getInstance();
        departmentEntityRetriever = new DepartmentEntityRetriever(tableInfoFactory);
        doctorEntityRetriever = new DoctorEntityRetriever(tableInfoFactory);
        medicEntityRetriever = new MedicEntityRetriever(tableInfoFactory);
        patientEntityRetriever = new PatientEntityRetriever(tableInfoFactory);
        therapyEntityRetriever = new TherapyEntityRetriever(tableInfoFactory);
    }

    public static EntityRetrieverFactory getInstance() {
        return INSTANCE;
    }

    public EntityRetriever<Department> getDepartmentEntityRetriever() {
        return departmentEntityRetriever;
    }

    public EntityRetriever<Doctor> getDoctorEntityRetriever() {
        return doctorEntityRetriever;
    }

    public EntityRetriever<Medic> getMedicEntityRetriever() {
        return medicEntityRetriever;
    }

    public EntityRetriever<Patient> getPatientEntityRetriever() {
        return patientEntityRetriever;
    }

    public EntityRetriever<Therapy> getTherapyEntityetriever() {
        return therapyEntityRetriever;
    }
}
