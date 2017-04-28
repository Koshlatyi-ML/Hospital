package dao;

import domain.DoctorDTO;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO extends CrudDAO<DoctorDTO> {
    List<DoctorDTO> findByFullName(String fullName, int offset, int limit);
    long findByFullNameСount(String fullName);
    List<DoctorDTO> findWithoutDepartmentId(int offset, int limit);
    long findWithoutDepartmentIdSize();
    List<DoctorDTO> findByDepartmentId(long id, int offset, int limit);
    long findByDepartmentIdCount(long id);
    Optional<DoctorDTO> findByLoginAndPassword(String login, String password);
    Optional<DoctorDTO> findByCredentialsId(long id);
    Optional<DoctorDTO> findByPatientId(long id);
}
