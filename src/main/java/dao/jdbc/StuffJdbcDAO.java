package dao.jdbc;

import dao.StuffDAO;
import dao.jdbc.query.StuffQueryExecutor;
import domain.Person;
import domain.dto.AbstractStuffDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class StuffJdbcDAO<E extends AbstractStuffDTO> extends PersonJdbcDAO<E>
        implements StuffDAO<E> {

    @Override
    public void create(E dto) {
        if (connectionManager.isTransactional()) {
            plainCreate(dto);
        } else {
            connectionManager.beginTransaction();
            plainCreate(dto);
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
    public void update(E dto) {
//        plainUpdate(entity);

        if (connectionManager.isTransactional()) {
            plainUpdate(dto);
        } else {
            connectionManager.beginTransaction();
            plainUpdate(dto);
            connectionManager.finishTransaction();
        }
    }

    private void plainUpdate(E dto) {
        Connection connection = connectionManager.getConnection();
        try {
            getQueryExecutor().queryUpdate(connection, dto);
        } catch (SQLException e) {
            connectionManager.rollbackAndClose(connection);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(E dto) {
        if (connectionManager.isTransactional()) {
            plainDelete(dto);
        } else {
            connectionManager.beginTransaction();
            plainDelete(dto);
            connectionManager.finishTransaction();
        }
    }

    private void plainDelete(E dto) {
        Connection connection = connectionManager.getConnection();
        try {
            getQueryExecutor().queryDelete(connection, dto);
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
