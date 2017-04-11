package dao.jdbc;

import dao.*;
import dao.connection.ConnectionProvider;
import dao.jdbc.query.QueryExecutorFactory;

import java.sql.Connection;

public class JdbcDaoManager extends DaoManager {

    private DepartmentJdbcDAO departmentDao;
    private DoctorJdbcDAO doctorDao;
    private MedicJdbcDAO medicDao;
    private PatientJdbcDAO patientDao;
    private TherapyJdbcDAO therapyDao;
    private CredentialsDAO credentialsDAO;
    private ConnectionManager connectionManager;

    private JdbcDaoManager() {
        connectionManager = new ConnectionManager(ConnectionProvider.getInstance());
        QueryExecutorFactory queryExecutorFactory = QueryExecutorFactory.getInstance();
        departmentDao = new DepartmentJdbcDAO(queryExecutorFactory.getDepartmentQueryExecutor(),
                connectionManager);
        doctorDao = new DoctorJdbcDAO(queryExecutorFactory.getDoctorQueryExecutor(),
                connectionManager);
        medicDao = new MedicJdbcDAO(queryExecutorFactory.getMedicQueryExecutor(),
                connectionManager);
        patientDao = new PatientJdbcDAO(queryExecutorFactory.getPatientQueryExecutor(),
                connectionManager);
        therapyDao = new TherapyJdbcDAO(queryExecutorFactory.getTherapyQueryExecutor(),
                connectionManager);
        credentialsDAO = new CredentialsJdbcDAO(queryExecutorFactory.getCredentialsQueryExecutor(),
                connectionManager);
    }

    @Override
    public DepartmentDAO getDepartmentDao() {
        return departmentDao;
    }

    @Override
    public DoctorDAO getDoctorDao() {
        return doctorDao;
    }

    @Override
    public MedicDAO getMedicDao() {
        return medicDao;
    }

    @Override
    public PatientDAO getPatientDao() {
        return patientDao;
    }

    @Override
    public TherapyDAO getTherapyDao() {
        return therapyDao;
    }

    @Override
    public CredentialsDAO getCredentialsDao() {
        return credentialsDAO;
    }

    @Override
    public void beginTransaction() {
        beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    @Override
    public void beginTransaction(int isolationLevel) {
        connectionManager.beginTransaction(isolationLevel);
    }

    @Override
    public void finishTransaction() {
        connectionManager.finishTransaction();
    }
}
