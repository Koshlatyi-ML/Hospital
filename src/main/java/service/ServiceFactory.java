package service;

import dao.DaoManager;

public class ServiceFactory {

    private DepartmentService departmentService;
    private DoctorService doctorService;
    private MedicService medicService;
    private PatientService patientService;
    private TherapyService therapyService;

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private ServiceFactory() {
        DaoManager daoManager = DaoManager.getInstance();
        departmentService = new DepartmentService(daoManager);
        doctorService = new DoctorService(daoManager);
        medicService = new MedicService(daoManager);
        patientService = new PatientService(daoManager);
        therapyService = new TherapyService(daoManager);
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public DoctorService getDoctorService() {
        return doctorService;
    }

    public MedicService getMedicService() {
        return medicService;
    }

    public PatientService getPatientService() {
        return patientService;
    }

    public TherapyService getTherapyService() {
        return therapyService;
    }
}
