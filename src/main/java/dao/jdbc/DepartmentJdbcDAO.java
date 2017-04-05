package dao.jdbc;

import dao.DaoFactory;
import dao.DaoManager;
import dao.DepartmentDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.dto.DepartmentDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class DepartmentJdbcDAO extends CrudJdbcDAO<DepartmentDTO> implements DepartmentDAO {
    private DepartmentQueryExecutor queryExecutor;
    private JdbcDaoFactory jdbcDaoFactory;

    DepartmentJdbcDAO(QueryExecutorFactory queryExecutorFactory,
                      JdbcDaoFactory jdbcDaoFactory,
                      ConnectionManager connectionManager) {

        queryExecutor = queryExecutorFactory.getDepartmentQueryExecutor();
        this.jdbcDaoFactory = jdbcDaoFactory;
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<DepartmentDTO> findByName(String name) {
        Optional<DepartmentDTO> departmentOptional;
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                departmentOptional = queryExecutor.queryFindByName(connection, name);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                departmentOptional = queryExecutor.queryFindByName(localConnection, name);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return departmentOptional;
    }

    @Override
    protected QueryExecutor<DepartmentDTO> getQueryExecutor() {
        return queryExecutor;
    }

    public static void main(String[] args) throws SQLException {
        DaoManager daoManager = DaoManager.getInstance();
        DaoFactory daoFactory = daoManager.getDaoFactory();

//        Connection connection = ConnectionManager.getInstance().getConnection();
//        connection.setAutoCommit(false);
//        connection.prepareStatement("INSERT INTO stuff (name, surname, department_id)" +
//                " VALUES ('A', 'B', NULL)").execute();
//        connection.prepareStatement("INSERT INTO stuff (name, surname, department_id)" +
//                " VALUES ('C', 'D', NULL)").execute();
//        connection.commit();

        DepartmentDAO departmentDao = daoFactory.getDepartmentDao();

        DepartmentDTO newDTO = new DepartmentDTO.Builder()
                .setName("Дядько")
                .build();

        DepartmentDTO dto = new DepartmentDTO.Builder()
                .setId(91)
                .setName("фдфдфдфдф")
                .build();

//        departmentDao.findByName("Дядько").ifPresent(System.out::println);
//        departmentDao.findByName("Хыдыш").ifPresent(System.out::println);

        System.out.println();
        System.out.println(departmentDao.find(93).toString());
        System.out.println();
        departmentDao.findAll().forEach(System.out::println);

        //        Department newDep2 = new Department.Builder()
//                .setName("ddd")
//                .build();
//
//        daoManager.beginTransaction();
//        departmentDao.create(newDep1);
//        departmentDao.create(newDep2);
//        daoManager.finishTransaction();
    }
}