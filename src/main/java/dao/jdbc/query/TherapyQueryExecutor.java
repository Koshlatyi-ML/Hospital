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

    public List<TherapyDTO> queryFindByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findByPatientIdAndType"));
    }

    public List<TherapyDTO> queryFindCurrentByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findCurrentByPatientIdAndType"));
    }

    public List<TherapyDTO> queryFindFinishedByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findFinishedByPatientIdAndType"));
    }

    public List<TherapyDTO> queryFindFutureByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findFutureByPatientIdAndType"));
    }

    public List<TherapyDTO> queryFindByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findByPerformerIdAndType"));
    }

    public List<TherapyDTO> queryFindCurrentByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findCurrentByPerformerIdAndType"));
    }

    public List<TherapyDTO> queryFindFinishedByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findFinishedByPerformerIdAndType"));
    }

    public List<TherapyDTO> queryFindFutureByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type, int offset, int limit)
            throws SQLException {

        return queryFindByIdAndType(connection, id, type, offset, limit,
                queries.getProperty("findFutureByPerformerIdAndType"));
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
