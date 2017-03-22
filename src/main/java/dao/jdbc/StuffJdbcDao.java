package dao.jdbc;

import dao.StuffDao;
import dao.jdbc.query.StuffQueryPreparer;
import domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class StuffJdbcDao<E extends Person, T extends StuffQueryPreparer>
        extends PersonJdbcDao<E, T> implements StuffDao<E> {

    @Override
    public List<E> findByDepartmentId(long id) {
        Connection connection = getConnection();

        try(PreparedStatement statement
                    = queryPreparer.findByDepartmentId(connection)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntityList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
