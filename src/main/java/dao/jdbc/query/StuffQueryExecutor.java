package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.supply.StuffValueSupplier;
import dao.metadata.StuffTableInfo;
import domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public abstract class StuffQueryExecutor<E extends Person> extends PersonQueryExecutor<E> {

    private final String INSERT_QUERY =
            String.format("INSERT INTO %s %s VALUES %s;",
                    getTableInfo().getStuffTableName(),
                    Queries.formatColumnNames(getTableInfo().getStuffColumns()),
                    Queries.formatPlaceholders(getTableInfo().getStuffColumns().size()));

    private final String UPDATE_QUERY =
            String.format("UPDATE %s SET %s WHERE %s=?;",
                    getTableInfo().getStuffTableName(),
                    Queries.formatColumnPlaceholders(getTableInfo().getColumns()),
                    getTableInfo().getIdColumn());

    private final String DELETE_QUERY =
            String.format("DELETE FROM %s WHERE %s = ?;",
                    getTableInfo().getStuffTableName(),
                    getTableInfo().getIdColumn());

    private final String FIND_BY_DEPARTMENT_ID_QUERY =
            String.format("SELECT FROM %s WHERE %s = ?;",
                    getTableInfo().getStuffTableName(),
                    getTableInfo().getDepartmentIdColumn());

    @Override
    public ResultSet queryInsert(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            getValueSupplier().supplyStuffValues(statement, entity);
            statement.execute();
            return statement.getGeneratedKeys();
        }
    }

    @Override
    public void queryUpdate(Connection connection, E entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setLong(1, entity.getId());
            statement.execute();
        }
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public List<E> queryFindByDepartmentId(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_DEPARTMENT_ID_QUERY)) {
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
