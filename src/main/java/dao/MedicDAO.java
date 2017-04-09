package dao;

import domain.dto.MedicDTO;

import java.util.List;
import java.util.Optional;

public interface MedicDAO {
    List<MedicDTO> findByFullName(String fullName);
    List<MedicDTO> findByDepartmentId(long id);
    Optional<MedicDTO> findByCredentialsId(long id);
}
