package dao.jdbc;

import dao.DepartmentDAO;
import dao.exception.DaoException;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import domain.DepartmentDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class DepartmentJdbcDAO extends CrudJdbcDAO<DepartmentDTO> implements DepartmentDAO {

    private DepartmentQueryExecutor queryExecutor;
    private static final Logger LOG = LogManager.getLogger(DepartmentQueryExecutor.class);

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
            LOG.log(Level.ERROR, "Can't query findByName", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    protected QueryExecutor<DepartmentDTO> getQueryExecutor() {
        return queryExecutor;
    }
}
