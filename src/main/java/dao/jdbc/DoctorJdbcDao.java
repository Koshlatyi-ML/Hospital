package dao.jdbc;

import dao.DoctorDao;
import dao.metadata.DoctorTableInfo;
import dao.metadata.annotation.Entity;
import domain.Doctor;

import java.util.List;
import java.util.Optional;

@Entity(Doctor.class)
public class DoctorJdbcDao extends PersonJdbcDao<Doctor, DoctorTableInfo>
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
