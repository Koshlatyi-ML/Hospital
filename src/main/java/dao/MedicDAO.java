package dao;

import domain.MedicDTO;

import java.util.List;
import java.util.Optional;

public interface MedicDAO extends CrudDAO<MedicDTO> {
    List<MedicDTO> findByFullName(String fullName, int offset, int limit);
    long findByFullNameСount(String fullName);
    List<MedicDTO> findWithoutDepartmentId(int offset, int limit);
    long findWithoutDepartmentIdSize();
    List<MedicDTO> findByDepartmentId(long id, int offset, int limit);
    long findByDepartmentIdCount(long id);
    Optional<MedicDTO> findByLoginAndPassword(String login, String password);
    Optional<MedicDTO> findByCredentialsId(long id);
}
