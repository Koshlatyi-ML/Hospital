package dao.jdbc;

import dao.DepartmentDao;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import domain.Department;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentJdbcDao extends CrudJdbcDao<Department> implements DepartmentDao {
    private DepartmentQueryExecutor queryExecutor;
    private DoctorJdbcDao doctorDao;
    private MedicJdbcDao medicDao;

    DepartmentJdbcDao(DoctorJdbcDao doctorDao, MedicJdbcDao medicDao) {
        this.queryExecutor = new DepartmentQueryExecutor();
        this.doctorDao = doctorDao;
        this.medicDao = medicDao;
    }

    @Override
    public Optional<Department> find(long id) {
        Optional<Department> departmentOptional;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            departmentOptional = super.find(id);
            departmentOptional.ifPresent(department -> setStuff(connection, department));
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }

        return departmentOptional;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departmentList;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            departmentList = super.findAll();
            setStuff(connection, departmentList);
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }

        return departmentList;
    }

    @Override
    public Optional<Department> findByName(String name) {
        Optional<Department> departmentOptional;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            departmentOptional = queryExecutor.prepareFindByName(connection, name);
            departmentOptional.ifPresent(department -> setStuff(connection, department));
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }

        return departmentOptional;
    }

    private void setStuff(Connection connection, Department department) {
        long departmentId = department.getId();

        medicDao.setThreadLocalConnection(connection);
        department.setMedics(medicDao.findByDepartmentId(departmentId));
        medicDao.releaseThreadLocalConnection();

        doctorDao.setThreadLocalConnection(connection);
        department.setDoctors(doctorDao.findByDepartmentId(departmentId));
        doctorDao.releaseThreadLocalConnection();
    }

    private void setStuff(Connection connection, List<Department> departmentList) {
        medicDao.setThreadLocalConnection(connection);
        doctorDao.setThreadLocalConnection(connection);

        departmentList.forEach(department -> {
            long departmentId = department.getId();
            department.setMedics(medicDao.findByDepartmentId(departmentId));
            department.setDoctors(doctorDao.findByDepartmentId(departmentId));
        });

        medicDao.releaseThreadLocalConnection();
        doctorDao.releaseThreadLocalConnection();
    }

    @Override
    protected QueryExecutor<Department> getQueryExecutor() {
        return queryExecutor;
    }
}
