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
import java.util.List;
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

    @Override
    String getFindAllQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() + ";",
                Queries.formatColumnNames(tableInfo.getColumns()));
    }

    @Override
    String getFindByIdQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s = ?;",
                Queries.formatColumnNames(tableInfo.getColumns()),
                tableInfo.getIdColumn());
    }


    public String getFindByFullNameQuery() {
        return String.format("SELECT %s FROM" +
                        getStuffInnerJoin() +
                        "WHERE %s LIKE %%?%% OR %s LIKE %%?%%;",
                Queries.formatColumnNames(tableInfo.getColumns()),
                tableInfo.getNameColumn(),
                tableInfo.getSurnameColumn());
    }

    @Override
    String getFindByDepartmentIdQuery() {
        return String.format("SELECT FROM %s WHERE %s = ?;",
                Queries.formatColumnNames(tableInfo.getColumns()),
                tableInfo.getDepartmentIdColumn());
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
    public void queryInsert(Connection connection, Doctor entity) throws SQLException {
        super.queryInsertStuff(connection, entity);
        super.queryInsert(connection, entity);
    }

    @Override
    public void queryUpdate(Connection connection, Doctor entity) throws SQLException {
        super.queryUpdateStuff(connection, entity);
        super.queryUpdate(connection, entity);
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        super.queryDeleteStuff(connection, id);
        super.queryDelete(connection, id);
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
