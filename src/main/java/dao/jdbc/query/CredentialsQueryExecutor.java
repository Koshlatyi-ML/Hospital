package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import domain.CredentialsDTO;
import util.PropertyLoader;

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
