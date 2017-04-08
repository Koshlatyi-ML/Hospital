package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import dao.metadata.ColumnNameStyle;
import dao.metadata.MedicTableInfo;
import dao.metadata.StuffTableInfo;
import domain.dto.MedicDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MedicQueryExecutor extends StuffQueryExecutor<MedicDTO> {
    private StuffTableInfo stuffTableInfo;
    private MedicTableInfo medicTableInfo;
    private List<String> selectingColumns;
    private StuffDtoValueSupplier<MedicDTO> valueSupplier;
    private DtoRetriever<MedicDTO> dtoRetriever;

    MedicQueryExecutor() {
    }

    void setStuffTableInfo(StuffTableInfo stuffTableInfo) {
        this.stuffTableInfo = stuffTableInfo;
        selectingColumns = Arrays.asList(stuffTableInfo.getIdColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getNameColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getSurnameColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getDepartmentIdColumn(ColumnNameStyle.FULL),
                medicTableInfo.getCredentialsIdColumn(ColumnNameStyle.FULL));
    }

    void setMedicTableInfo(MedicTableInfo medicTableInfo) {
        this.medicTableInfo = medicTableInfo;
    }

    void setValueSupplier(StuffDtoValueSupplier<MedicDTO> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    void setDtoRetriever(DtoRetriever<MedicDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    private String getStuffInnerJoin() {
        return String.format(" %s INNER JOIN %s ON %s=%s ",
                stuffTableInfo.getTableName(),
                medicTableInfo.getTableName(),
                stuffTableInfo.getIdColumn(ColumnNameStyle.FULL),
                medicTableInfo.getIdColumn(ColumnNameStyle.FULL));
    }

    @Override
    String getFindAllQuery() {
        return String.format("SELECT %s FROM" + getStuffInnerJoin(),
                Queries.formatAliasedColumns(selectingColumns));
    }

    @Override
    String getFindByIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                stuffTableInfo.getIdColumn(ColumnNameStyle.FULL));
    }


    public String getFindByFullNameQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE LOWER(%s||%s) LIKE LOWER(?)",
                Queries.formatAliasedColumns(selectingColumns),
                stuffTableInfo.getNameColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getSurnameColumn(ColumnNameStyle.FULL));
    }

    @Override
    String getFindByDepartmentIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                stuffTableInfo.getDepartmentIdColumn(ColumnNameStyle.FULL));
    }

    private String getFindByCredentialsIdQuery() {
        return String.format("SELECT %s FROM %s WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                getStuffInnerJoin(),
                medicTableInfo.getCredentialsIdColumn(ColumnNameStyle.FULL));
    }

    String getInsertQuery() {
        return String.format("INSERT INTO %s %s VALUES %s",
                medicTableInfo.getTableName(),
                Queries.formatInsertColumnNames(medicTableInfo.getNonGeneratingColumns()),
                Queries.formatInsertPlaceholders(medicTableInfo.getNonGeneratingColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s=?",
                medicTableInfo.getTableName(),
                Queries.formatUpdateColumnPlaceholders(medicTableInfo.getNonGeneratingColumns()),
                medicTableInfo.getIdColumn(ColumnNameStyle.SHORT));
    }

    String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE %s=?",
                medicTableInfo.getTableName(),
                medicTableInfo.getIdColumn(ColumnNameStyle.SHORT));
    }

    @Override
    public void queryInsert(Connection connection, MedicDTO dto) throws SQLException {
        queryInsertStuff(connection, dto);
        super.queryInsert(connection, dto);
    }

    @Override
    public void queryUpdate(Connection connection, MedicDTO dto) throws SQLException {
        queryUpdateStuff(connection, dto);
        super.queryUpdate(connection, dto);
    }

    @Override
    public void queryDelete(Connection connection, MedicDTO dto) throws SQLException {
        queryDelete(connection, dto.getId());
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        queryDeleteStuff(connection, id);
    }

    public Optional<MedicDTO> queryFindByCredentialsId(Connection connection, long id)
            throws SQLException {

        return AppUserStatementExecutor.queryFindByCredentialsId(
                connection, id, getFindByCredentialsIdQuery(), dtoRetriever);
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
