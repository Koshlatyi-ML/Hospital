package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.supply.ValueSupplier;
import dao.metadata.PlainTableInfo;
import domain.IdHolder;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public abstract class QueryExecutor<E extends IdHolder> {
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
                Queries.formatColumnNames(getTableInfo().getEntityfulColumns()),
                Queries.formatPlaceholders(getTableInfo().getColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s = ?;",
                getTableInfo().getTableName(),
                Queries.formatColumnPlaceholders(getTableInfo().getEntityfulColumns()),
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
            return getEntityRetriever().retrieveEntityList(resultSet);
        }
    }

    public Optional<E> queryFindById(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByIdQuery())) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getEntityRetriever().retrieveEntity(resultSet);
        }
    }

    public void queryInsert(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getInsertQuery(),
                             Statement.RETURN_GENERATED_KEYS)) {
            getValueSupplier().supplyValues(statement, entity);
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        }
    }

    public void queryUpdate(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getUpdateQuery())) {
            int index = getValueSupplier().supplyValues(statement, entity);
            statement.setLong(++index, entity.getId());
            statement.execute();
        }
    }

    public void queryDelete(Connection connection, E entity) throws SQLException {
        queryDelete(connection, entity.getId());
    }

    public void queryDelete(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getDeleteQuery())) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    protected abstract EntityRetriever<E> getEntityRetriever();

    protected abstract ValueSupplier<E> getValueSupplier();

    protected abstract PlainTableInfo getTableInfo();
}
