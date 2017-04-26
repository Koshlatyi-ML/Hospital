package dao.jdbc;

import dao.*;
import dao.connection.TestConnectionProvider;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;

public class TestJdbcDaoManager extends DaoManager {

    private QueryExecutorFactory queryExecutorFactory = QueryExecutorFactory.getInstance();
    private ConnectionManager connectionManager =
            new TestJdbcConnectionManager(TestConnectionProvider.getInstance());

    @Override
    public void beginTransaction() {
    }

    @Override
    public void beginTransaction(int isolationLevel) {
    }

    @Override
    public void finishTransaction() {
    }

    @Override
    public DepartmentDAO getDepartmentDao() {
        return new DepartmentJdbcDAO(queryExecutorFactory.getDepartmentQueryExecutor(), connectionManager);
    }

    @Override
    public DoctorDAO getDoctorDao() {
        return new DoctorJdbcDAO(queryExecutorFactory.getDoctorQueryExecutor(), connectionManager);
    }

    @Override
    public MedicDAO getMedicDao() {
        return new MedicJdbcDAO(queryExecutorFactory.getMedicQueryExecutor(), connectionManager);
    }

    @Override
    public PatientDAO getPatientDao() {
        return new PatientJdbcDAO(queryExecutorFactory.getPatientQueryExecutor(), connectionManager);
    }

    @Override
    public TherapyDAO getTherapyDao() {
        return new TherapyJdbcDAO(queryExecutorFactory.getTherapyQueryExecutor(), connectionManager);
    }

    @Override
    public CredentialsDAO getCredentialsDao() {
        return new CredentialsJdbcDAO(queryExecutorFactory.getCredentialsQueryExecutor(), connectionManager);
    }
}
