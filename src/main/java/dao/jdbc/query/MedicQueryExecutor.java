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
import domain.Doctor;
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

    private String getStuffInnerJoin() {
        return String.format(" %s INNER JOIN %s ON %s = %s ",
                tableInfo.getStuffTableName(),
                tableInfo.getTableName(),
                tableInfo.getIdColumn(),
                tableInfo.getStuffIdColumn());
    }

    @Override
    String getFindAllQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() + ";",
                Queries.formatColumnNames(tableInfo.getColumns()));
    }

    @Override
    String getFindByIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s = ?;",
                Queries.formatColumnNames(tableInfo.getColumns()),
                tableInfo.getIdColumn());
    }


    public String getFindByFullNameQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s LIKE %%?%% OR %s LIKE %%?%%;",
                Queries.formatColumnNames(tableInfo.getColumns()),
                tableInfo.getNameColumn(),
                tableInfo.getSurnameColumn());
    }

    @Override
    String getFindByDepartmentIdQuery() {
        return String.format("SELECT FROM %s WHERE %s = ?;",
                Queries.formatColumnNames(tableInfo.getColumns()),
                tableInfo.getDepartmentIdColumn());
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
    public void queryInsert(Connection connection, Medic entity) throws SQLException {
        super.queryInsertStuff(connection, entity);
        super.queryInsert(connection, entity);
    }

    @Override
    public void queryUpdate(Connection connection, Medic entity) throws SQLException {
        super.queryUpdateStuff(connection, entity);
        super.queryUpdate(connection, entity);
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        super.queryDeleteStuff(connection, id);
        super.queryDelete(connection, id);
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
