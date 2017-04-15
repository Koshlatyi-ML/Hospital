package dao;

import domain.MedicDTO;

import java.util.List;
import java.util.Optional;

public interface MedicDAO extends CrudDAO<MedicDTO> {
    List<MedicDTO> findByFullName(String fullName);
    List<MedicDTO> findByDepartmentId(long id);
    Optional<MedicDTO> findByLoginAndPassword(String login, String password);
    Optional<MedicDTO> findByCredentialsId(long id);
}
