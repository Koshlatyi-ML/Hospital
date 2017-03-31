package dao;

import domain.Patient;
import domain.dto.PatientDTO;

import java.util.List;

public interface PatientDAO extends CrudDAO<PatientDTO> {
    List<PatientDTO> findByDepartmentId(long id);
    List<PatientDTO> findByDoctorId(long id);
    List<PatientDTO> findByState(Patient.State state); //?
}
