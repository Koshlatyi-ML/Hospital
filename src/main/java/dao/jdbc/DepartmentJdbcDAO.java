package dao.jdbc;

import dao.DepartmentDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import domain.dto.DepartmentDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class DepartmentJdbcDAO extends CrudJdbcDAO<DepartmentDTO> implements DepartmentDAO {
    private DepartmentQueryExecutor queryExecutor;

    DepartmentJdbcDAO(DepartmentQueryExecutor queryExecutor,
                      ConnectionManager connectionManager) {

        this.queryExecutor = queryExecutor;
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<DepartmentDTO> findByName(String name) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByName(connection, name);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected QueryExecutor<DepartmentDTO> getQueryExecutor() {
        return queryExecutor;
    }

    public static void main(String[] args) throws SQLException {
//        DaoManager daoManager = DaoManager.getInstance();
//        DaoFactory daoFactory = daoManager.getDaoFactory();

//        Connection connection = ConnectionManager.getInstance().getConnection();
//        connection.setAutoCommit(false);
//        connection.prepareStatement("INSERT INTO stuff (name, surname, department_id)" +
//                " VALUES ('A', 'B', NULL)").execute();
//        connection.prepareStatement("INSERT INTO stuff (name, surname, department_id)" +
//                " VALUES ('C', 'D', NULL)").execute();
//        connection.commit();

//        DepartmentDAO departmentDao = daoFactory.getDepartmentDao();

        DepartmentDTO newDTO = new DepartmentDTO.Builder()
                .setName("Дядько")
                .build();

        DepartmentDTO dto = new DepartmentDTO.Builder()
                .setId(91)
                .setName("фдфдфдфдф")
                .build();

//        departmentDao.findByName("Дядько").ifPresent(System.out::println);
//        departmentDao.findByName("Хыдыш").ifPresent(System.out::println);

//        System.out.println();
//        System.out.println(departmentDao.find(93).toString());
//        System.out.println();
//        departmentDao.findAll().forEach(System.out::println);

        //        Department newDep2 = new Department.Builder()
//                .setTitle("ddd")
//                .build();
//
//        daoManager.beginTransaction();
//        departmentDao.create(newDep1);
//        departmentDao.create(newDep2);
//        daoManager.finishTransaction();
    }
}
