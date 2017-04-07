package dao.jdbc;

import dao.CrudDAO;
import dao.connection.ConnectionManager;
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
        try (Connection connection1 = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindById(connection1, id);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> findAll() {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindAll(connection);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(T dto) {
        try (Connection connection = connectionManager.getConnection()) {
            getQueryExecutor().queryInsert(connection, dto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T dto) {
        try (Connection connection = connectionManager.getConnection()) {
            getQueryExecutor().queryUpdate(connection, dto);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
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
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    protected abstract QueryExecutor<T> getQueryExecutor();
}
