package dao.jdbc.query;

import dao.jdbc.query.supply.StuffDtoValueSupplier;
import domain.AbstractStuffDTO;
import domain.MedicDTO;
import util.PropertyLoader;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@QueryResource("dao/query/stuff.properties")
public abstract class StuffQueryExecutor<T extends AbstractStuffDTO> extends QueryExecutor<T> {

    private Properties stuffQueries =
            PropertyLoader.getProperties(StuffQueryExecutor.class
                    .getDeclaredAnnotation(QueryResource.class).value());

    void queryInsertStuff(Connection connection, T dto) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(stuffQueries.getProperty("insert"),
                             Statement.RETURN_GENERATED_KEYS)) {
            getDtoValueSupplier().supplyStuffValues(statement, dto);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                dto.setId(generatedKeys.getLong(1));
            }
        }
    }

    void queryUpdateStuff(Connection connection, T dto) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(stuffQueries.getProperty("update"))) {
            int index = getDtoValueSupplier().supplyStuffValues(statement, dto);
            statement.setLong(++index, dto.getId());
            statement.execute();
        }
    }

    void queryDeleteStuff(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(stuffQueries.getProperty("delete"))) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public List<T> queryFindByFullName(Connection connection, String fullName,
                                       int offset, int limit) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getQueries().getProperty("findByName"))) {
            statement.setString(1, "%" + fullName + "%");
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDtoList(resultSet);
        }
    }

    public long queryFindByFullNameСount(Connection connection, String fullName)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getQueries().getProperty("findByNameCount"))) {
            statement.setString(1, "%" + fullName + "%");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    public List<T> queryFindByDepartmentId(Connection connection, long id,
                                           int offset, int limit) throws SQLException {
        try (PreparedStatement statement = connection
                .prepareStatement(getQueries().getProperty("findByDepartmentId"))) {

            statement.setLong(1, id);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDtoList(resultSet);
        }
    }

    public long queryFindByDepartmentIdCount(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement = connection
                .prepareStatement(getQueries().getProperty("findByDepartmentIdCount"))) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    public List<T> queryFindWithoutDepartmentId(Connection connection,
                                                  int offset, int limit) throws SQLException {
        try (PreparedStatement statement = connection
                .prepareStatement(getQueries().getProperty("findWithoutDepartmentId"))) {

            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDtoList(resultSet);
        }
    }

    public long queryFindWithoutDepartmentIdCount(Connection connection)
            throws SQLException {

        try (PreparedStatement statement = connection
                .prepareStatement(getQueries().getProperty("findWithoutDepartmentIdCount"))) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    public Optional<T> queryFindByLoginAndPassword(Connection connection, String login,
                                                   String password) throws SQLException {

        try (PreparedStatement statement = connection
                .prepareStatement(getQueries().getProperty("findByLoginAndPassword"))) {
            statement.setString(1, login);
            statement.setString(2, password);
            return getDtoRetriever().retrieveDTO(statement.executeQuery());
        }
    }

    public Optional<T> queryFindByCredentialsId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement = connection
                .prepareStatement(getQueries().getProperty("findByCredentialsId"))) {
            statement.setLong(1, id);
            return getDtoRetriever().retrieveDTO(statement.executeQuery());
        }
    }

    @Override
    protected abstract StuffDtoValueSupplier<T> getDtoValueSupplier();
}
