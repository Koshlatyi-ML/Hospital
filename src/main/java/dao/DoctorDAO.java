package dao;

import domain.dto.DoctorDTO;

import java.util.Optional;

public interface DoctorDAO extends StuffDAO<DoctorDTO>, ApplicationUserDAO<DoctorDTO> {
    Optional<DoctorDTO> findByPatientId(long id);
    Optional<DoctorDTO> findByCredentialsId(long id);
}
