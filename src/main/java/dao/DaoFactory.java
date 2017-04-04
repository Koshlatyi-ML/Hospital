package dao;

import dao.jdbc.JdbcDaoFactory;
import util.load.Implementation;
import util.load.ImplementationLoader;

import java.time.Instant;
import java.time.ZonedDateTime;

@Implementation(JdbcDaoFactory.class)
public abstract class DaoFactory {
    private static final DaoFactory INSTANCE =
            ImplementationLoader.getInstance().loadInstance(DaoFactory.class);

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public abstract DepartmentDAO getDepartmentDao();
    public abstract DoctorDAO getDoctorDao();
    public abstract MedicDAO getMedicDao();
    public abstract PatientDAO getPatientDao();
    public abstract TherapyDAO getTherapyDao();
}
