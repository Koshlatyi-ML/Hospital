package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.metadata.PlainTableInfo;
import domain.dto.AbstractDTO;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public abstract class QueryExecutor<E extends AbstractDTO> {
    String getFindAllQuery() {
        return String.format("SELECT * FROM %s;",
                getTableInfo().getTableName());
    }

    String getFindByIdQuery() {
        return String.format("SELECT * FROM %s WHERE %s = ?;",
                getTableInfo().getTableName(),
                getTableInfo().getIdColumn());
    }


    String getInsertQuery() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                getTableInfo().getTableName(),
                Queries.formatColumnNames(getTableInfo().getColumns()),
                Queries.formatPlaceholders(getTableInfo().getColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s = ?;",
                getTableInfo().getTableName(),
                Queries.formatColumnPlaceholders(getTableInfo().getColumns()),
                getTableInfo().getIdColumn());
    }

    String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE %s = ?;",
                getTableInfo().getTableName(),
                getTableInfo().getIdColumn());
    }

    public List<E> queryFindAll(Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getFindAllQuery())) {
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDTOList(resultSet);
        }
    }

    public Optional<E> queryFindById(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByIdQuery())) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDTO(resultSet);
        }
    }

    public void queryInsert(Connection connection, E dto) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getInsertQuery(),
                             Statement.RETURN_GENERATED_KEYS)) {
            getDtoValueSupplier().supplyValues(statement, dto);
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                dto.setId(generatedKeys.getLong(1));
            }
        }
    }

    public void queryUpdate(Connection connection, E dto) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getUpdateQuery())) {
            int index = getDtoValueSupplier().supplyValues(statement, dto);
            statement.setLong(++index, dto.getId());
            statement.execute();
        }
    }

    public void queryDelete(Connection connection, E dto) throws SQLException {
        queryDelete(connection, dto.getId());
    }

    public void queryDelete(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getDeleteQuery())) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    protected abstract DtoRetriever<E> getDtoRetriever();

    protected abstract DtoValueSupplier<E> getDtoValueSupplier();

    protected abstract PlainTableInfo getTableInfo();
}
