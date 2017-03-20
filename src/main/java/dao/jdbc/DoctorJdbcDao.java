package dao.jdbc;

import dao.DoctorDao;
import dao.metadata.DoctorTableInfo;
import dao.metadata.annotation.DoctorAnnotTableInfo;
import dao.metadata.annotation.mapping.Entity;
import domain.Doctor;

import java.util.List;
import java.util.Optional;

public class DoctorJdbcDao implements DoctorDao {
    @Override
    public Optional<Doctor> find(int id) {
        return null;
    }

    @Override
    public List<Doctor> findAll() {
        return null;
    }

    @Override
    public void create(Doctor entity) {

    }

    @Override
    public void update(Doctor entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Doctor> findByDepartmentId(long id) {
        return null;
    }

    @Override
    public Optional<Doctor> findByPatientId(long id) {
        return null;
    }
}
