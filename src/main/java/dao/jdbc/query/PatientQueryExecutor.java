package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import domain.DoctorDTO;
import domain.PatientDTO;
import util.PropertyLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@QueryResource("dao/query/patients.properties")
public class PatientQueryExecutor extends QueryExecutor<PatientDTO> {

    private Properties queries;
    private DtoValueSupplier<PatientDTO> dtoValueSupplier;
    private DtoRetriever<PatientDTO> dtoRetriever;

    PatientQueryExecutor() {
        queries = PropertyLoader.getProperties(this.getClass()
                .getDeclaredAnnotation(QueryResource.class).value());
    }

    void setDtoValueSupplier(DtoValueSupplier<PatientDTO> dtoValueSupplier) {
        this.dtoValueSupplier = dtoValueSupplier;
    }

    void setDtoRetriever(DtoRetriever<PatientDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    public List<PatientDTO> queryFindByFullName(Connection connection, String fullName,
                                                int offset, int limit) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByDepartment"))) {

            statement.setString(1, "%" + fullName + "%");
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public Optional<PatientDTO> queryFindByLoginAndPassword(
            Connection connection, String login, String password) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByLoginAndPassword"))) {

            statement.setString(1, login);
            statement.setString(2, password);
            return dtoRetriever.retrieveDTO(statement.executeQuery());
        }
    }

    public Optional<PatientDTO> queryFindByCredentialsId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByCredentialsId"))) {

            statement.setLong(1, id);
            return dtoRetriever.retrieveDTO(statement.executeQuery());
        }
    }

    public List<PatientDTO> queryFindByDepartmentId(Connection connection, long id,
                                                    int offset, int limit) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByDepartmentId"))) {

            statement.setLong(1, id);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public List<PatientDTO> queryFindByDoctorId(Connection connection, long id,
                                                int offset, int limit) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByDoctorId"))) {

            statement.setLong(1, id);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public List<PatientDTO> queryFindByState(Connection connection, PatientDTO.State state,
                                             int offset, int limit) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByState"))) {

            statement.setString(1, state.toString());
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public List<PatientDTO> queryFindByDoctorIdAndState(Connection connection, long doctorId,
                                                        PatientDTO.State state, int offset,
                                                        int limit) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(
                queries.getProperty("findByDoctorIdAndState"))) {

            statement.setLong(1, doctorId);
            statement.setString(2, state.toString());
            statement.setInt(3, offset);
            statement.setInt(4, limit);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    @Override
    protected DtoRetriever<PatientDTO> getDtoRetriever() {
        return dtoRetriever;
    }

    @Override
    protected DtoValueSupplier<PatientDTO> getDtoValueSupplier() {
        return dtoValueSupplier;
    }

    @Override
    protected Properties getQueries() {
        return queries;
    }

}
