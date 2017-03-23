package dao.jdbc.query;

import dao.jdbc.retrieve.EntityRetriever;
import dao.jdbc.supply.ValueSupplier;
import dao.metadata.PlainTableInfo;
import dao.metadata.annotation.IdHolderAnnotTableInfo;
import domain.IdHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class QueryExecutor<E extends IdHolder> {
    private final String FIND_ALL_QUERY
            = String.format("SELECT * FROM %s;", getTableInfo().getTableName());

    private final String FIND_BY_ID_QUERY
            = String.format("SELECT * FROM %s WHERE %s = ?;",
            getTableInfo().getTableName(),
            getTableInfo().getIdColumn());

    private final String INSERT_QUERY
            = String.format("INSERT INTO %s %s VALUES %s;",
            getTableInfo().getTableName(),
            Queries.formatColumnNames(getTableInfo().getColumns()),
            Queries.formatPlaceholders(getTableInfo().getColumns().size()));

    private final String UPDATE_QUERY
            = String.format("UPDATE %s SET %s WHERE %s = ?;",
            getTableInfo().getTableName(),
            Queries.formatColumnNames(getTableInfo().getColumns()),
            Queries.formatColumnPlaceholders(getTableInfo().getColumns()),
            getTableInfo().getIdColumn());

    private final String DELETE_QUERY
                = String.format("DELETE FROM %s WHERE %s = ?;",
                        getTableInfo().getTableName(),
                        getTableInfo().getIdColumn());


    public List<E> queryFindAll(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            return getEntityRetriever().retrieveEntityList(resultSet);
        }
    }

    public Optional<E> queryFindById(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getEntityRetriever().retrieveEntity(resultSet);
        }
    }

    public ResultSet queryInsert(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            getValueSupplier().supplyValues(statement, entity);
            statement.execute();
            return statement.getGeneratedKeys();
        }
    }

    public void queryUpdate(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setLong(1, entity.getId());
            statement.execute();
        }
    }

    public void queryDelete(Connection connection, long id) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
                statement.setLong(1, id);
                statement.execute();
        }
    }

    protected abstract EntityRetriever<E> getEntityRetriever();
    protected abstract ValueSupplier<E> getValueSupplier();
    protected abstract PlainTableInfo getTableInfo();
}
