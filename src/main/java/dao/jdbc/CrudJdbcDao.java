package dao.jdbc;

import dao.CrudDao;
import dao.connection.ConnectionFactory;
import dao.jdbc.exception.IllegalConnectionPolicyException;
import dao.jdbc.query.QueryExecutor;
import domain.IdHolder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class CrudJdbcDao<E extends IdHolder> implements CrudDao<E> {
    private ConnectionPolicy connectionPolicy = ConnectionPolicy.METHOD_SCOPED;

    enum ConnectionPolicy {
        INSTANCE_SCOPED {
            @Override
            public Connection getConnection() {
                return threadLocalConnection.get();
            }

            @Override
            public void setThreadLocalConnection(Connection connection) {
                threadLocalConnection.set(connection);
            }
        },
        METHOD_SCOPED {
            @Override
            public Connection getConnection() {
                return factory.getConnection();
            }

            @Override
            public void setThreadLocalConnection(Connection connection) {
                throw new IllegalConnectionPolicyException();
            }
        };
        private static ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
        private static ConnectionFactory factory = ConnectionFactory.getInstance();

        public abstract Connection getConnection();

        public abstract void setThreadLocalConnection(Connection connection);
    }

    @Override
    public Optional<E> find(long id) {
        try (Connection connection = connectionPolicy.getConnection()) {
            return getQueryExecutor().queryFindById(connection, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<E> findAll() {
        try (Connection connection = connectionPolicy.getConnection()) {
            return getQueryExecutor().queryFindAll(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(E entity) {
        try (Connection connection = connectionPolicy.getConnection()) {
            getQueryExecutor().queryInsert(connection,entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(E entity) {
        try (Connection connection = connectionPolicy.getConnection()) {
            getQueryExecutor().queryUpdate(connection, entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = connectionPolicy.getConnection()) {
            getQueryExecutor().queryDelete(connection, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Connection getConnection() {
        return connectionPolicy.getConnection();
    }

    void setThreadLocalConnection(Connection connection) {
        connectionPolicy = ConnectionPolicy.INSTANCE_SCOPED;
        connectionPolicy.setThreadLocalConnection(connection);
    }

    void releaseThreadLocalConnection() {
        connectionPolicy.setThreadLocalConnection(null);
        connectionPolicy = ConnectionPolicy.METHOD_SCOPED;
    }

    protected abstract QueryExecutor<E> getQueryExecutor();
}
