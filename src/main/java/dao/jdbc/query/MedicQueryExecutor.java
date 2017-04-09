package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import domain.dto.MedicDTO;
import util.load.PropertyLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

@QueryResource("dao/query/medics.properties")
public class MedicQueryExecutor extends StuffQueryExecutor<MedicDTO> {

    private Properties queries;
    private StuffDtoValueSupplier<MedicDTO> valueSupplier;
    private DtoRetriever<MedicDTO> dtoRetriever;

    MedicQueryExecutor() {
        queries = PropertyLoader.getProperties(this.getClass()
                .getDeclaredAnnotation(QueryResource.class).value());
    }

    void setValueSupplier(StuffDtoValueSupplier<MedicDTO> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    void setDtoRetriever(DtoRetriever<MedicDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    @Override
    public void queryInsert(Connection connection, MedicDTO dto) throws SQLException {
        queryInsertStuff(connection, dto);
        super.queryInsert(connection, dto);
    }

    @Override
    public void queryUpdate(Connection connection, MedicDTO dto) throws SQLException {
        queryUpdateStuff(connection, dto);
        super.queryUpdate(connection, dto);
    }

    @Override
    public void queryDelete(Connection connection, MedicDTO dto) throws SQLException {
        queryDelete(connection, dto.getId());
    }

    @Override
    public void queryDelete(Connection connection, long id) throws SQLException {
        queryDeleteStuff(connection, id);
    }

    public Optional<MedicDTO> queryFindByCredentialsId(Connection connection, long id)
            throws SQLException {

        return CommonQueriesExecutor.findByCredentialsId(
                connection, id, queries.getProperty("findByCredentialsId"), dtoRetriever);
    }

    @Override
    protected Properties getQueries() {
        return queries;
    }

    @Override
    protected StuffDtoValueSupplier<MedicDTO> getDtoValueSupplier() {
        return valueSupplier;
    }

    @Override
    protected DtoRetriever<MedicDTO> getDtoRetriever() {
        return dtoRetriever;
    }
}
