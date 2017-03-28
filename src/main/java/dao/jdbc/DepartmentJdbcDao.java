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
        try (Connection connection = getConnection()) {
            Optional<Department> departmentOptional =
                    queryExecutor.queryFindById(connection, id);
            departmentOptional.ifPresent(this::setStuff);
            return departmentOptional;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Department> findAll() {
        try (Connection connection = getConnection()) {
            List<Department> departmentList =
                    queryExecutor.queryFindAll(connection);
            setStuff(departmentList);
            return departmentList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Department> findByName(String name) {
        try (Connection connection = getConnection()) {
            Optional<Department> departmentOptional =
                    queryExecutor.queryFindByName(connection, name);
            departmentOptional.ifPresent(this::setStuff);
            return departmentOptional;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
