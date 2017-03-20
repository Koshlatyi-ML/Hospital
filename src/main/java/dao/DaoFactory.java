package dao;

import dao.jdbc.JdbcDaoFactory;
import util.load.Implementation;
import util.load.Instantiator;

@Implementation(JdbcDaoFactory.class)
public abstract class DaoFactory {
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
