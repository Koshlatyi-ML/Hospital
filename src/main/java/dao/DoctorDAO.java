package dao;

import domain.DoctorDTO;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO extends CrudDAO<DoctorDTO> {
    List<DoctorDTO> findByFullName(String fullName);
    List<DoctorDTO> findByDepartmentId(long id);
    Optional<DoctorDTO> findByLoginAndPassword(String login, String password);
    Optional<DoctorDTO> findByCredentialsId(long id);
    Optional<DoctorDTO> findByPatientId(long id);
}
