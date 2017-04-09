package dao;

import domain.dto.DoctorDTO;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO {
    List<DoctorDTO> findByFullName(String fullName);
    List<DoctorDTO> findByDepartmentId(long id);
    Optional<DoctorDTO> findByCredentialsId(long id);
    Optional<DoctorDTO> findByPatientId(long id);
}
