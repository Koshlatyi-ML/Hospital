package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.DepartmentTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Department;
import domain.dto.DepartmentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DepartmentQueryExecutor extends QueryExecutor<DepartmentDTO> {
    private DepartmentTableInfo tableInfo;
    private DtoValueSupplier<DepartmentDTO> dtoValueSupplier;
    private DtoRetriever<DepartmentDTO> dtoRetriever;

    DepartmentQueryExecutor(TableInfoFactory tableInfoFactory,
                                   ValueSupplierFactory valueSupplierFactory,
                                   DtoRetrieverFactory dtoRetrieverFactory) {

        dtoRetriever = dtoRetrieverFactory.getDepartmentDtoRetriever();
        dtoValueSupplier = valueSupplierFactory.getDepartmentDtoValueSupplier();
        tableInfo = tableInfoFactory.getDepartmentTableInfo();
    }

    private String getFindByNameQuery() {
        return String.format("SELECT * FROM %s WHERE %s = ?;",
                tableInfo.getTableName(),
                tableInfo.getNameColumn());
    }

    public Optional<DepartmentDTO> queryFindByName(Connection connection, String name)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByNameQuery())) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDTO(resultSet);
        } catch (SQLException e) {
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
    protected DepartmentTableInfo getTableInfo() {
        return tableInfo;
    }
}
