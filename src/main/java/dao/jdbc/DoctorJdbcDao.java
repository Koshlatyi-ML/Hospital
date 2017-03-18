package dao.jdbc;

import dao.DoctorDao;
import dao.jdbc.metadata.annotation.Entity;
import domain.model.Doctor;

import java.util.List;
import java.util.Optional;

@Entity(Doctor.class)
public class DoctorJdbcDao extends PersonJdbcDao<Doctor>
        implements DoctorDao {
    @Override
    public List<Doctor> findByDepartmentId(long id) {
        return null;
    }

    @Override
    public Optional<Doctor> findByPatientId(long id) {
        return null;
    }
}
