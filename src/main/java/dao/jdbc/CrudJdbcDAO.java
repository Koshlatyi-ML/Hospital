package dao.jdbc;

import dao.CrudDAO;
import dao.connection.jdbc.ConnectionManager;
import dao.jdbc.query.QueryExecutor;
import domain.dto.AbstractDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class CrudJdbcDAO<T extends AbstractDTO> implements CrudDAO<T> {
    ConnectionManager connectionManager;

    @Override
    public Optional<T> find(long id) {
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
    public List<T> findAll() {
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
    public void create(T dto) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                getQueryExecutor().queryInsert(connection, dto);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                getQueryExecutor().queryInsert(localConnection, dto);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(T dto) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                getQueryExecutor().queryUpdate(connection, dto);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                getQueryExecutor().queryUpdate(localConnection, dto);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(T dto) {
        delete(dto.getId());
    }

    @Override
    public void delete(long id) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                getQueryExecutor().queryDelete(connection, id);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                getQueryExecutor().queryDelete(localConnection, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract QueryExecutor<T> getQueryExecutor();
}
