package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.*;
import domain.Patient;
import domain.dto.PatientDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PatientQueryExecutor extends PersonQueryExecutor<PatientDTO> {
    private PatientTableInfo tableInfo;
    private StuffTableInfo stuffTableInfo;
    private DoctorTableInfo doctorTableInfo;
    private final List<String> selectingColumns;
    private DtoValueSupplier<PatientDTO> dtoValueSupplier;
    private DtoRetriever<PatientDTO> dtoRetriever;

    PatientQueryExecutor(TableInfoFactory tableInfoFactory,
                                ValueSupplierFactory valueSupplierFactory,
                                DtoRetrieverFactory dtoRetrieverFactory) {

        tableInfo = tableInfoFactory.getPatientTableInfo();
        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
        doctorTableInfo = tableInfoFactory.getDoctorTableInfo();
        dtoValueSupplier = valueSupplierFactory.getPatientDtoValueSupplier();
        dtoRetriever = dtoRetrieverFactory.getPatientDtoRetriever();

        selectingColumns = Arrays.asList(tableInfo.getIdColumn(ColumnNameStyle.FULL),
                tableInfo.getNameColumn(ColumnNameStyle.FULL),
                tableInfo.getSurnameColumn(ColumnNameStyle.FULL),
                tableInfo.getDoctorIdColumn(ColumnNameStyle.FULL),
                tableInfo.getComplaintsColumn(ColumnNameStyle.FULL),
                tableInfo.getDiagnosisColumn(ColumnNameStyle.FULL),
                tableInfo.getStateColumn(ColumnNameStyle.FULL));
    }

    private String getFindByDepartmentIdQuery() {
        return String.format("SELECT %s FROM %s " +
                        "INNER JOIN %s ON %s = %s " +
                        "INNER JOIN %s ON %s = %s " +
                        "WHERE %s = ?;",
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
        return String.format("SELECT %s FROM %s WHERE %s = ?;",
                Queries.formatAliasedColumns(selectingColumns),
                tableInfo.getTableName(),
                tableInfo.getDoctorIdColumn(ColumnNameStyle.FULL));
    }

    private String getFindByStateQuery() {
        return String.format("SELECT %s FROM %s WHERE %s = ?;",
                Queries.formatAliasedColumns(selectingColumns),
                tableInfo.getTableName(),
                tableInfo.getStateColumn(ColumnNameStyle.FULL));
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
