package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import domain.Patient;
import domain.dto.PatientDTO;
import util.load.PropertyLoader;

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

    public List<PatientDTO> queryFindByFullName(Connection connection, String fullName)
            throws SQLException {
        return CommonQueriesExecutor.findByFullName(connection, fullName,
                queries.getProperty("findByDepartment"), dtoRetriever);
    }

    public Optional<PatientDTO> queryFindByCredentialsId(Connection connection, long id)
            throws SQLException {

        return CommonQueriesExecutor.findByCredentialsId(
                connection, id, queries.getProperty("findByCredentialsId"), dtoRetriever);
    }

    public List<PatientDTO> queryFindByDepartmentId(Connection connection, long id)
            throws SQLException {
        return CommonQueriesExecutor.findByDepartmentId(connection, id,
                queries.getProperty("findByDepartmentId"), dtoRetriever);
    }

    public List<PatientDTO> queryFindByDoctorId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByDoctorId"))) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    public List<PatientDTO> queryFindByState(Connection connection, Patient.State state)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByState"))) {

            statement.setString(1, state.toString());
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
