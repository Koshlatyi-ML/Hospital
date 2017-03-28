package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.EntityRetrieverFactory;
import dao.jdbc.query.supply.StuffValueSupplier;
import dao.jdbc.query.supply.ValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
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
    private PatientTableInfo patientTableInfo;
    private StuffValueSupplier<Doctor> valueSupplier;
    private EntityRetriever<Doctor> entityRetriever;

    DoctorQueryExecutor(TableInfoFactory tableInfoFactory,
                               ValueSupplierFactory valueSupplierFactory,
                               EntityRetrieverFactory entityRetrieverFactory) {

        tableInfo = tableInfoFactory.getDoctorTableInfo();
        patientTableInfo = tableInfoFactory.getPatientTableInfo();
        valueSupplier = valueSupplierFactory.getDoctorValueSupplier();
        entityRetriever = entityRetrieverFactory.getDoctorEntityRetriever();
    }

    private String getStuffInnerJoin() {
        return String.format(" %s INNER JOIN %s ON %s = %s ",
                tableInfo.getStuffTableName(),
                tableInfo.getTableName(),
                tableInfo.getIdColumn(),
                tableInfo.getStuffIdColumn());
    }

    private String getFindByPatientIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "INNER JOIN %s ON %s = %s " +
                        "WHERE %s = ?;",
                Queries.formatColumnNames(tableInfo.getColumns()),
                patientTableInfo.getTableName(),
                tableInfo.getStuffIdColumn(),
                patientTableInfo.getDoctorIdColumn(),
                patientTableInfo.getIdColumn());
    }
/*

    private final String FIND_ALL_QUERY =
            String.format("SELECT * FROM" + STUFF_INNER_JOIN + ";");

    private final String FIND_BY_ID_QUERY =
            String.format("SELECT * FROM" + STUFF_INNER_JOIN + "WHERE %s = ?;",
                    getTableInfo().getIdColumn());
*/

    String getInsertQuery() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                tableInfo.getTableName(),
                Queries.formatColumnNames(tableInfo.getDoctorColumns()),
                Queries.formatPlaceholders(tableInfo.getDoctorColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s = ?;",
                tableInfo.getTableName(),
                Queries.formatColumnPlaceholders(tableInfo.getDoctorColumns()),
                getTableInfo().getStuffIdColumn());
    }

    String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE %s = ?;",
                tableInfo.getTableName(),
                tableInfo.getStuffIdColumn());
    }

    @Override
    public ResultSet queryInsert(Connection connection, Doctor entity) throws SQLException {
        ResultSet generatedKeys = super.queryInsert(connection, entity);
        long stuffId = generatedKeys.getLong(1);

        try (PreparedStatement statement =
                     connection.prepareStatement(getInsertQuery())) {
            statement.setLong(1, stuffId);
            statement.execute();
            return statement.getGeneratedKeys();
        }
    }

    public Optional<Doctor> queryFindByPatientId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByPatientIdQuery())) {
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
