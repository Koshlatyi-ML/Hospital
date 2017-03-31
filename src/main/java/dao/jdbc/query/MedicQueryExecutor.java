package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.MedicTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Medic;
import domain.dto.MedicDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MedicQueryExecutor extends StuffQueryExecutor<MedicDTO> {
    private StuffTableInfo stuffTableInfo;
    private MedicTableInfo medicTableInfo;
    private StuffDtoValueSupplier<MedicDTO> valueSupplier;
    private DtoRetriever<MedicDTO> dtoRetriever;

    MedicQueryExecutor(TableInfoFactory tableInfoFactory,
                       ValueSupplierFactory valueSupplierFactory,
                       DtoRetrieverFactory dtoRetrieverFactory) {

        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
        medicTableInfo = tableInfoFactory.getMedicTableInfo();
        valueSupplier = valueSupplierFactory.getMedicDtoValueSupplier();
        dtoRetriever = dtoRetrieverFactory.getMedicDtoRetriever();
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
    public void queryInsert(Connection connection, MedicDTO dto) throws SQLException {
        super.queryInsertStuff(connection, dto);
        super.queryInsert(connection, dto);
    }

    @Override
    public void queryUpdate(Connection connection, MedicDTO dto) throws SQLException {
        super.queryUpdateStuff(connection, dto);
        super.queryUpdate(connection, dto);
    }

    @Override
    public void queryDelete(Connection connection, MedicDTO dto) throws SQLException {
        queryDelete(connection, dto.getId());
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
    protected StuffDtoValueSupplier<MedicDTO> getDtoValueSupplier() {
        return valueSupplier;
    }

    @Override
    protected DtoRetriever<MedicDTO> getDtoRetriever() {
        return dtoRetriever;
    }
}
