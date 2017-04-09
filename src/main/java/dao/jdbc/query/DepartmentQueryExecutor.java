package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import domain.dto.DepartmentDTO;
import util.load.PropertyLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

@QueryResource("dao/query/departments.properties")
public class DepartmentQueryExecutor extends QueryExecutor<DepartmentDTO> {

    private Properties queries;
    private DtoValueSupplier<DepartmentDTO> dtoValueSupplier;
    private DtoRetriever<DepartmentDTO> dtoRetriever;

    DepartmentQueryExecutor() {
        queries = PropertyLoader.getProperties(this.getClass()
                .getDeclaredAnnotation(QueryResource.class).value());
    }

    void setDtoValueSupplier(DtoValueSupplier<DepartmentDTO> dtoValueSupplier) {
        this.dtoValueSupplier = dtoValueSupplier;
    }

    void setDtoRetriever(DtoRetriever<DepartmentDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    public Optional<DepartmentDTO> queryFindByName(Connection connection, String name)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(queries.getProperty("findByName"))) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDTO(resultSet);
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected DtoRetriever<DepartmentDTO> getDtoRetriever() {
        return dtoRetriever;
    }

    @Override
    protected DtoValueSupplier<DepartmentDTO> getDtoValueSupplier() {
        return dtoValueSupplier;
    }

    @Override
    protected Properties getQueries() {
        return queries;
    }
}
