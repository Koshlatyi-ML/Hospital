package dao.jdbc;

import dao.CrudDao;
import dao.connection.ConnectionFactory;
import dao.jdbc.exception.IllegalConnectionPolicyException;
import dao.jdbc.query.QueryPreparer;
import dao.jdbc.retrieve.EntityRetriever;
import dao.jdbc.supply.ValueSupplier;
import domain.IdHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class CrudJdbcDao<E extends IdHolder,T extends QueryPreparer>
        implements CrudDao<E> {

    protected T queryPreparer;
    protected EntityRetriever<E> entityRetriever;
    protected ValueSupplier<E> valueSupplier;
    protected ConnectionPolicy connectionPolicy = ConnectionPolicy.METHOD_SCOPED;


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
        Connection connection = connectionPolicy.getConnection();
        try (PreparedStatement statement = queryPreparer.prepareFindById(connection)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntity(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<E> findAll() {
        Connection connection = connectionPolicy.getConnection();
        try (PreparedStatement statement = queryPreparer.prepareFindAll(connection)) {
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntityList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(E entity) {
        Connection connection = connectionPolicy.getConnection();
        try (PreparedStatement statement = queryPreparer.prepareInsert(connection)) {
            valueSupplier.supplyValues(statement, entity);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(E entity) {
        Connection connection = connectionPolicy.getConnection();
        try (PreparedStatement statement = queryPreparer.prepareUpdate(connection)) {
            valueSupplier.supplyValues(statement, entity);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        Connection connection = connectionPolicy.getConnection();
        try (PreparedStatement statement = queryPreparer.prepareDelete(connection)) {
            statement.setLong(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void setThreadLocalConnection(Connection connection) {
        connectionPolicy = ConnectionPolicy.INSTANCE_SCOPED;
        connectionPolicy.setThreadLocalConnection(connection);
    }

    void releaseThreadLocalConnection() {
        connectionPolicy.setThreadLocalConnection(null);
        connectionPolicy = ConnectionPolicy.METHOD_SCOPED;
    }
}
