package dao.jdbc;

import dao.PersonDao;
import dao.jdbc.query.PersonQueryExecutor;
import domain.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class PersonJdbcDao<E extends Person> extends CrudJdbcDao<E>
        implements PersonDao<E> {

    @Override
    public List<E> findByFullName(String name, String surname) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return getQueryExecutor()
                        .queryFindByFullName(connection, name, surname);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connectionManager.getConnection()) {
                return getQueryExecutor()
                        .queryFindByFullName(localConnection, name, surname);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected abstract PersonQueryExecutor<E> getQueryExecutor();
}
