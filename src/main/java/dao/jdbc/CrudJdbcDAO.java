package dao.jdbc;

import dao.CrudDAO;
import dao.exception.DaoException;
import dao.jdbc.query.QueryExecutor;
import domain.AbstractDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class CrudJdbcDAO<T extends AbstractDTO> implements CrudDAO<T> {

    ConnectionManager connectionManager;
    private static final Logger LOG = LogManager.getLogger(CrudJdbcDAO.class);

    @Override
    public Optional<T> find(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindById(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findById", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> findAll(int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindAll(connection, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findAll", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public void create(T dto) {
        try (Connection connection = connectionManager.getConnection()) {
            getQueryExecutor().queryInsert(connection, dto);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query insert", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public void update(T dto) {
        try (Connection connection = connectionManager.getConnection()) {
            getQueryExecutor().queryUpdate(connection, dto);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query update", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(T dto) {
        delete(dto.getId());
    }

    @Override
    public void delete(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            getQueryExecutor().queryDelete(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query delete", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long count() {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryCount(connection);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query row count", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    protected abstract QueryExecutor<T> getQueryExecutor();
}
