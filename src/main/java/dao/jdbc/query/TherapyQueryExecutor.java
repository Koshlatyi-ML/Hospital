package dao.jdbc.query;

import dao.TherapyDAO;
import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.*;
import domain.Therapy;
import domain.dto.TherapyDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TherapyQueryExecutor extends QueryExecutor<TherapyDTO> {
    private TherapyTableInfo tableInfo;
    private PatientTableInfo patientTableInfo;
    private DoctorTableInfo doctorTableInfo;
    private MedicTableInfo medicTableInfo;
    private DtoValueSupplier<TherapyDTO> dtoValueSupplier;
    private DtoRetriever<TherapyDTO> dtoRetriever;

    TherapyQueryExecutor(TableInfoFactory tableInfoFactory,
                                ValueSupplierFactory valueSupplierFactory,
                                DtoRetrieverFactory dtoRetrieverFactory) {

        tableInfo = tableInfoFactory.getTherapyTableInfo();
        patientTableInfo = tableInfoFactory.getPatientTableInfo();
        doctorTableInfo = tableInfoFactory.getDoctorTableInfo();
        medicTableInfo = tableInfoFactory.getMedicTableInfo();
        dtoValueSupplier = valueSupplierFactory.getTherapyDtoValueSupplier();
        dtoRetriever = dtoRetrieverFactory.getTherapyEntityetriever();
    }

    private List<String> getSeekableColumns() {
        List<String> seekableColumns = new ArrayList<>(tableInfo.getColumns());
        seekableColumns.addAll(patientTableInfo.getColumns());
        return seekableColumns;
    }

    private String getSelectAllFromTherapies() {
        return String.format("SELECT %s FROM %s",
                Queries.formatColumnNames(getSeekableColumns()),
                tableInfo.getTableName());
    }

    private String getInnerJoinPatients() {
        return String.format(" INNER JOIN %s ON %s = %s ",
                patientTableInfo.getTableName(),
                tableInfo.getPatientIdColumn(),
                patientTableInfo.getIdColumn());
    }

    private String getInnerJoinDoctors() {
        return String.format(" INNER JOIN %s ON %s = %s ",
                doctorTableInfo.getTableName(),
                tableInfo.getPerformerIdColumn(),
                doctorTableInfo.getIdColumn());
    }

    private String getInnerJoinMedics() {
        return String.format(" INNER JOIN %s ON %s = %s ",
                medicTableInfo.getTableName(),
                tableInfo.getPerformerIdColumn(),
                medicTableInfo.getIdColumn());
    }

    private String getFindByPatientIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        "WHERE %s = ? AND %s = ?;",
                patientTableInfo.getIdColumn(),
                tableInfo.getTypeColumn());
    }

    private String getFindCurrentByPatientIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s IS NULL AND %s < now();",
                patientTableInfo.getIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getCompleteDateColumn(),
                tableInfo.getAppointmentDateColumn());
    }

    private String getFindFinishedByPatientIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s IS NOT NULL;",
                patientTableInfo.getIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getCompleteDateColumn());
    }

    private String getFindFutureByPatientIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s > now();",
                patientTableInfo.getIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getAppointmentDateColumn());
    }

    private String getFindByDoctorIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinDoctors() +
                        "WHERE %s = ? AND %s = ?;",
                doctorTableInfo.getIdColumn(),
                tableInfo.getTypeColumn());
    }

    private String getFindCurrentByDoctorIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinDoctors() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s IS NULL AND %s < now();",
                doctorTableInfo.getIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getCompleteDateColumn(),
                tableInfo.getAppointmentDateColumn());
    }

    private String getFindFinishedByDoctorIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinDoctors() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s IS NOT NULL;",
                doctorTableInfo.getIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getCompleteDateColumn());
    }

    private String getFindFutureByDoctorIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinDoctors() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s > now();",
                doctorTableInfo.getIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getAppointmentDateColumn());
    }

    private String getFindByMedicIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinMedics() +
                        "WHERE %s = ? AND %s = ?;",
                medicTableInfo.getIdColumn(),
                tableInfo.getTypeColumn());
    }

    private String getFindCurrentByMedicIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinMedics() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s IS NULL AND %s < now();",
                medicTableInfo.getIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getCompleteDateColumn(),
                tableInfo.getAppointmentDateColumn());
    }

    private String getFindFinishedByMedicIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinMedics() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s IS NOT NULL;",
                medicTableInfo.getIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getCompleteDateColumn());
    }

    private String getFindFutureByMedicIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinMedics() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s > now();",
                medicTableInfo.getIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getAppointmentDateColumn());
    }

    private List<TherapyDTO> queryFindByIdAndType(
            Connection connection, long id, Therapy.Type type, String sqlQuery)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            statement.setString(2, type.toString());
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDTOList(resultSet);
        }
    }

    public List<TherapyDTO> queryFindByPatientIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindByPatientIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindCurrentByPatientIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindCurrentByPatientIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindFinishedByPatientIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFinishedByPatientIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindFutureByPatientIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFutureByPatientIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindByDoctorIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindByDoctorIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindCurrentByDoctorIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindCurrentByDoctorIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindFinishedByDoctorIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFinishedByDoctorIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindFutureByDoctorIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFutureByDoctorIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindByMedicIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindByMedicIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindCurrentByMedicIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindCurrentByMedicIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindFinishedByMedicIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFinishedByMedicIdAndTypeQuery());
    }

    public List<TherapyDTO> queryFindFutureByMedicIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFutureByMedicIdAndTypeQuery());
    }


    @Override
    protected DtoRetriever<TherapyDTO> getDtoRetriever() {
        return dtoRetriever;
    }

    @Override
    protected DtoValueSupplier<TherapyDTO> getDtoValueSupplier() {
        return dtoValueSupplier;
    }

    @Override
    protected TherapyTableInfo getTableInfo() {
        return tableInfo;
    }
}
