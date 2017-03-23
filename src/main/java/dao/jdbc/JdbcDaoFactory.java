package dao.jdbc;

import dao.*;

public class JdbcDaoFactory extends DaoFactory {
    private PatientJdbcDao patientDao = new PatientJdbcDao();
    private TherapyJdbcDao therapyDao = new TherapyJdbcDao();
    private MedicJdbcDao medicDao = new MedicJdbcDao(therapyDao);
    private DoctorJdbcDao doctorDao = new DoctorJdbcDao(patientDao, therapyDao);
    private DepartmentJdbcDao departmentDao
            = new DepartmentJdbcDao(doctorDao, medicDao);

    private JdbcDaoFactory(){}

    @Override
    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    @Override
    public DoctorDao getDoctorDao() {
        return doctorDao;
    }

    @Override
    public MedicDao getMedicDao() {
        return medicDao;
    }

    @Override
    public PatientDao getPatient() {
        return patientDao;
    }

    @Override
    public TherapyDao getTherapyDao() {
        return therapyDao;
    }
}
