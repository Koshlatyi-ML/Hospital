package dao;

import domain.DepartmentDTO;

import java.util.Optional;

public interface DepartmentDAO extends CrudDAO<DepartmentDTO> {
    Optional<DepartmentDTO> findByName(String name);
}
