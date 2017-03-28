package dao.jdbc;

import dao.*;
import dao.jdbc.query.QueryExecutorFactory;

public class JdbcDaoFactory extends DaoFactory {
    private DepartmentJdbcDao departmentDao;
    private DoctorJdbcDao doctorDao;
    private MedicJdbcDao medicDao;
    private PatientJdbcDao patientDao;
    private TherapyJdbcDao therapyDao;

    private JdbcDaoFactory() {
        QueryExecutorFactory queryExecutorFactory = QueryExecutorFactory.getInstance();
        departmentDao = new DepartmentJdbcDao(queryExecutorFactory, this);
        doctorDao = new DoctorJdbcDao(queryExecutorFactory, this);
        medicDao = new MedicJdbcDao(queryExecutorFactory, this);
        patientDao = new PatientJdbcDao(queryExecutorFactory);
        therapyDao = new TherapyJdbcDao(queryExecutorFactory);
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
