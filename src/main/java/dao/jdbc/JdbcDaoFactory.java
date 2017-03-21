package dao.jdbc;

import dao.*;

public class JdbcDaoFactory extends DaoFactory {
    private DepartmentDao departmentDao = new DepartmentJdbcDao();
    private DoctorDao doctorDao = new DoctorJdbcDao();
    private MedicDao medicDao = new MedicJdbcDao();
    private PatientDao patientDao = new PatientJdbcDao();
    private TherapyDao therapyDao = new TherapyJdbcDao();

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
