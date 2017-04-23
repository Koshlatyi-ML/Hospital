package dao;

import domain.PatientDTO;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends CrudDAO<PatientDTO> {
    List<PatientDTO> findByFullName(String fullName, int offset, int limit);
    long findByFullNameСount(String fullName);
    List<PatientDTO> findByDepartmentId(long id, int offset, int limit);
    long findByDepartmentIdCount(long id);
    List<PatientDTO> findByDoctorId(long id, int offset, int limit);
    long findByDoctorIdCount(long id);
    List<PatientDTO> findByState(PatientDTO.State state, int offset, int limit);
    long findByStateCount(PatientDTO.State state);
    List<PatientDTO> findByDoctorIdAndState(long doctorId, PatientDTO.State state, int offset, int limit);
    long findByDoctorIdAndStateCount(long doctorId, PatientDTO.State state);
    Optional<PatientDTO> findByLoginAndPassword(String login, String password);
    Optional<PatientDTO> findByCredentialsId(long id);
}
