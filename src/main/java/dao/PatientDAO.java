package dao;

import domain.Patient;
import domain.dto.PatientDTO;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends CrudDAO<PatientDTO>,
        PersonDAO<PatientDTO>, ApplicationUserDAO<PatientDTO> {
    List<PatientDTO> findByDepartmentId(long id);
    List<PatientDTO> findByDoctorId(long id);
    List<PatientDTO> findByState(Patient.State state);
}
