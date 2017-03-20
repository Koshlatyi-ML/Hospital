package dao.jdbc;

import dao.PatientDao;
import dao.metadata.PatientTableInfo;
import dao.metadata.annotation.PatientAnnotTableInfo;
import dao.metadata.annotation.mapping.Entity;
import domain.Patient;

import java.util.List;

@Entity(Patient.class)
public class PatientJdbcDao extends PersonJdbcDao<Patient, PatientTableInfo>
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
