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

    private List<TherapyDTO> queryFindByIdAndType(Connection connection, long id,
                                                  TherapyDTO.Type type, String sqlQuery)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            statement.setString(2, type.toString());
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public List<TherapyDTO> queryFindByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                queries.getProperty("findByPatientIdAndType"));
    }

    public List<TherapyDTO> queryFindCurrentByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                queries.getProperty("findCurrentByPatientIdAndType"));
    }

    public List<TherapyDTO> queryFindFinishedByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                queries.getProperty("findFinishedByPatientIdAndType"));
    }

    public List<TherapyDTO> queryFindFutureByPatientIdAndType(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                queries.getProperty("findFutureByPatientIdAndType"));
    }

    public List<TherapyDTO> queryFindByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                queries.getProperty("findByPerformerIdAndType"));
    }

    public List<TherapyDTO> queryFindCurrentByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                queries.getProperty("findCurrentByPerformerIdAndType"));
    }

    public List<TherapyDTO> queryFindFinishedByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                queries.getProperty("findFinishedByPerformerIdAndType"));
    }

    public List<TherapyDTO> queryFindFutureByPerformerIdAndType(
            Connection connection, long id, TherapyDTO.Type type) throws SQLException {

        return queryFindByIdAndType(connection, id, type,
                queries.getProperty("findFutureByPerformerIdAndType"));
    }

    public List<TherapyDTO> queryFindFinishedByPatientId(
            Connection connection, long id) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(
                queries.getProperty("findFinishedByPatientId"))) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public List<TherapyDTO> queryFindNotFinishedByPatientId(
            Connection connection, long id) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(
                queries.getProperty("findNotFinishedByPatientId"))) {

            statement.setLong(1, id);
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
