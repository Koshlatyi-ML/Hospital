package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.EntityRetrieverFactory;
import dao.jdbc.query.retrieve.MedicEntityRetriever;
import dao.jdbc.query.supply.MedicValueSupplier;
import dao.jdbc.query.supply.StuffValueSupplier;
import dao.jdbc.query.supply.ValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.MedicTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Medic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicQueryExecutor extends StuffQueryExecutor<Medic> {
    private MedicTableInfo tableInfo;
    private StuffValueSupplier<Medic> valueSupplier;
    private EntityRetriever<Medic> entityRetriever;

    MedicQueryExecutor(TableInfoFactory tableInfoFactory,
                              ValueSupplierFactory valueSupplierFactory,
                              EntityRetrieverFactory entityRetrieverFactory) {

        tableInfo = tableInfoFactory.getMedicTableInfo();
        valueSupplier = valueSupplierFactory.getMedicValueSupplier();
        entityRetriever = entityRetrieverFactory.getMedicEntityRetriever();
    }

    String getInsertQuery() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                tableInfo.getTableName(),
                Queries.formatColumnNames(tableInfo.getMedicColumns()),
                Queries.formatPlaceholders(tableInfo.getMedicColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s = ?;",
                tableInfo.getTableName(),
                Queries.formatColumnPlaceholders(tableInfo.getMedicColumns()),
                getTableInfo().getStuffIdColumn());
    }

    String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE %s = ?;",
                tableInfo.getTableName(),
                tableInfo.getStuffIdColumn());
    }

    @Override
    public ResultSet queryInsert(Connection connection, Medic entity) throws SQLException {
        ResultSet generatedKeys = super.queryInsert(connection, entity);
        long stuffId = generatedKeys.getLong(1);
        try (PreparedStatement statement =
                     connection.prepareStatement(getInsertQuery())) {
            statement.setLong(1, stuffId);
            statement.execute();
            return statement.getGeneratedKeys();
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
