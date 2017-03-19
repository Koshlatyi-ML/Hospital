package dao.jdbc;

import dao.DepartmentDao;
import dao.metadata.DepartmentTableInfo;
import domain.Department;

import java.util.Optional;

public class DepartmentJdbcDao extends CrudJdbcDao<Department,
        DepartmentTableInfo> implements DepartmentDao {

    public DepartmentJdbcDao() {
        ownTableInfo
                = DepartmentTableInfo.createDepartmentTableInfo();
    }

    @Override
    public Optional<Department> findByName() {
        return null;
    }
}
