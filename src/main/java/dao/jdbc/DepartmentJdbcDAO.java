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
}
