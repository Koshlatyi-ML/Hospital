package dao.jdbc;

import dao.CrudDao;
import dao.connection.jdbc.ConnectionManager;
import dao.jdbc.query.QueryExecutor;
import domain.IdHolder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class CrudJdbcDao<E extends IdHolder> implements CrudDao<E> {
    ConnectionManager connectionManager;

    @Override
    public Optional<E> find(long id) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return getQueryExecutor().queryFindById(connection, id);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return getQueryExecutor().queryFindById(localConnection, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<E> findAll() {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return getQueryExecutor().queryFindAll(connection);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return getQueryExecutor().queryFindAll(localConnection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void insert(E entity) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                getQueryExecutor().queryInsert(connection, entity);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                getQueryExecutor().queryInsert(localConnection, entity);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(E entity) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                getQueryExecutor().queryUpdate(connection, entity);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                getQueryExecutor().queryUpdate(localConnection, entity);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(E entity) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                getQueryExecutor().queryDelete(connection, entity);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                getQueryExecutor().queryDelete(localConnection, entity);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract QueryExecutor<E> getQueryExecutor();
}
