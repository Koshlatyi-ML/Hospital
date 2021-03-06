package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import domain.TherapyDTO;
import util.PropertyLoader;

import java.sql.*;
import java.util.List;
import java.util.Properties;

@QueryResource("dao/query/therapies.properties")
public class TherapyQueryExecutor extends QueryExecutor<TherapyDTO> {

    private Properties queries;
    private DtoValueSupplier<TherapyDTO> dtoValueSupplier;
    private DtoRetriever<TherapyDTO> dtoRetriever;

    TherapyQueryExecutor() {
        queries = PropertyLoader.getProperties(this.getClass()
                .getDeclaredAnnotation(QueryResource.class).value());
    }

    void setDtoValueSupplier(DtoValueSupplier<TherapyDTO> dtoValueSupplier) {
        this.dtoValueSupplier = dtoValueSupplier;
    }

    void setDtoRetriever(DtoRetriever<TherapyDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    private List<TherapyDTO> queryFindByIdAndType(
            Connection connection, long id, TherapyDTO.Type type,
            int offset, int limit, String sqlQuery) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            statement.setString(2, type.toString());
            statement.setInt(3, offset);
            statement.setInt(4, limit);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    private long queryFindByIdAndTypeCount(
            Connection connection, long id, TherapyDTO.Type type, String sqlQuery)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            statement.setString(2, type.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    public List<TherapyDTO> queryFindByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findByPatientIdAndType"));
    }

    public long queryFindByPatientIdAndTypeCount(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndTypeCount(connection, id, type,
                queries.getProperty("findByPatientIdAndTypeCount"));
    }

    public List<TherapyDTO> queryFindCurrentByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findCurrentByPatientIdAndType"));
    }

    public long queryFindCurrentByPatientIdAndTypeCount(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndTypeCount(connection, id, type,
                queries.getProperty("findCurrentByPatientIdAndTypeCount"));
    }

    public List<TherapyDTO> queryFindFinishedByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findFinishedByPatientIdAndType"));
    }

    public long queryFindFinishedByPatientIdAndTypeCount(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndTypeCount(connection, id, type,
                queries.getProperty("findFinishedByPatientIdAndTypeCount"));
    }

    public List<TherapyDTO> queryFindFutureByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findFutureByPatientIdAndType"));
    }

    public long queryFindFutureByPatientIdAndTypeCount(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndTypeCount(connection, id, type,
                queries.getProperty("findFutureByPatientIdAndTypeCount"));
    }

    public List<TherapyDTO> queryFindByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findByPerformerIdAndType"));
    }

    public long queryFindByPerformerIdAndTypeCount(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndTypeCount(connection, id, type,
                queries.getProperty("findByPerformerIdAndTypeCount"));
    }

    public List<TherapyDTO> queryFindCurrentByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findCurrentByPerformerIdAndType"));
    }

    public long queryFindCurrentByPerformerIdAndTypeCount(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndTypeCount(connection, id, type,
                queries.getProperty("findCurrentByPerformerIdAndTypeCount"));
    }

    public List<TherapyDTO> queryFindFinishedByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findFinishedByPerformerIdAndType"));
    }

    public long queryFindFinishedByPerformerIdAndTypeCount(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndTypeCount(connection, id, type,
                queries.getProperty("findFinishedByPerformerIdAndTypeCount"));
    }

    public List<TherapyDTO> queryFindFutureByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findFutureByPerformerIdAndType"));
    }

    public long queryFindFutureByPerformerIdAndTypeCount(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndTypeCount(connection, id, type,
                queries.getProperty("findFutureByPerformerIdAndTypeCount"));
    }

    public List<TherapyDTO> queryFindFinishedByPatientId(
            Connection connection, long id, int offset, int limit) throws SQLException {

        try (PreparedStatement statement = connection
                .prepareStatement(queries.getProperty("findFinishedByPatientId"))) {

            statement.setLong(1, id);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public long queryFindFinishedByPatientIdCount(
            Connection connection, long id) throws SQLException {

        try (PreparedStatement statement = connection
                .prepareStatement(queries.getProperty("findFinishedByPatientIdCount"))) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    public List<TherapyDTO> queryFindCurrentByPerformerId(Connection connection, long id,
                                                          int offset, int limit) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(
                queries.getProperty("findCurrentByPerformerId"))) {

            statement.setLong(1, id);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public long queryFindCurrentByPerformerIdCount(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection
                .prepareStatement(queries.getProperty("findCurrentByPerformerIdCount"))) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    public List<TherapyDTO> queryFindNotFinishedByPatientId(
            Connection connection, long id, int offset, int limit) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(
                queries.getProperty("findNotFinishedByPatientId"))) {

            statement.setLong(1, id);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public long queryFindNotFinishedByPatientIdCount(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(
                queries.getProperty("findNotFinishedByPatientIdCount"))) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    @Override
    protected Properties getQueries() {
        return queries;
    }

    @Override
    protected DtoRetriever<TherapyDTO> getDtoRetriever() {
        return dtoRetriever;
    }

    @Override
    protected DtoValueSupplier<TherapyDTO> getDtoValueSupplier() {
        return dtoValueSupplier;
    }
}
