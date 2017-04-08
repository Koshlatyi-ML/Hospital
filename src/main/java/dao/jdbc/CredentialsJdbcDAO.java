package dao.jdbc;

import dao.CredentialsDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.CredentialsQueryExecutor;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import domain.dto.CredentialsDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class CredentialsJdbcDAO extends CrudJdbcDAO<CredentialsDTO>
        implements CredentialsDAO {

    private CredentialsQueryExecutor queryExecutor;

    CredentialsJdbcDAO(CredentialsQueryExecutor queryExecutor,
                       ConnectionManager connectionManager) {

        this.queryExecutor = queryExecutor;
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<CredentialsDTO> findByLoginAndPassword(String login, String password) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByLoginAndPassword(connection, login, password);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected QueryExecutor<CredentialsDTO> getQueryExecutor() {
        return queryExecutor;
    }
}
