package dao.jdbc;

import dao.DepartmentDao;
import dao.metadata.DepartmentTableInfo;
import dao.metadata.annotation.AnnotTableInfoFactory;
import domain.Department;

import java.util.Optional;

public class DepartmentJdbcDao
        extends CrudJdbcDao<Department, DepartmentTableInfo>
        implements DepartmentDao {

    public DepartmentJdbcDao() {
        ownTableInfo = AnnotTableInfoFactory.getInstance().getDepartmentTableInfo();
    }

    @Override
    public Optional<Department> findByName() {
        return null;
    }
}
