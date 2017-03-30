package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.EntityRetrieverFactory;
import dao.jdbc.query.supply.StuffValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.DoctorTableInfo;
import dao.metadata.PatientTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Doctor;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DoctorQueryExecutor extends StuffQueryExecutor<Doctor> {
    private StuffTableInfo stuffTableInfo;
    private DoctorTableInfo doctorTableInfo;
    private PatientTableInfo patientTableInfo;
    private StuffValueSupplier<Doctor> valueSupplier;
    private EntityRetriever<Doctor> entityRetriever;

    DoctorQueryExecutor(TableInfoFactory tableInfoFactory,
                        ValueSupplierFactory valueSupplierFactory,
                        EntityRetrieverFactory entityRetrieverFactory) {

        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
        doctorTableInfo = tableInfoFactory.getDoctorTableInfo();
        patientTableInfo = tableInfoFactory.getPatientTableInfo();
        valueSupplier = valueSupplierFactory.getDoctorValueSupplier();
        entityRetriever = entityRetrieverFactory.getDoctorEntityRetriever();
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
                stuffTableInfo.getTableName(),
                Queries.formatColumnNames(stuffTableInfo.getColumns()),
                Queries.formatPlaceholders(stuffTableInfo.getColumns().size()));
    }

    String getUpdateQuery() {
        return String.format("UPDATE %s SET %s WHERE %s = ?;",
                stuffTableInfo.getTableName(),
                Queries.formatColumnPlaceholders(stuffTableInfo.getColumns()),
                stuffTableInfo.getIdColumn());
    }

    String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE %s = ?;",
                stuffTableInfo.getTableName(),
                stuffTableInfo.getIdColumn());
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
    public void queryDelete(Connection connection, Doctor entity) throws SQLException {
        super.queryDeleteStuff(connection, entity);
        super.queryDelete(connection, entity);
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
    protected StuffTableInfo getTableInfo() {
        return stuffTableInfo;
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
