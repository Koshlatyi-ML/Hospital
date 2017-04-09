package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import domain.dto.CredentialsDTO;
import util.load.PropertyLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

@QueryResource("dao/query/credentials.properties")
public class CredentialsQueryExecutor extends QueryExecutor<CredentialsDTO> {

    private Properties queries;
    private DtoValueSupplier<CredentialsDTO> valueSupplier;
    private DtoRetriever<CredentialsDTO> dtoRetriever;

    CredentialsQueryExecutor() {
        queries = PropertyLoader.getProperties(this.getClass()
                .getDeclaredAnnotation(QueryResource.class).value());
    }

    void setValueSupplier(DtoValueSupplier<CredentialsDTO> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    void setDtoRetriever(DtoRetriever<CredentialsDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    public Optional<CredentialsDTO> queryFindByLoginAndPassword(
            Connection connection, String login, String password) throws SQLException {

        try (PreparedStatement statement = connection
                .prepareStatement(queries.getProperty("findByLoginAndPassword"))) {

            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDTO(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected DtoRetriever<CredentialsDTO> getDtoRetriever() {
        return dtoRetriever;
    }

    @Override
    protected DtoValueSupplier<CredentialsDTO> getDtoValueSupplier() {
        return valueSupplier;
    }

    @Override
    protected Properties getQueries() {
        return queries;
    }
}
