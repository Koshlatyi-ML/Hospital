package dao.jdbc.query;

import dao.jdbc.query.supply.StuffDtoValueSupplier;
import dao.metadata.ColumnNameStyle;
import dao.metadata.StuffTableInfo;
import domain.dto.AbstractStuffDTO;

import java.sql.*;
import java.util.List;


public abstract class StuffQueryExecutor<T extends AbstractStuffDTO> extends PersonQueryExecutor<T> {

     private String getInsertStuffQuery() {
        return String.format("INSERT INTO %s %s VALUES %s",
                getTableInfo().getTableName(),
                Queries.formatInsertColumnNames(getTableInfo().getNonGeneratingColumns()),
                Queries.formatInsertPlaceholders(getTableInfo().getNonGeneratingColumns().size()));
    }

    private String getUpdateStuffQuery() {
        return String.format("UPDATE %s SET %s WHERE %s=?",
                getTableInfo().getTableName(),
                Queries.formatUpdateColumnPlaceholders(getTableInfo().getNonGeneratingColumns()),
                getTableInfo().getIdColumn(ColumnNameStyle.SHORT));
    }

    private String getDeleteStuffQuery() {
        return String.format("DELETE FROM %s WHERE %s=?",
                getTableInfo().getTableName(),
                getTableInfo().getIdColumn(ColumnNameStyle.SHORT));
    }

    abstract String getFindByDepartmentIdQuery();

    void queryInsertStuff(Connection connection, T dto) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getInsertStuffQuery(),
                             Statement.RETURN_GENERATED_KEYS)) {
            getDtoValueSupplier().supplyStuffValues(statement, dto);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                dto.setId(generatedKeys.getLong(1));
            }
        }
    }

    void queryUpdateStuff(Connection connection, T dto) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getUpdateStuffQuery())) {
            int index = getDtoValueSupplier().supplyStuffValues(statement, dto);
            statement.setLong(++index, dto.getId());
            statement.execute();
        }
    }

    public void queryDeleteStuff(Connection connection, T dto) throws SQLException {
        queryDelete(connection, dto.getId());
    }

    void queryDeleteStuff(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getDeleteStuffQuery())) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public List<T> queryFindByDepartmentId(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByDepartmentIdQuery())) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDtoList(resultSet);
        }
    }

    @Override
    protected abstract StuffTableInfo getTableInfo();

    @Override
    protected abstract StuffDtoValueSupplier<T> getDtoValueSupplier();
}
