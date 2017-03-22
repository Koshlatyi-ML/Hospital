package dao.jdbc;

import dao.PersonDao;
import dao.jdbc.query.PersonQueryPreparer;
import domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class PersonJdbcDao<E extends Person, T extends PersonQueryPreparer>
        extends CrudJdbcDao<E, T> implements PersonDao<E> {

    @Override
    public List<E> findByFullName(String name, String surname) {
        Connection connection = connectionPolicy.getConnection();

        try(PreparedStatement statement = queryPreparer.prepareFindByFullName(connection)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntityList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
