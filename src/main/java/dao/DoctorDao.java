package dao;

import domain.Doctor;
import java.util.Optional;

public interface DoctorDao extends StuffDao<Doctor> {
    Optional<Doctor> findByPatientId(long id);
}
