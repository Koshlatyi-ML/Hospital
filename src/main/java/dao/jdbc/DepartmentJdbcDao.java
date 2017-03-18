package dao.jdbc;

import dao.DepartmentDao;
import domain.model.Department;

import java.util.List;
import java.util.Optional;

public class DepartmentJdbcDao extends CrudJdbcDao<Department>
        implements DepartmentDao {

    @Override
    public Optional<Department> findByName() {
        return null;
    }
}
