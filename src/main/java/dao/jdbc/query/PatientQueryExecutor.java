package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.EntityRetrieverFactory;
import dao.jdbc.query.retrieve.PatientEntityRetriever;
import dao.jdbc.query.supply.PatientValueSupplier;
import dao.jdbc.query.supply.ValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.DoctorTableInfo;
import dao.metadata.PatientTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PatientQueryExecutor extends PersonQueryExecutor<Patient> {
    private PatientTableInfo tableInfo;
    private StuffTableInfo stuffTableInfo;
    private DoctorTableInfo doctorTableInfo;
    private ValueSupplier<Patient> valueSupplier;
    private EntityRetriever<Patient> entityRetriever;

    PatientQueryExecutor(TableInfoFactory tableInfoFactory,
                                ValueSupplierFactory valueSupplierFactory,
                                EntityRetrieverFactory entityRetrieverFactory) {

        tableInfo = tableInfoFactory.getPatientTableInfo();
        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
        doctorTableInfo = tableInfoFactory.getDoctorTableInfo();
        valueSupplier = valueSupplierFactory.getPatientValueSupplier();
        entityRetriever = entityRetrieverFactory.getPatientEntityRetriever();
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

    public List<Patient> queryFindByDepartmentId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByDepartmentIdQuery())) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntityList(resultSet);
        }
    }

    public List<Patient> queryFindByDoctorId(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement
                     = connection.prepareStatement(getFindByDoctorIdQuery())) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntityList(resultSet);
        }
    }

    public List<Patient> queryFindByState(Connection connection, Patient.State state)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByStateQuery())) {

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
