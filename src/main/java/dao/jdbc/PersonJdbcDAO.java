package dao.jdbc;

import dao.PersonDAO;
import dao.jdbc.query.PersonQueryExecutor;
import domain.dto.AbstractPersonDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class PersonJdbcDAO<E extends AbstractPersonDTO> extends CrudJdbcDAO<E>
        implements PersonDAO<E> {

    @Override
    public List<E> findByFullName(String fullName) {
        try (Connection connection = connectionManager.getConnection()) {
            return getQueryExecutor()
                    .queryFindByFullName(connection, fullName);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected abstract PersonQueryExecutor<E> getQueryExecutor();
}
