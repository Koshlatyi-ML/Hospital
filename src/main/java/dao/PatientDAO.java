package dao;

import domain.PatientDTO;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends CrudDAO<PatientDTO> {
    List<PatientDTO> findByFullName(String fullName, int offset, int limit);
    List<PatientDTO> findByDepartmentId(long id, int offset, int limit);
    List<PatientDTO> findByDoctorId(long id, int offset, int limit);
    List<PatientDTO> findByState(PatientDTO.State state, int offset, int limit);
    List<PatientDTO> findByDoctorIdAndState(long doctorId, PatientDTO.State state, int offset, int limit);
    Optional<PatientDTO> findByLoginAndPassword(String login, String password);
    Optional<PatientDTO> findByCredentialsId(long id);
}
