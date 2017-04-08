package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.metadata.ColumnNameStyle;
import dao.metadata.CredentialsTableInfo;
import domain.dto.CredentialsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CredentialsQueryExecutor extends QueryExecutor<CredentialsDTO> {
    private CredentialsTableInfo tableInfo;
    private DtoValueSupplier<CredentialsDTO> valueSupplier;
    private DtoRetriever<CredentialsDTO> dtoRetriever;
    private List<String> selectingColumns;

    CredentialsQueryExecutor() {
    }


    public void setTableInfo(CredentialsTableInfo tableInfo) {
        this.tableInfo = tableInfo;
        selectingColumns = Arrays.asList(
                tableInfo.getIdColumn(ColumnNameStyle.FULL),
                tableInfo.getLoginColumn(ColumnNameStyle.FULL),
                tableInfo.getPasswordColumn(ColumnNameStyle.FULL),
                tableInfo.getRoleColumn(ColumnNameStyle.FULL));
    }

    public void setValueSupplier(DtoValueSupplier<CredentialsDTO> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    public void setDtoRetriever(DtoRetriever<CredentialsDTO> dtoRetriever) {
        this.dtoRetriever = dtoRetriever;
    }

    private String getFindByLoginAndPasswordQuert() {
        return String.format("SELECT %s FROM %s WHERE %s=? AND %s=?",
                Queries.formatAliasedColumns(selectingColumns),
                tableInfo.getTableName(),
                tableInfo.getLoginColumn(ColumnNameStyle.FULL),
                tableInfo.getPasswordColumn(ColumnNameStyle.FULL));
    }

    public Optional<CredentialsDTO> queryFindByLoginAndPassword(
            Connection connection, String login, String password) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByLoginAndPasswordQuert())) {
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
    protected CredentialsTableInfo getTableInfo() {
        return tableInfo;
    }

    @Override
    protected List<String> getSelectingColumns() {
        return selectingColumns;
    }
}
