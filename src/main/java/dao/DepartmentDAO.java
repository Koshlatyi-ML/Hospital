package dao;

import domain.dto.DepartmentDTO;

import java.util.Optional;

public interface DepartmentDAO extends CrudDAO<DepartmentDTO> {
    Optional<DepartmentDTO> findByName(String name);
}
