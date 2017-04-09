package dao.jdbc;

import dao.jdbc.query.StuffQueryExecutor;
import domain.dto.AbstractStuffDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class StuffJdbcDAO<T extends AbstractStuffDTO> extends CrudJdbcDAO<T> {

    @Override
    public void create(T dto) {
        connectionManager.beginTransaction();
        try (Connection connection = connectionManager.getConnection()) {
            getQueryExecutor().queryInsert(connection, dto);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
        connectionManager.finishTransaction();
    }

    @Override
    public void update(T dto) {
        connectionManager.beginTransaction();
        try (Connection connection = connectionManager.getConnection()) {
            getQueryExecutor().queryUpdate(connection, dto);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
        connectionManager.finishTransaction();
    }

    @Override
    public void delete(T dto) {
        try (Connection connection = connectionManager.getConnection()) {
            getQueryExecutor().queryDelete(connection, dto);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    public List<T> findByFullName(String fullName) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindByFullName(connection, fullName);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    public List<T> findByDepartmentId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindByDepartmentId(connection, id);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected abstract StuffQueryExecutor<T> getQueryExecutor();
}
