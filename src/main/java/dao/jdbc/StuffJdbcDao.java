package dao.jdbc;

import dao.StuffDao;
import dao.jdbc.query.StuffQueryExecutor;
import domain.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class StuffJdbcDao<E extends Person> extends PersonJdbcDao<E>
        implements StuffDao<E> {

    @Override
    public void insert(E entity) {
        if (connectionManager.isTransactional()) {
            plainCreate(entity);
        } else {
            connectionManager.beginTransaction();
            plainCreate(entity);
            connectionManager.finishTransaction();
        }
    }

    private void plainCreate(E entity) {
        Connection connection = connectionManager.getConnection();
        try {
            getQueryExecutor().queryInsert(connection, entity);
        } catch (SQLException e) {
            connectionManager.rollbackAndClose(connection);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(E entity) {
        if (connectionManager.isTransactional()) {
            plainUpdate(entity);
        } else {
            connectionManager.beginTransaction();
            plainUpdate(entity);
            connectionManager.finishTransaction();
        }
    }

    private void plainUpdate(E entity) {
        Connection connection = connectionManager.getConnection();
        try {
            getQueryExecutor().queryUpdate(connection, entity);
        } catch (SQLException e) {
            connectionManager.rollbackAndClose(connection);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(E entity) {
        if (connectionManager.isTransactional()) {
            plainDelete(entity);
        } else {
            connectionManager.beginTransaction();
            plainDelete(entity);
            connectionManager.finishTransaction();
        }
    }

    private void plainDelete(E entity) {
        Connection connection = connectionManager.getConnection();
        try {
            getQueryExecutor().queryDelete(connection, entity);
        } catch (SQLException e) {
            connectionManager.rollbackAndClose(connection);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<E> findByDepartmentId(long id) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return getQueryExecutor().queryFindByDepartmentId(connection, id);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return getQueryExecutor().queryFindByDepartmentId(localConnection, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected abstract StuffQueryExecutor<E> getQueryExecutor();
}
