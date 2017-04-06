package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.ColumnNameStyle;
import dao.metadata.DepartmentTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Department;
import domain.dto.DepartmentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DepartmentQueryExecutor extends QueryExecutor<DepartmentDTO> {
    private DepartmentTableInfo tableInfo;
    private DtoValueSupplier<DepartmentDTO> dtoValueSupplier;
    private DtoRetriever<DepartmentDTO> dtoRetriever;

    private List<String> selectingColumns;

    DepartmentQueryExecutor() {}

    void setTableInfo(DepartmentTableInfo tableInfo) {
        this.tableInfo = tableInfo;
        selectingColumns = Arrays.asList(
                tableInfo.getIdColumn(ColumnNameStyle.FULL),
                tableInfo.getNameColumn(ColumnNameStyle.FULL));
    }

    void setDtoValueSupplier(DtoValueSupplier<DepartmentDTO> dtoValueSupplier) {
        this.dtoValueSupplier = dtoValueSupplier;
    }

    void setDtoRetriever(DtoRetriever<DepartmentDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    private String getFindByNameQuery() {
        return String.format("SELECT %s FROM %s WHERE %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                tableInfo.getTableName(),
                tableInfo.getNameColumn(ColumnNameStyle.FULL));
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

    @Override
    protected List<String> getSelectingColumns() {
        return selectingColumns;
    }
}
