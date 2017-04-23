package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import domain.AbstractDTO;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public abstract class QueryExecutor<T extends AbstractDTO> {

    public long queryCount(Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getQueries().getProperty("count"))) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    public List<T> queryFindAll(Connection connection, int offset, int limit)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getQueries().getProperty("findAll"))) {

            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDtoList(resultSet);
        }
    }

    public Optional<T> queryFindById(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getQueries().getProperty("findById"))) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDTO(resultSet);
        }
    }

    public void queryInsert(Connection connection, T dto) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getQueries().getProperty("insert"),
                             Statement.RETURN_GENERATED_KEYS)) {

            getDtoValueSupplier().supplyValues(statement, dto);
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                dto.setId(generatedKeys.getLong(1));
            }
        }
    }

    public void queryUpdate(Connection connection, T dto) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getQueries().getProperty("update"))) {

            int index = getDtoValueSupplier().supplyValues(statement, dto);
            statement.setLong(++index, dto.getId());
            statement.execute();
        }
    }

    public void queryDelete(Connection connection, T dto) throws SQLException {
        queryDelete(connection, dto.getId());
    }

    public void queryDelete(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getQueries().getProperty("delete"))) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    protected abstract DtoRetriever<T> getDtoRetriever();

    protected abstract DtoValueSupplier<T> getDtoValueSupplier();

    protected abstract Properties getQueries();
}
