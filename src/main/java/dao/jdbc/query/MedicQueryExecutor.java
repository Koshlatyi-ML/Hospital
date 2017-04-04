package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.ColumnNameStyle;
import dao.metadata.MedicTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.dto.MedicDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MedicQueryExecutor extends StuffQueryExecutor<MedicDTO> {
    private StuffTableInfo stuffTableInfo;
    private MedicTableInfo medicTableInfo;
    private List<String> selectingColumns;
    private StuffDtoValueSupplier<MedicDTO> valueSupplier;
    private DtoRetriever<MedicDTO> dtoRetriever;

    MedicQueryExecutor(TableInfoFactory tableInfoFactory,
                       ValueSupplierFactory valueSupplierFactory,
                       DtoRetrieverFactory dtoRetrieverFactory) {

        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
        medicTableInfo = tableInfoFactory.getMedicTableInfo();
        valueSupplier = valueSupplierFactory.getMedicDtoValueSupplier();
        dtoRetriever = dtoRetrieverFactory.getMedicDtoRetriever();

        selectingColumns = Arrays.asList(stuffTableInfo.getIdColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getNameColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getSurnameColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getDepartmentIdColumn(ColumnNameStyle.FULL));
    }

    private String getStuffInnerJoin() {
        return String.format(" %s INNER JOIN %s ON %s = %s ",
                stuffTableInfo.getTableName(),
                medicTableInfo.getTableName(),
                stuffTableInfo.getIdColumn(ColumnNameStyle.FULL),
                medicTableInfo.getIdColumn(ColumnNameStyle.FULL));
    }

    @Override
    String getFindAllQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() + ";",
                Queries.formatAliasedColumns(selectingColumns));
    }

    @Override
    String getFindByIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s = ?;",
                Queries.formatAliasedColumns(selectingColumns),
                stuffTableInfo.getIdColumn(ColumnNameStyle.FULL));
    }


    public String getFindByFullNameQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s LIKE %%?%% OR %s LIKE %%?%%;",
                Queries.formatAliasedColumns(selectingColumns),
                stuffTableInfo.getNameColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getSurnameColumn(ColumnNameStyle.FULL));
    }

    @Override
    String getFindByDepartmentIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s = ?;",
                Queries.formatAliasedColumns(selectingColumns),
                stuffTableInfo.getDepartmentIdColumn(ColumnNameStyle.FULL));
    }

    String getInsertQuery() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                medicTableInfo.getTableName(),
                Queries.formatColumnNames(medicTableInfo.getNonGeneratingColumns()),
                Queries.formatPlaceholders(medicTableInfo.getNonGeneratingColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s = ?;",
                medicTableInfo.getTableName(),
                Queries.formatColumnPlaceholders(medicTableInfo.getNonGeneratingColumns()),
                medicTableInfo.getIdColumn(ColumnNameStyle.SHORT));
    }

    String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE %s = ?;",
                medicTableInfo.getTableName(),
                medicTableInfo.getIdColumn(ColumnNameStyle.SHORT));
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
        super.queryDelete(connection, id);
        super.queryDeleteStuff(connection, id);
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

    @Override
    protected List<String> getSelectingColumns() {
        return selectingColumns;
    }
}
