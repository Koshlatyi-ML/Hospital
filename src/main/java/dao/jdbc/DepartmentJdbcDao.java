package dao.jdbc;

import dao.DaoFactory;
import dao.DaoManager;
import dao.DepartmentDao;
import dao.connection.jdbc.ConnectionManager;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class DepartmentJdbcDao extends CrudJdbcDao<Department> implements DepartmentDao {
    private DepartmentQueryExecutor queryExecutor;
    private JdbcDaoFactory jdbcDaoFactory;

    DepartmentJdbcDao(QueryExecutorFactory queryExecutorFactory,
                      JdbcDaoFactory jdbcDaoFactory,
                      ConnectionManager connectionManager) {

        queryExecutor = queryExecutorFactory.getDepartmentQueryExecutor();
        this.jdbcDaoFactory = jdbcDaoFactory;
        this.connectionManager = connectionManager;
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

    public static void main(String[] args) throws SQLException {
        DaoManager daoManager = DaoManager.getInstance();
        DaoFactory daoFactory = daoManager.getDaoFactory();

        Connection connection = ConnectionManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        connection.prepareStatement("INSERT INTO stuff (name, surname, department_id)" +
                " VALUES ('A', 'B', NULL)").execute();
        connection.prepareStatement("INSERT INTO stuff (name, surname, department_id)" +
                " VALUES ('C', 'D', NULL)").execute();
        connection.commit();


//        DepartmentDao departmentDao = daoFactory.getDepartmentDao();
//        Department newDep1 = new Department.Builder()
//                .setName("ccc")
//                .build();
//        Department newDep2 = new Department.Builder()
//                .setName("ddd")
//                .build();
//
//        daoManager.beginTransaction();
//        departmentDao.insert(newDep1);
//        departmentDao.insert(newDep2);
//        daoManager.finishTransaction();
    }
}
