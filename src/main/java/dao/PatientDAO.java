package dao;

import domain.Patient;
import domain.dto.PatientDTO;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends CrudDAO<PatientDTO> {
    List<PatientDTO> findByFullName(String fullName);
    List<PatientDTO> findByDepartmentId(long id);
    List<PatientDTO> findByDoctorId(long id);
    List<PatientDTO> findByState(Patient.State state);
    Optional<PatientDTO> findByCredentialsId(long id);
}
