package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.*;
import domain.dto.DoctorDTO;
import domain.dto.PatientDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DoctorQueryExecutor extends StuffQueryExecutor<DoctorDTO> {
    private StuffTableInfo stuffTableInfo;
    private DoctorTableInfo doctorTableInfo;
    private PatientTableInfo patientTableInfo;
    private List<String> selectingColumns;
    private StuffDtoValueSupplier<DoctorDTO> valueSupplier;
    private DtoRetriever<DoctorDTO> dtoRetriever;

    DoctorQueryExecutor() {
    }

    void setStuffTableInfo(StuffTableInfo stuffTableInfo) {
        this.stuffTableInfo = stuffTableInfo;
        selectingColumns = Arrays.asList(stuffTableInfo.getIdColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getNameColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getSurnameColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getDepartmentIdColumn(ColumnNameStyle.FULL),
                doctorTableInfo.getCredentialsIdColumn(ColumnNameStyle.FULL));
    }

    void setDoctorTableInfo(DoctorTableInfo doctorTableInfo) {
        this.doctorTableInfo = doctorTableInfo;
    }

    void setPatientTableInfo(PatientTableInfo patientTableInfo) {
        this.patientTableInfo = patientTableInfo;
    }

    void setValueSupplier(StuffDtoValueSupplier<DoctorDTO> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    void setDtoRetriever(DtoRetriever<DoctorDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    private String getStuffInnerJoin() {
        return String.format(" %s INNER JOIN %s ON %s=%s ",
                stuffTableInfo.getTableName(),
                doctorTableInfo.getTableName(),
                stuffTableInfo.getIdColumn(ColumnNameStyle.FULL),
                doctorTableInfo.getIdColumn(ColumnNameStyle.FULL));
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

    @Override
    String getFindByFullNameQuery() {
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

    private String getFindByPatientIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "INNER JOIN %s ON %s=%s " +
                        "WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                patientTableInfo.getTableName(),
                stuffTableInfo.getIdColumn(ColumnNameStyle.FULL),
                patientTableInfo.getDoctorIdColumn(ColumnNameStyle.FULL),
                patientTableInfo.getIdColumn(ColumnNameStyle.FULL));
    }

    private String getFindByCredentialsIdQuery() {
        return String.format("SELECT %s FROM %s WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                getStuffInnerJoin(),
                doctorTableInfo.getCredentialsIdColumn(ColumnNameStyle.FULL));
    }

    String getInsertQuery() {
        return String.format("INSERT INTO %s %s VALUES %s",
                doctorTableInfo.getTableName(),
                Queries.formatInsertColumnNames(doctorTableInfo.getNonGeneratingColumns()),
                Queries.formatInsertPlaceholders(doctorTableInfo.getNonGeneratingColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s=?",
                doctorTableInfo.getTableName(),
                Queries.formatUpdateColumnPlaceholders(doctorTableInfo.getNonGeneratingColumns()),
                doctorTableInfo.getIdColumn(ColumnNameStyle.SHORT));
    }

    String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE %s=?",
                doctorTableInfo.getTableName(),
                doctorTableInfo.getIdColumn(ColumnNameStyle.SHORT));
    }

    @Override
    public void queryInsert(Connection connection, DoctorDTO dto) throws SQLException {
        queryInsertStuff(connection, dto);
        super.queryInsert(connection, dto);
    }

    @Override
    public void queryUpdate(Connection connection, DoctorDTO dto) throws SQLException {
        queryUpdateStuff(connection, dto);
        super.queryUpdate(connection, dto);
    }

    @Override
    public void queryDelete(Connection connection, DoctorDTO dto) throws SQLException {
        queryDelete(connection, dto.getId());
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        queryDeleteStuff(connection, id);
    }

    public Optional<DoctorDTO> queryFindByPatientId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByPatientIdQuery())) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDTO(resultSet);
        }
    }

    public Optional<DoctorDTO> queryFindByCredentialsId(Connection connection, long id)
            throws SQLException {

        return AppUserStatementExecutor.queryFindByCredentialsId(
                connection, id, getFindByCredentialsIdQuery(), dtoRetriever);
    }

    @Override
    protected StuffTableInfo getTableInfo() {
        return stuffTableInfo;
    }

    @Override
    protected DtoRetriever<DoctorDTO> getDtoRetriever() {
        return dtoRetriever;
    }

    @Override
    protected StuffDtoValueSupplier<DoctorDTO> getDtoValueSupplier() {
        return valueSupplier;
    }

    @Override
    protected List<String> getSelectingColumns() {
        return selectingColumns;
    }
}
