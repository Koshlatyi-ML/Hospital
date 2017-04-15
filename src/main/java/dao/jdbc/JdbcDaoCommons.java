package dao.jdbc;

import dao.exception.DaoException;
import dao.jdbc.query.CredentialsHolderQueryExecutor;
import dao.jdbc.query.DepartmentMemberQueryExecutor;
import dao.jdbc.query.PersonQueryExecutor;
import domain.AbstractPersonDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class JdbcDaoCommons {

    private static final Logger LOG = LogManager.getLogger(JdbcDaoCommons.class);

    private JdbcDaoCommons() {}

    static <T extends AbstractPersonDTO> Optional<T> findByLoginAndPassword(
            ConnectionManager connectionManager,
            CredentialsHolderQueryExecutor<T> queryExecutor,
            String login, String password) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByLoginAndPassword(connection, login, password);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByLoginAndPassword", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    static <T extends AbstractPersonDTO> Optional<T> findByCredentialsId(
            ConnectionManager connectionManager,
            CredentialsHolderQueryExecutor<T> queryExecutor,
            long id) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByCredentialsId(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByCredentials", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    static <T extends AbstractPersonDTO> List<T> findByFullName(
            ConnectionManager connectionManager,
            PersonQueryExecutor<T> queryExecutor,
            String name) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByFullName(connection, name);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByFullName", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    static <T extends AbstractPersonDTO> List<T> findByDepartmentId(
            ConnectionManager connectionManager,
            DepartmentMemberQueryExecutor<T> queryExecutor,
            long id) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByDepartmentId(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByCredentials", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }
}
