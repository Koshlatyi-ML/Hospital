package dao;

import domain.Department;
import java.util.Optional;

public interface DepartmentDao extends Dao<Department> {
    Optional<Department> findByName();
}
