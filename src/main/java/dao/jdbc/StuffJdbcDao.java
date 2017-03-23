package dao.jdbc;

import dao.StuffDao;
import dao.jdbc.query.PersonQueryExecutor;
import dao.jdbc.query.StuffQueryExecutor;
import domain.Doctor;
import domain.Medic;
import domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class StuffJdbcDao<E extends Person> extends PersonJdbcDao<E>
        implements StuffDao<E> {

    @Override
    public void create(E entity) {
        try (Connection connection = getConnection()) {
            try {
                connection.setAutoCommit(false);
                getQueryExecutor().queryInsert(connection, entity);
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            //connection.rollback() inside close //todo
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(E entity) {
        try (Connection connection = getConnection()) {
            try {
                connection.setAutoCommit(false);
                getQueryExecutor().queryUpdate(connection, entity);
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = getConnection()) {
            try {
                connection.setAutoCommit(false);
                getQueryExecutor().queryDelete(connection, id);
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
