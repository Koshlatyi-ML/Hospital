package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.metadata.*;
import domain.Patient;
import domain.dto.PatientDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PatientQueryExecutor extends PersonQueryExecutor<PatientDTO> {
    private PatientTableInfo tableInfo;
    private StuffTableInfo stuffTableInfo;
    private DoctorTableInfo doctorTableInfo;
    private List<String> selectingColumns;
    private DtoValueSupplier<PatientDTO> dtoValueSupplier;
    private DtoRetriever<PatientDTO> dtoRetriever;

    PatientQueryExecutor() {
    }

    void setTableInfo(PatientTableInfo tableInfo) {
        this.tableInfo = tableInfo;
        selectingColumns = Arrays.asList(tableInfo.getIdColumn(ColumnNameStyle.FULL),
                tableInfo.getNameColumn(ColumnNameStyle.FULL),
                tableInfo.getSurnameColumn(ColumnNameStyle.FULL),
                tableInfo.getDoctorIdColumn(ColumnNameStyle.FULL),
                tableInfo.getComplaintsColumn(ColumnNameStyle.FULL),
                tableInfo.getDiagnosisColumn(ColumnNameStyle.FULL),
                tableInfo.getStateColumn(ColumnNameStyle.FULL),
                tableInfo.getCredentialsIdColumn(ColumnNameStyle.FULL));
    }

    void setStuffTableInfo(StuffTableInfo stuffTableInfo) {
        this.stuffTableInfo = stuffTableInfo;
    }

    void setDoctorTableInfo(DoctorTableInfo doctorTableInfo) {
        this.doctorTableInfo = doctorTableInfo;
    }

    void setDtoValueSupplier(DtoValueSupplier<PatientDTO> dtoValueSupplier) {
        this.dtoValueSupplier = dtoValueSupplier;
    }

    void setDtoRetriever(DtoRetriever<PatientDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    private String getFindByDepartmentIdQuery() {
        return String.format("SELECT %s FROM %s " +
                        "INNER JOIN %s ON %s=%s " +
                        "INNER JOIN %s ON %s=%s " +
                        "WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                tableInfo.getTableName(),
                doctorTableInfo.getTableName(),
                tableInfo.getDoctorIdColumn(ColumnNameStyle.FULL),
                doctorTableInfo.getIdColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getTableName(),
                doctorTableInfo.getIdColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getIdColumn(ColumnNameStyle.FULL),
                stuffTableInfo.getDepartmentIdColumn(ColumnNameStyle.FULL));
    }

    private String getFindByDoctorIdQuery() {
        return String.format("SELECT %s FROM %s WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                tableInfo.getTableName(),
                tableInfo.getDoctorIdColumn(ColumnNameStyle.FULL));
    }

    private String getFindByStateQuery() {
        return String.format("SELECT %s FROM %s WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                tableInfo.getTableName(),
                tableInfo.getStateColumn(ColumnNameStyle.FULL));
    }

    private String getFindByCredentialsIdQuery() {
        return String.format("SELECT %s FROM %s WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                tableInfo.getTableName(),
                tableInfo.getCredentialsIdColumn(ColumnNameStyle.FULL));
    }

    public List<PatientDTO> queryFindByDepartmentId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByDepartmentIdQuery())) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public List<PatientDTO> queryFindByDoctorId(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement
                     = connection.prepareStatement(getFindByDoctorIdQuery())) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public List<PatientDTO> queryFindByState(Connection connection, Patient.State state)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByStateQuery())) {

            statement.setString(1, state.toString());
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public Optional<PatientDTO> queryFindByCredentialsId(Connection connection, long id)
            throws SQLException {

        return AppUserStatementExecutor.queryFindByCredentialsId(
                connection, id, getFindByCredentialsIdQuery(), dtoRetriever);
    }

    @Override
    protected PatientTableInfo getTableInfo() {
        return tableInfo;
    }

    @Override
    protected DtoRetriever<PatientDTO> getDtoRetriever() {
        return dtoRetriever;
    }

    @Override
    protected DtoValueSupplier<PatientDTO> getDtoValueSupplier() {
        return dtoValueSupplier;
    }

    @Override
    protected List<String> getSelectingColumns() {
        return selectingColumns;
    }
}
