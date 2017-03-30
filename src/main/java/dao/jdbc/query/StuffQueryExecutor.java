package dao.jdbc.query;

import dao.jdbc.query.supply.StuffValueSupplier;
import dao.metadata.StuffTableInfo;
import domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public abstract class StuffQueryExecutor<E extends Person> extends PersonQueryExecutor<E> {

    String getInsertStuffQuery() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                getTableInfo().getTableName(),
                Queries.formatColumnNames(getTableInfo().getColumns()),
                Queries.formatPlaceholders(getTableInfo().getColumns().size()));
    }

    String getUpdateStuffQuery() {
        return String.format("UPDATE %s SET %s WHERE %s=?;",
                getTableInfo().getTableName(),
                Queries.formatColumnPlaceholders(getTableInfo().getColumns()),
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
                     connection.prepareStatement(getInsertStuffQuery())) {
            getValueSupplier().supplyStuffValues(statement, entity);
            statement.execute();
            entity.setId(statement.getGeneratedKeys().getLong(1));
        }
    }

    void queryUpdateStuff(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getUpdateStuffQuery())) {
            getValueSupplier().supplyStuffValues(statement, entity);
            statement.setLong(getTableInfo().getColumns().size(), entity.getId());
            statement.execute();
        }
    }

    void queryDeleteStuff(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getDeleteStuffQuery())) {
            statement.setLong(1, entity.getId());
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
