package dao;

import domain.PatientDTO;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends CrudDAO<PatientDTO> {
    List<PatientDTO> findByFullName(String fullName);
    List<PatientDTO> findByDepartmentId(long id);
    List<PatientDTO> findByDoctorId(long id);
    List<PatientDTO> findByState(PatientDTO.State state);
    List<PatientDTO> findByDoctorIdAndState(long doctorId, PatientDTO.State state);
    Optional<PatientDTO> findByLoginAndPassword(String login, String password);
    Optional<PatientDTO> findByCredentialsId(long id);
}
