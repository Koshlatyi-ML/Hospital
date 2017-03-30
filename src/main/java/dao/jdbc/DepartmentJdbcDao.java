package dao.jdbc;

import dao.DepartmentDao;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.Department;
import sun.dc.pr.PRError;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentJdbcDao extends CrudJdbcDao<Department> implements DepartmentDao {
    private DepartmentQueryExecutor queryExecutor;
    private JdbcDaoFactory jdbcDaoFactory;

    DepartmentJdbcDao(QueryExecutorFactory queryExecutorFactory,
                      JdbcDaoFactory jdbcDaoFactory) {

        queryExecutor = queryExecutorFactory.getDepartmentQueryExecutor();
        this.jdbcDaoFactory = jdbcDaoFactory;
    }

    @Override
    public Optional<Department> find(long id) {
        Optional<Department> departmentOptional = super.find(id);
        departmentOptional.ifPresent(this::setStuff);
        return departmentOptional;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departmentList = super.findAll();
        setStuff(departmentList);
        return departmentList;
    }

    @Override
    public Optional<Department> findByName(String name) {
        Optional<Department> departmentOptional;
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                departmentOptional = queryExecutor.queryFindByName(connection, name);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                departmentOptional = queryExecutor.queryFindByName(localConnection, name);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        departmentOptional.ifPresent(this::setStuff);
        return departmentOptional;
    }

    private void setStuff(List<Department> departments) {
        MedicJdbcDao medicJdbcDao = jdbcDaoFactory.getMedicDao();
        DoctorJdbcDao doctorJdbcDao = jdbcDaoFactory.getDoctorDao();
        departments.forEach(department ->
                setStuff(medicJdbcDao, doctorJdbcDao, department));
    }

    private void setStuff(Department department) {
        setStuff(jdbcDaoFactory.getMedicDao(), jdbcDaoFactory.getDoctorDao(),
                department);
    }

    private void setStuff(MedicJdbcDao medicJdbcDao, DoctorJdbcDao doctorJdbcDao,
                          Department department) {
        long departmentId = department.getId();
        department.setMedics(medicJdbcDao.findByDepartmentId(departmentId));
        department.setDoctors(doctorJdbcDao.findByDepartmentId(departmentId));
    }

    @Override
    protected QueryExecutor<Department> getQueryExecutor() {
        return queryExecutor;
    }
}
