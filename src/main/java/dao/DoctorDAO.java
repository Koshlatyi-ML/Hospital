package dao;

import domain.dto.DoctorDTO;

import java.util.Optional;

public interface DoctorDAO extends StuffDAO<DoctorDTO> {
    Optional<DoctorDTO> findByPatientId(long id);
}
