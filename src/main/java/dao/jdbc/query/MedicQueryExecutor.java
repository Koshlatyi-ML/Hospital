package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.EntityRetrieverFactory;
import dao.jdbc.query.supply.StuffValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.MedicTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Medic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MedicQueryExecutor extends StuffQueryExecutor<Medic> {
    private StuffTableInfo stuffTableInfo;
    private MedicTableInfo medicTableInfo;
    private StuffValueSupplier<Medic> valueSupplier;
    private EntityRetriever<Medic> entityRetriever;

    MedicQueryExecutor(TableInfoFactory tableInfoFactory,
                              ValueSupplierFactory valueSupplierFactory,
                              EntityRetrieverFactory entityRetrieverFactory) {

        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
        medicTableInfo = tableInfoFactory.getMedicTableInfo();
        valueSupplier = valueSupplierFactory.getMedicValueSupplier();
        entityRetriever = entityRetrieverFactory.getMedicEntityRetriever();
    }

    private String getStuffInnerJoin() {
        return String.format(" %s INNER JOIN %s ON %s = %s ",
                stuffTableInfo.getTableName(),
                medicTableInfo.getTableName(),
                stuffTableInfo.getIdColumn(),
                medicTableInfo.getIdColumn());
    }

    private List<String> getJoinedColumns() {
        List<String> columns = stuffTableInfo.getColumns();
        columns.addAll(medicTableInfo.getColumns());
        return columns;
    }

    @Override
    String getFindAllQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() + ";",
                Queries.formatColumnNames(getJoinedColumns()));
    }

    @Override
    String getFindByIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s = ?;",
                Queries.formatColumnNames(getJoinedColumns()),
                stuffTableInfo.getIdColumn());
    }


    public String getFindByFullNameQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s LIKE %%?%% OR %s LIKE %%?%%;",
                Queries.formatColumnNames(getJoinedColumns()),
                stuffTableInfo.getNameColumn(),
                stuffTableInfo.getSurnameColumn());
    }

    @Override
    String getFindByDepartmentIdQuery() {
        return String.format("SELECT FROM %s WHERE %s = ?;",
                Queries.formatColumnNames(getJoinedColumns()),
                stuffTableInfo.getDepartmentIdColumn());
    }

    String getInsertQuery() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                medicTableInfo.getTableName(),
                Queries.formatColumnNames(medicTableInfo.getColumns()),
                Queries.formatPlaceholders(medicTableInfo.getColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s = ?;",
                medicTableInfo.getTableName(),
                Queries.formatColumnPlaceholders(medicTableInfo.getColumns()),
                medicTableInfo.getIdColumn());
    }

    String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE %s = ?;",
                medicTableInfo.getTableName(),
                medicTableInfo.getIdColumn());
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
    public void queryDelete(Connection connection, Medic entity) throws SQLException {
        queryDelete(connection, entity.getId());
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        super.queryDeleteStuff(connection, id);
        super.queryDelete(connection, id);
    }

    @Override
    protected StuffTableInfo getTableInfo() {
        return stuffTableInfo;
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
