package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import domain.DoctorDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PropertyLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

@QueryResource("dao/query/doctors.properties")
public class DoctorQueryExecutor extends StuffQueryExecutor<DoctorDTO>
        implements CredentialsHolderQueryExecutor<DoctorDTO> {

    private Properties queries;
    private StuffDtoValueSupplier<DoctorDTO> valueSupplier;
    private DtoRetriever<DoctorDTO> dtoRetriever;

    DoctorQueryExecutor() {
        queries = PropertyLoader.getProperties(this.getClass()
                .getDeclaredAnnotation(QueryResource.class).value());
    }

    void setValueSupplier(StuffDtoValueSupplier<DoctorDTO> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    void setDtoRetriever(DtoRetriever<DoctorDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    @Override
    public void queryInsert(Connection connection, DoctorDTO dto) throws SQLException {
        queryInsertStuff(connection, dto);
        super.queryInsert(connection, dto);
    }

    @Override
    public void queryUpdate(Connection connection, DoctorDTO dto) throws SQLException {
        queryUpdateStuff(connection, dto);
        super.queryUpdate(connection, dto);
    }

    @Override
    public void queryDelete(Connection connection, DoctorDTO dto) throws SQLException {
        queryDelete(connection, dto.getId());
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        queryDeleteStuff(connection, id);
    }

    public Optional<DoctorDTO> queryFindByPatientId(Connection connection, long id)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByPatientId"))) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDTO(resultSet);
        }
    }

    @Override
    public Optional<DoctorDTO> queryFindByLoginAndPassword(
            Connection connection, String login, String password) throws SQLException {

        return CommonQueriesExecutor.findByLoginAndPassword(connection, login, password,
                queries.getProperty("findByLoginAndPassword"), dtoRetriever);
    }

    @Override
    public Optional<DoctorDTO> queryFindByCredentialsId(Connection connection, long id)
            throws SQLException {

        return CommonQueriesExecutor.findByCredentialsId(
                connection, id, queries.getProperty("findByCredentialsId"), dtoRetriever);
    }

    @Override
    protected DtoRetriever<DoctorDTO> getDtoRetriever() {
        return dtoRetriever;
    }

    @Override
    protected StuffDtoValueSupplier<DoctorDTO> getDtoValueSupplier() {
        return valueSupplier;
    }

    @Override
    protected Properties getQueries() {
        return queries;
    }
}
