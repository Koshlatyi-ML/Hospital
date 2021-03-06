package dao;

import dao.jdbc.JdbcDaoManager;
import util.load.Implementation;
import util.load.ImplementationLoaderFactory;

@Implementation(JdbcDaoManager.class)
public abstract class DaoManager {

    private static class Holder {
        static final DaoManager INSTANCE = ImplementationLoaderFactory.getInstance()
                .createImplementationLoader().loadInstance(DaoManager.class);

    }
    public static DaoManager getInstance() {
        return Holder.INSTANCE;
    }

    public abstract void beginTransaction();
    public abstract void beginTransaction(int isolationLevel);
    public abstract void finishTransaction();

    public abstract DepartmentDAO getDepartmentDao();
    public abstract DoctorDAO getDoctorDao();
    public abstract MedicDAO getMedicDao();
    public abstract PatientDAO getPatientDao();
    public abstract TherapyDAO getTherapyDao();
    public abstract CredentialsDAO getCredentialsDao();
}
