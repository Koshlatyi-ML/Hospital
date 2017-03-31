package dao.jdbc;

import dao.*;
import dao.connection.jdbc.ConnectionManager;
import dao.jdbc.query.QueryExecutorFactory;

public class JdbcDaoFactory extends DaoFactory {
    private DepartmentJdbcDAO departmentDao;
    private DoctorJdbcDAO doctorDao;
    private MedicJdbcDAO medicDao;
    private PatientJdbcDAO patientDao;
    private TherapyJdbcDAO therapyDao;
    private ConnectionManager connectionManager;


    private JdbcDaoFactory() {
        QueryExecutorFactory queryExecutorFactory = QueryExecutorFactory.getInstance();
        connectionManager = ConnectionManager.getInstance();
        departmentDao = new DepartmentJdbcDAO(queryExecutorFactory, this, connectionManager);
        doctorDao = new DoctorJdbcDAO(queryExecutorFactory, this, connectionManager);
        medicDao = new MedicJdbcDAO(queryExecutorFactory, this, connectionManager);
        patientDao = new PatientJdbcDAO(queryExecutorFactory, connectionManager);
        therapyDao = new TherapyJdbcDAO(queryExecutorFactory, connectionManager);
    }

    @Override
    public DepartmentJdbcDAO getDepartmentDao() {
        return departmentDao;
    }

    @Override
    public DoctorJdbcDAO getDoctorDao() {
        return doctorDao;
    }

    @Override
    public MedicJdbcDAO getMedicDao() {
        return medicDao;
    }

    @Override
    public PatientJdbcDAO getPatientDao() {
        return patientDao;
    }

    @Override
    public TherapyJdbcDAO getTherapyDao() {
        return therapyDao;
    }
}
