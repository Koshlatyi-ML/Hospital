package dao.jdbc;

import dao.PersonDao;
import dao.jdbc.query.PersonQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class PersonJdbcDao<E extends Person> extends CrudJdbcDao<E>
        implements PersonDao<E> {

    @Override
    public List<E> findByFullName(String name, String surname) {
        try (Connection connection = getConnection()) {
            return getQueryExecutor()
                    .prepareFindByFullName(connection, name, surname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected abstract PersonQueryExecutor<E> getQueryExecutor();

}
