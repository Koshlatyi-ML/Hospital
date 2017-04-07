package dao.jdbc;

import dao.StuffDAO;
import dao.jdbc.query.StuffQueryExecutor;
import domain.dto.AbstractStuffDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class StuffJdbcDAO<E extends AbstractStuffDTO> extends PersonJdbcDAO<E>
        implements StuffDAO<E> {

    @Override
    public void create(E dto) {
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
    public void update(E dto) {
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
    public void delete(E dto) {
        connectionManager.beginTransaction();
        try (Connection connection = connectionManager.getConnection()) {
            getQueryExecutor().queryDelete(connection, dto);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
        connectionManager.finishTransaction();
    }

    @Override
    public List<E> findByDepartmentId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor().queryFindByDepartmentId(connection, id);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected abstract StuffQueryExecutor<E> getQueryExecutor();
}
