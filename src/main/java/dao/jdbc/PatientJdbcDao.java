package dao.jdbc;

import dao.PatientDao;
import dao.jdbc.metadata.annotation.Entity;
import domain.model.Patient;

import java.util.List;

@Entity(Patient.class)
public class PatientJdbcDao extends PersonJdbcDao<Patient>
        implements PatientDao {

    @Override
    public List<Patient> findByDepartmentId(long id) {
        return null;
    }

    @Override
    public List<Patient> findByDoctorId(long id) {
        return null;
    }

    @Override
    public List<Patient> findByState(Patient.State state) {
        return null;
    }
}
