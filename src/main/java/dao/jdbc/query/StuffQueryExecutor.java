package dao.jdbc.query;

import dao.jdbc.query.supply.StuffValueSupplier;
import dao.metadata.StuffTableInfo;
import domain.Person;

import java.sql.*;
import java.util.List;


public abstract class StuffQueryExecutor<E extends Person> extends PersonQueryExecutor<E> {

    String getInsertStuffQuery() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                getTableInfo().getTableName(),
                Queries.formatColumnNames(getTableInfo().getEntityfulColumns()),
                Queries.formatPlaceholders(getTableInfo().getEntityfulColumns().size()));
    }

    String getInsertStuffWithDepartmentIdQuery() {
        return String.format("INSERT INTO %s %s, %s=? VALUES %s;",
                getTableInfo().getTableName(),
                Queries.formatColumnNames(getTableInfo().getEntityfulColumns()),
                getTableInfo().getDepartmentIdColumn(),
                Queries.formatPlaceholders(getTableInfo().getColumns().size()));
    }

    String getUpdateStuffQuery() {
        return String.format("UPDATE %s SET %s WHERE %s=?;",
                getTableInfo().getTableName(),
                Queries.formatColumnPlaceholders(getTableInfo().getEntityfulColumns()),
                getTableInfo().getIdColumn());
    }

    String getUpdateStuffWithDeapratmentIdQuery() {
        return String.format("UPDATE %s SET %s, %s=? WHERE %s=?;",
                getTableInfo().getTableName(),
                Queries.formatColumnPlaceholders(getTableInfo().getEntityfulColumns()),
                getTableInfo().getDepartmentIdColumn(),
                getTableInfo().getIdColumn());
    }

    String getDeleteStuffQuery() {
        return String.format("DELETE FROM %s WHERE %s = ?;",
                getTableInfo().getTableName(),
                getTableInfo().getIdColumn());
    }

    abstract String getFindByDepartmentIdQuery();

    void queryInsertStuff(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getInsertStuffQuery(),
                             Statement.RETURN_GENERATED_KEYS)) {
            getValueSupplier().supplyStuffValues(statement, entity);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        }
    }

    void queryInsertStuff(Connection connection, E entity, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getInsertStuffWithDepartmentIdQuery(),
                             Statement.RETURN_GENERATED_KEYS)) {
            int index = getValueSupplier().supplyStuffValues(statement, entity);
            statement.setLong(++index, id);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        }
    }

    void queryUpdateStuff(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getUpdateStuffQuery())) {
            int index = getValueSupplier().supplyStuffValues(statement, entity);
            statement.setLong(++index, entity.getId());
            statement.execute();
        }
    }

    void queryUpdateStuff(Connection connection, E entity, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getUpdateStuffWithDeapratmentIdQuery())) {
            int index = getValueSupplier().supplyStuffValues(statement, entity);
            statement.setLong(++index, id);
            statement.setLong(++index, entity.getId());
            statement.execute();
        }
    }

    public void queryDeleteStuff(Connection connection, E entity) throws SQLException {
        queryDelete(connection, entity.getId());
    }

    public void queryDeleteStuff(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getDeleteStuffQuery())) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public List<E> queryFindByDepartmentId(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByDepartmentIdQuery())) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getEntityRetriever().retrieveEntityList(resultSet);
        }
    }

    @Override
    protected abstract StuffTableInfo getTableInfo();

    @Override
    protected abstract StuffValueSupplier<E> getValueSupplier();
}
