package dao.jdbc;

import dao.DepartmentDao;
import dao.jdbc.metadata.annotation.Entity;
import domain.model.Department;

import java.util.Optional;

@Entity(Department.class)
public class DepartmentJdbcDao extends CrudJdbcDao<Department>
        implements DepartmentDao {

    @Override
    public Optional<Department> findByName() {
        return null;
    }
}
