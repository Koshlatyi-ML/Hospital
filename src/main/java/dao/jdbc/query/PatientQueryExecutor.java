package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.PatientEntityRetriever;
import dao.jdbc.query.supply.PatientValueSupplier;
import dao.jdbc.query.supply.ValueSupplier;
import dao.metadata.DoctorTableInfo;
import dao.metadata.PatientTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PatientQueryExecutor extends PersonQueryExecutor<Patient> {
    private PatientTableInfo tableInfo;
    private PatientEntityRetriever entityRetriever;
    private PatientValueSupplier valueSupplier;
    private DoctorTableInfo doctorTableInfo;

    private final String FIND_BY_DEPARTMENT_ID_QUERY =
            String.format("SELECT %s FROM %s " +
                            "INNER JOIN %s ON %s.%s = %s.%s " +
                            "INNER JOIN %s ON %s.%s = %s.%s " +
                            "WHERE %s.%s = ?;",
                    Queries.formatColumnNames(tableInfo.getColumns()),
                    tableInfo.getTableName(), doctorTableInfo.getTableName(),
                    tableInfo.getTableName(), tableInfo.getDoctorIdColumn(),
                    doctorTableInfo.getTableName(), doctorTableInfo.getStuffIdColumn(),
                    doctorTableInfo.getStuffTableName(),
                    doctorTableInfo.getTableName(), doctorTableInfo.getStuffIdColumn(),
                    doctorTableInfo.getStuffTableName(), doctorTableInfo.getIdColumn(),
                    doctorTableInfo.getStuffTableName(), doctorTableInfo.getDepartmentIdColumn());

    private final String FIND_BY_DOCTOR_ID_QUERY =
            String.format("SELECT * FROM %s WHERE %s = ?;",
                    tableInfo.getTableName(),
                    tableInfo.getDoctorIdColumn());

    private final String FIND_BY_STATE_QUERY =
            String.format("SELECT * FROM %s WHERE %s = ?;",
                    tableInfo.getTableName(),
                    tableInfo.getStateColumn());

    public PatientQueryExecutor(TableInfoFactory tableInfoFactory) {
        tableInfo = tableInfoFactory.getPatientTableInfo();
        entityRetriever = new PatientEntityRetriever();
        valueSupplier = new PatientValueSupplier();
        doctorTableInfo = tableInfoFactory.getDoctorTableInfo();
    }

    public List<Patient> findByDepartmentId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_DEPARTMENT_ID_QUERY)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntityList(resultSet);
        }
    }

    public List<Patient> findByDoctorId(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement
                     = connection.prepareStatement(FIND_BY_DOCTOR_ID_QUERY)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntityList(resultSet);
        }
    }

    public List<Patient> findByState(Connection connection, Patient.State state)
            throws SQLException {

        try(PreparedStatement statement =
                    connection.prepareStatement(FIND_BY_STATE_QUERY)) {

            statement.setString(1, state.toString());
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntityList(resultSet);
        }
    }

    @Override
    protected PatientTableInfo getTableInfo() {
        return tableInfo;
    }

    @Override
    protected EntityRetriever<Patient> getEntityRetriever() {
        return entityRetriever;
    }

    @Override
    protected ValueSupplier<Patient> getValueSupplier() {
        return valueSupplier;
    }
}
