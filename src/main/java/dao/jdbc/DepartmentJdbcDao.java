package dao.jdbc;

import dao.DepartmentDao;
import dao.jdbc.query.DepartmentQueryPreparer;
import dao.jdbc.retrieve.DepartmentEntityRetriever;
import dao.jdbc.supply.DepartmentValueSupplier;
import domain.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentJdbcDao extends CrudJdbcDao<Department, DepartmentQueryPreparer>
        implements DepartmentDao {
    private DoctorJdbcDao doctorDao;
    private MedicJdbcDao medicDao;

    DepartmentJdbcDao(DoctorJdbcDao doctorDao, MedicJdbcDao medicDao) {
        this.doctorDao = doctorDao;
        this.medicDao = medicDao;
        entityRetriever = new DepartmentEntityRetriever();
        valueSupplier = new DepartmentValueSupplier();
        queryPreparer = new DepartmentQueryPreparer();
    }

    @Override
    public Optional<Department> find(long id) {
        Connection connection = getConnection();
        setThreadLocalConnection(connection);

        Optional<Department> departmentOptional = super.find(id);
        departmentOptional.ifPresent(department -> setStuff(connection, department));

        releaseThreadLocalConnection();

        return departmentOptional;
    }

    @Override
    public List<Department> findAll() {
        Connection connection = getConnection();
        setThreadLocalConnection(connection);

        List<Department> departmentList = super.findAll();
        setStuff(connection, departmentList);

        releaseThreadLocalConnection();

        return departmentList;
    }

    @Override
    public Optional<Department> findByName(String name) {
        Connection connection = getConnection();
        setThreadLocalConnection(connection);

        Optional<Department> departmentOptional;
        try (PreparedStatement statement = queryPreparer.prepareFindByName(connection)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            departmentOptional = entityRetriever.retrieveEntity(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        departmentOptional.ifPresent(department -> setStuff(connection, department));
        releaseThreadLocalConnection();

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

        departmentList.forEach(department ->  {
            long departmentId = department.getId();
            department.setMedics(medicDao.findByDepartmentId(departmentId));
            department.setDoctors(doctorDao.findByDepartmentId(departmentId));
        });

        medicDao.releaseThreadLocalConnection();
        doctorDao.releaseThreadLocalConnection();
    }
}
