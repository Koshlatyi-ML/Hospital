package dao;

import dao.jdbc.JdbcDaoFactory;
import util.load.Implementation;
import util.load.Instantiator;

@Implementation(JdbcDaoFactory.class)
public abstract class DaoFactory {
    protected DepartmentDao departmentDao;
    protected DoctorDao doctorDao;
    protected MedicDao medicDao;
    protected PatientDao patientDao;
    protected TherapyDao therapyDao;

    private static final DaoFactory INSTANCE
            = Instantiator.getInstance().loadInstance(DaoFactory.class);

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public abstract DepartmentDao getDepartmentDao();
    public abstract DoctorDao getDoctorDao();
    public abstract MedicDao getMedicDao();
    public abstract PatientDao getPatient();
    public abstract TherapyDao getTherapyDao();
}
