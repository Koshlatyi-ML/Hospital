package dao.jdbc.query;

import dao.jdbc.query.retrieve.DoctorEntityRetriever;
import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.supply.DoctorValueSupplier;
import dao.jdbc.query.supply.StuffValueSupplier;
import dao.metadata.DoctorTableInfo;
import dao.metadata.PatientTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DoctorQueryExecutor extends StuffQueryExecutor<Doctor> {
    private DoctorTableInfo tableInfo;
    private DoctorEntityRetriever entityRetriever;
    private DoctorValueSupplier valueSupplier;
    private PatientTableInfo patientTableInfo;

    private final String STUFF_INNER_JOIN =
            String.format(" %s INNER JOIN %s ON %s.%s = %s.%s ",
                    tableInfo.getStuffTableName(),
                    tableInfo.getTableName(),
                    tableInfo.getStuffTableName(),
                    tableInfo.getIdColumn(),
                    tableInfo.getTableName(),
                    tableInfo.getStuffIdColumn());

    private final String FIND_BY_PATIENT_ID_QUERY =
            String.format("SELECT %s " + STUFF_INNER_JOIN +
                            "INNER JOIN %s ON %s.%s = %s.%s " +
                            "WHERE %s.%s = ?;",
                    Queries.formatColumnNames(tableInfo.getColumns()),
                    patientTableInfo.getTableName(),
                    tableInfo.getTableName(), tableInfo.getStuffIdColumn(),
                    patientTableInfo.getTableName(), patientTableInfo.getDoctorIdColumn(),
                    patientTableInfo.getTableName(), patientTableInfo.getIdColumn());
/*

    private final String FIND_ALL_QUERY =
            String.format("SELECT * FROM" + STUFF_INNER_JOIN + ";");

    private final String FIND_BY_ID_QUERY =
            String.format("SELECT * FROM" + STUFF_INNER_JOIN + "WHERE %s = ?;",
                    getTableInfo().getIdColumn());
*/

    private final String INSERT_QUERY =
            String.format("INSERT INTO %s %s VALUES %s;",
                    tableInfo.getTableName(),
                    Queries.formatColumnNames(tableInfo.getDoctorColumns()),
                    Queries.formatPlaceholders(tableInfo.getDoctorColumns().size()));

    private final String UPDATE_QUERY =
            String.format("UPDATE %s SET %s WHERE %s = ?;",
                    tableInfo.getTableName(),
                    Queries.formatColumnNames(tableInfo.getDoctorColumns()),
                    Queries.formatColumnPlaceholders(tableInfo.getDoctorColumns()),
                    getTableInfo().getStuffIdColumn());

    private final String DELETE_QUERY =
            String.format("DELETE FROM %s WHERE %s = ?;",
                    tableInfo.getTableName(),
                    tableInfo.getStuffIdColumn());


    public DoctorQueryExecutor(TableInfoFactory tableInfoFactory) {
        tableInfo = tableInfoFactory.getDoctorTableInfo();
        entityRetriever = new DoctorEntityRetriever();
        valueSupplier = new DoctorValueSupplier();
        patientTableInfo = tableInfoFactory.getPatientTableInfo();
    }

    @Override
    public ResultSet queryInsert(Connection connection, Doctor entity) throws SQLException {
        ResultSet generatedKeys = super.queryInsert(connection, entity);
        long stuffId = generatedKeys.getLong(1);

        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setLong(1, stuffId);
            statement.execute();
            return statement.getGeneratedKeys();
        }
    }

    @Override
    public void queryUpdate(Connection connection, Doctor entity) throws SQLException {
        super.queryUpdate(connection, entity);

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            valueSupplier.supplyStuffValues(statement, entity);
            statement.execute();
        }
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        super.queryDelete(connection, id);

        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public Optional<Doctor> queryFindByPatientId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_PATIENT_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntity(resultSet);
        }
    }

    @Override
    protected DoctorTableInfo getTableInfo() {
        return tableInfo;
    }

    @Override
    protected EntityRetriever<Doctor> getEntityRetriever() {
        return entityRetriever;
    }

    @Override
    protected StuffValueSupplier<Doctor> getValueSupplier() {
        return valueSupplier;
    }
}
