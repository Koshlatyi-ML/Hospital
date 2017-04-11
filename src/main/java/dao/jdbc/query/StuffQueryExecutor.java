package dao.jdbc.query;

import dao.jdbc.query.supply.StuffDtoValueSupplier;
import domain.AbstractStuffDTO;
import util.PropertyLoader;

import java.sql.*;
import java.util.List;
import java.util.Properties;

@QueryResource("dao/query/stuff.properties")
public abstract class StuffQueryExecutor<T extends AbstractStuffDTO> extends QueryExecutor<T>
        implements PersonQueryExecutor<T>, DepartmentMemberQueryExecutor<T> {

    private Properties stuffQueries =
            PropertyLoader.getProperties(StuffQueryExecutor.class
                    .getDeclaredAnnotation(QueryResource.class).value());

    void queryInsertStuff(Connection connection, T dto) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(stuffQueries.getProperty("insert"),
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
                     connection.prepareStatement(stuffQueries.getProperty("update"))) {
            int index = getDtoValueSupplier().supplyStuffValues(statement, dto);
            statement.setLong(++index, dto.getId());
            statement.execute();
        }
    }

    void queryDeleteStuff(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(stuffQueries.getProperty("delete"))) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    @Override
    public List<T> queryFindByFullName(Connection connection, String fullName) throws SQLException {
        return CommonQueriesExecutor.findByFullName(connection, fullName,
                getQueries().getProperty("findByFullName"), getDtoRetriever());
    }

    @Override
    public List<T> queryFindByDepartmentId(Connection connection, long id) throws SQLException {
        return CommonQueriesExecutor.findByDepartmentId(connection, id,
                getQueries().getProperty("findByFullName"), getDtoRetriever());
    }

    @Override
    protected abstract StuffDtoValueSupplier<T> getDtoValueSupplier();
}
