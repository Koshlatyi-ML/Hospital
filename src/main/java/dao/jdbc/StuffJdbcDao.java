package dao.jdbc;

import dao.StuffDao;
import dao.jdbc.query.PersonQueryExecutor;
import dao.jdbc.query.StuffQueryExecutor;
import domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class StuffJdbcDao<E extends Person> extends PersonJdbcDao<E>
        implements StuffDao<E> {

    @Override
    public List<E> findByDepartmentId(long id) {
        try (Connection connection = getConnection()) {
            return getQueryExecutor().queryFindByDepartmentId(connection, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected abstract StuffQueryExecutor<E> getQueryExecutor();
}
