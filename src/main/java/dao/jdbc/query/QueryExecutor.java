package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.supply.ValueSupplier;
import dao.metadata.PlainTableInfo;
import domain.IdHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class QueryExecutor<E extends IdHolder> {
    private String getFindAllQuery() {
        return String.format("SELECT * FROM %s;",
                getTableInfo().getTableName());
    }

    private String getFindByIdQuery() {
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

    public ResultSet queryInsert(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getInsertQuery())) {
            getValueSupplier().supplyValues(statement, entity);
            statement.execute();
            return statement.getGeneratedKeys();
        }
    }

    public void queryUpdate(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getUpdateQuery())) {
            getValueSupplier().supplyValues(statement, entity);
            statement.setLong(getTableInfo().getColumns().size(), entity.getId());
            statement.execute();
        }
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
