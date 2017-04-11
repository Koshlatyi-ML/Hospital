package dao.jdbc;

import dao.CredentialsDAO;
import dao.exception.DaoException;
import dao.jdbc.query.CredentialsQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import domain.CredentialsDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class CredentialsJdbcDAO extends CrudJdbcDAO<CredentialsDTO>
        implements CredentialsDAO {

    private CredentialsQueryExecutor queryExecutor;
    private static final Logger LOG = LogManager.getLogger(CredentialsJdbcDAO.class);

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
            LOG.log(Level.ERROR, "Can't query findByLoginAndPassword", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    protected QueryExecutor<CredentialsDTO> getQueryExecutor() {
        return queryExecutor;
    }
}
