package dao.jdbc.query;

import dao.DoctorDAO;
import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.DoctorTableInfo;
import dao.metadata.PatientTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Doctor;
import domain.dto.DoctorDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DoctorQueryExecutor extends StuffQueryExecutor<DoctorDTO> {
    private StuffTableInfo stuffTableInfo;
    private DoctorTableInfo doctorTableInfo;
    private PatientTableInfo patientTableInfo;
    private StuffDtoValueSupplier<DoctorDTO> valueSupplier;
    private DtoRetriever<DoctorDTO> dtoRetriever;

    DoctorQueryExecutor(TableInfoFactory tableInfoFactory,
                        ValueSupplierFactory valueSupplierFactory,
                        DtoRetrieverFactory dtoRetrieverFactory) {

        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
        doctorTableInfo = tableInfoFactory.getDoctorTableInfo();
        patientTableInfo = tableInfoFactory.getPatientTableInfo();
        valueSupplier = valueSupplierFactory.getDoctorDtoValueSupplier();
        dtoRetriever = dtoRetrieverFactory.getDoctorDtoRetriever();
    }

    private String getStuffInnerJoin() {
        return String.format(" %s INNER JOIN %s ON %s = %s ",
                stuffTableInfo.getTableName(),
                doctorTableInfo.getTableName(),
                stuffTableInfo.getIdColumn(),
                doctorTableInfo.getIdColumn());
    }

    private List<String> getJoinedColumns() {
        List<String> columns = stuffTableInfo.getColumns();
        columns.addAll(doctorTableInfo.getColumns());
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

    private String getFindByPatientIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "INNER JOIN %s ON %s = %s " +
                        "WHERE %s = ?;",
                Queries.formatColumnNames(getJoinedColumns()),
                patientTableInfo.getTableName(),
                stuffTableInfo.getIdColumn(),
                patientTableInfo.getDoctorIdColumn(),
                patientTableInfo.getIdColumn());
    }

    String getInsertQuery() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                doctorTableInfo.getTableName(),
                Queries.formatColumnNames(doctorTableInfo.getColumns()),
                Queries.formatPlaceholders(doctorTableInfo.getColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s = ?;",
                doctorTableInfo.getTableName(),
                Queries.formatColumnPlaceholders(doctorTableInfo.getColumns()),
                doctorTableInfo.getIdColumn());
    }

    String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE %s = ?;",
                doctorTableInfo.getTableName(),
                doctorTableInfo.getIdColumn());
    }

    @Override
    public void queryInsert(Connection connection, DoctorDTO dto) throws SQLException {
        super.queryInsertStuff(connection, dto);
        super.queryInsert(connection, dto);
    }

    @Override
    public void queryUpdate(Connection connection, DoctorDTO dto) throws SQLException {
        super.queryUpdateStuff(connection, dto);
        super.queryUpdate(connection, dto);
    }

    @Override
    public void queryDelete(Connection connection, DoctorDTO dto) throws SQLException {
        queryDelete(connection, dto.getId());
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        super.queryDelete(connection, id);
        super.queryDeleteStuff(connection, id);
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
}
