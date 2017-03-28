package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.EntityRetrieverFactory;
import dao.jdbc.query.supply.ValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.*;
import domain.Therapy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TherapyQueryExecutor extends QueryExecutor<Therapy> {
    private TherapyTableInfo tableInfo;
    private PatientTableInfo patientTableInfo;
    private DoctorTableInfo doctorTableInfo;
    private MedicTableInfo medicTableInfo;
    private ValueSupplier<Therapy> valueSupplier;
    private EntityRetriever<Therapy> entityRetriever;

    TherapyQueryExecutor(TableInfoFactory tableInfoFactory,
                                ValueSupplierFactory valueSupplierFactory,
                                EntityRetrieverFactory entityRetrieverFactory) {

        tableInfo = tableInfoFactory.getTherapyTableInfo();
        patientTableInfo = tableInfoFactory.getPatientTableInfo();
        doctorTableInfo = tableInfoFactory.getDoctorTableInfo();
        medicTableInfo = tableInfoFactory.getMedicTableInfo();
        valueSupplier = valueSupplierFactory.getTherapyValueSupplier();
        entityRetriever = entityRetrieverFactory.getTherapyEntityetriever();
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
                doctorTableInfo.getStuffIdColumn());
    }

    private String getInnerJoinMedics() {
        return String.format(" INNER JOIN %s ON %s = %s ",
                medicTableInfo.getTableName(),
                tableInfo.getPerformerIdColumn(),
                medicTableInfo.getStuffIdColumn());
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
                doctorTableInfo.getStuffIdColumn(),
                tableInfo.getTypeColumn());
    }

    private String getFindCurrentByDoctorIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinDoctors() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s IS NULL AND %s < now();",
                doctorTableInfo.getStuffIdColumn(),
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
                doctorTableInfo.getStuffIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getCompleteDateColumn());
    }

    private String getFindFutureByDoctorIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinDoctors() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s > now();",
                doctorTableInfo.getStuffIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getAppointmentDateColumn());
    }

    private String getFindByMedicIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinMedics() +
                        "WHERE %s = ? AND %s = ?;",
                medicTableInfo.getStuffIdColumn(),
                tableInfo.getTypeColumn());
    }

    private String getFindCurrentByMedicIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinMedics() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s IS NULL AND %s < now();",
                medicTableInfo.getStuffIdColumn(),
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
                medicTableInfo.getStuffIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getCompleteDateColumn());
    }

    private String getFindFutureByMedicIdAndTypeQuery() {
        return String.format(getSelectAllFromTherapies() +
                        getInnerJoinPatients() +
                        getInnerJoinMedics() +
                        "WHERE %s = ? AND %s = ? " +
                        "AND %s > now();",
                medicTableInfo.getStuffIdColumn(),
                tableInfo.getTypeColumn(),
                tableInfo.getAppointmentDateColumn());
    }

    private List<Therapy> queryFindByIdAndType(
            Connection connection, long id, Therapy.Type type, String sqlQuery)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            statement.setString(2, type.toString());
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntityList(resultSet);
        }
    }

    public List<Therapy> queryFindByPatientIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindByPatientIdAndTypeQuery());
    }

    public List<Therapy> queryFindCurrentByPatientIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindCurrentByPatientIdAndTypeQuery());
    }

    public List<Therapy> queryFindFinishedByPatientIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFinishedByPatientIdAndTypeQuery());
    }

    public List<Therapy> queryFindFutureByPatientIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFutureByPatientIdAndTypeQuery());
    }

    public List<Therapy> queryFindByDoctorIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindByDoctorIdAndTypeQuery());
    }

    public List<Therapy> queryFindCurrentByDoctorIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindCurrentByDoctorIdAndTypeQuery());
    }

    public List<Therapy> queryFindFinishedByDoctorIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFinishedByDoctorIdAndTypeQuery());
    }

    public List<Therapy> queryFindFutureByDoctorIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFutureByDoctorIdAndTypeQuery());
    }

    public List<Therapy> queryFindByMedicIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindByMedicIdAndTypeQuery());
    }

    public List<Therapy> queryFindCurrentByMedicIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindCurrentByMedicIdAndTypeQuery());
    }

    public List<Therapy> queryFindFinishedByMedicIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFinishedByMedicIdAndTypeQuery());
    }

    public List<Therapy> queryFindFutureByMedicIdAndType(
            Connection connection, long id, Therapy.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                getFindFutureByMedicIdAndTypeQuery());
    }


    @Override
    protected EntityRetriever<Therapy> getEntityRetriever() {
        return entityRetriever;
    }

    @Override
    protected ValueSupplier<Therapy> getValueSupplier() {
        return valueSupplier;
    }

    @Override
    protected TherapyTableInfo getTableInfo() {
        return tableInfo;
    }
}
