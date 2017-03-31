package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.DoctorTableInfo;
import dao.metadata.PatientTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Patient;
import domain.dto.PatientDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PatientQueryExecutor extends PersonQueryExecutor<PatientDTO> {
    private PatientTableInfo tableInfo;
    private StuffTableInfo stuffTableInfo;
    private DoctorTableInfo doctorTableInfo;
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
    }

    private String getFindByDepartmentIdQuery() {
        return String.format("SELECT %s FROM %s " +
                        "INNER JOIN %s ON %s = %s " +
                        "INNER JOIN %s ON %s = %s " +
                        "WHERE %s = ?;",
                Queries.formatColumnNames(tableInfo.getColumns()),
                tableInfo.getTableName(),
                doctorTableInfo.getTableName(),
                tableInfo.getDoctorIdColumn(),
                doctorTableInfo.getIdColumn(),
                stuffTableInfo.getTableName(),
                doctorTableInfo.getIdColumn(),
                stuffTableInfo.getIdColumn(),
                stuffTableInfo.getDepartmentIdColumn());
    }

    private String getFindByDoctorIdQuery() {
        return String.format("SELECT * FROM %s WHERE %s = ?;",
                tableInfo.getTableName(),
                tableInfo.getDoctorIdColumn());
    }

    private String getFindByStateQuery() {
        return String.format("SELECT * FROM %s WHERE %s = ?;",
                tableInfo.getTableName(),
                tableInfo.getStateColumn());
    }

    public List<PatientDTO> queryFindByDepartmentId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByDepartmentIdQuery())) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDTOList(resultSet);
        }
    }

    public List<PatientDTO> queryFindByDoctorId(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement
                     = connection.prepareStatement(getFindByDoctorIdQuery())) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDTOList(resultSet);
        }
    }

    public List<PatientDTO> queryFindByState(Connection connection, Patient.State state)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByStateQuery())) {

            statement.setString(1, state.toString());
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDTOList(resultSet);
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
}
