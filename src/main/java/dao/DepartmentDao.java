package dao;

import domain.model.Department;
import java.util.Optional;

public interface DepartmentDao extends CrudDao<Department> {
    Optional<Department> findByName();
}
