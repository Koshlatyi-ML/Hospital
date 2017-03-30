package dao.jdbc;

import dao.*;
import dao.connection.jdbc.ConnectionManager;
import dao.jdbc.query.QueryExecutorFactory;

public class JdbcDaoFactory extends DaoFactory {
    private DepartmentJdbcDao departmentDao;
    private DoctorJdbcDao doctorDao;
    private MedicJdbcDao medicDao;
    private PatientJdbcDao patientDao;
    private TherapyJdbcDao therapyDao;
    private ConnectionManager connectionManager;


    private JdbcDaoFactory() {
        QueryExecutorFactory queryExecutorFactory = QueryExecutorFactory.getInstance();
        connectionManager = ConnectionManager.getInstance();
        departmentDao = new DepartmentJdbcDao(queryExecutorFactory, this, connectionManager);
        doctorDao = new DoctorJdbcDao(queryExecutorFactory, this, connectionManager);
        medicDao = new MedicJdbcDao(queryExecutorFactory, this, connectionManager);
        patientDao = new PatientJdbcDao(queryExecutorFactory, connectionManager);
        therapyDao = new TherapyJdbcDao(queryExecutorFactory, connectionManager);
    }

    public void beginTransaction() {
        connectionManager.beginTransaction();
    }

    public void finishTransaction() {
        connectionManager.finishTransaction();
    }

    @Override
    public DepartmentJdbcDao getDepartmentDao() {
        return departmentDao;
    }

    @Override
    public DoctorJdbcDao getDoctorDao() {
        return doctorDao;
    }

    @Override
    public MedicJdbcDao getMedicDao() {
        return medicDao;
    }

    @Override
    public PatientJdbcDao getPatientDao() {
        return patientDao;
    }

    @Override
    public TherapyJdbcDao getTherapyDao() {
        return therapyDao;
    }
}
