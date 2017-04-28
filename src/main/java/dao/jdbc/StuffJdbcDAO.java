package dao.jdbc;

import dao.exception.DaoException;
import dao.jdbc.query.StuffQueryExecutor;
import domain.AbstractStuffDTO;
import domain.DoctorDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class StuffJdbcDAO<T extends AbstractStuffDTO> extends CrudJdbcDAO<T> {

    private static final Logger LOG = LogManager.getLogger(StuffJdbcDAO.class);

    @Override
    public void create(T dto) {
        connectionManager.beginTransaction();
        super.create(dto);
        connectionManager.finishTransaction();
    }

    @Override
    public void update(T dto) {
        connectionManager.beginTransaction();
        super.update(dto);
        connectionManager.finishTransaction();
    }

    public List<T> findByFullName(String fullName, int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindByFullName(connection, fullName, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByFullName", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    public List<T> findByDepartmentId(long id, int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindByDepartmentId(connection, id, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByCredentials", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    public long findByDepartmentIdCount(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindByDepartmentIdCount(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByDepartmentIdCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    public List<T> findWithoutDepartmentId(int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindWithoutDepartmentId(connection, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findWithoutDepartmentId", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    public long findWithoutDepartmentIdSize() {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindWithoutDepartmentIdCount(connection);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findWithoutDepartmentIdCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    public Optional<T> findByLoginAndPassword(String login, String password) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindByLoginAndPassword(connection, login, password);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByLoginAndPassword", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    public Optional<T> findByCredentialsId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindByCredentialsId(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByCredentials", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    public long findByFullNameСount(String fullName) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindByFullNameСount(connection, fullName);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByFullNameСount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }


    @Override
    protected abstract StuffQueryExecutor<T> getQueryExecutor();
}
