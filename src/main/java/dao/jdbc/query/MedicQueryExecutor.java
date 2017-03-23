package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.MedicEntityRetriever;
import dao.jdbc.query.supply.MedicValueSupplier;
import dao.jdbc.query.supply.StuffValueSupplier;
import dao.metadata.MedicTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Medic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicQueryExecutor extends StuffQueryExecutor<Medic> {
    private MedicTableInfo tableInfo;
    private MedicValueSupplier valueSupplier;
    private MedicEntityRetriever entityRetriever;

    private final String INSERT_QUERY =
            String.format("INSERT INTO %s %s VALUES %s;",
                    tableInfo.getTableName(),
                    Queries.formatColumnNames(tableInfo.getMedicColumns()),
                    Queries.formatPlaceholders(tableInfo.getMedicColumns().size()));

    private final String UPDATE_QUERY =
            String.format("UPDATE %s SET %s WHERE %s = ?;",
                    tableInfo.getTableName(),
                    Queries.formatColumnNames(tableInfo.getMedicColumns()),
                    Queries.formatColumnPlaceholders(tableInfo.getMedicColumns()),
                    getTableInfo().getStuffIdColumn());

    private final String DELETE_QUERY =
            String.format("DELETE FROM %s WHERE %s = ?;",
                    tableInfo.getTableName(),
                    tableInfo.getStuffIdColumn());

    public MedicQueryExecutor() {
        tableInfo = TableInfoFactory.getInstance().getMedicTableInfo();
        valueSupplier = new MedicValueSupplier();
        entityRetriever = new MedicEntityRetriever();
    }


    @Override
    public ResultSet queryInsert(Connection connection, Medic entity) throws SQLException {
        ResultSet generatedKeys = super.queryInsert(connection, entity);
        long stuffId = generatedKeys.getLong(1);

        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setLong(1, stuffId);
            statement.execute();
            return statement.getGeneratedKeys();
        }
    }

    @Override
    public void queryUpdate(Connection connection, Medic entity) throws SQLException {
        super.queryUpdate(connection, entity);

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            valueSupplier.supplyStuffValues(statement, entity);
            statement.execute();
        }
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        super.queryDelete(connection, id);

        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    @Override
    protected MedicTableInfo getTableInfo() {
        return tableInfo;
    }

    @Override
    protected StuffValueSupplier<Medic> getValueSupplier() {
        return valueSupplier;
    }

    @Override
    protected EntityRetriever<Medic> getEntityRetriever() {
        return entityRetriever;
    }
}
