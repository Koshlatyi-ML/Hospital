package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.CredentialsTableInfo;
import domain.dto.CredentialsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialsDtoRetriever extends AbstractDtoRetriever<CredentialsDTO> {
    private CredentialsTableInfo tableInfo;

    CredentialsDtoRetriever(CredentialsTableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    @Override
    protected CredentialsDTO retrieve(ResultSet resultSet) throws SQLException {
        return new CredentialsDTO.Builder()
                .setId(resultSet.getLong(tableInfo.getIdColumn(ColumnNameStyle.FULL)))
                .setLogin(resultSet.getString(tableInfo.getLoginColumn(ColumnNameStyle.FULL)))
                .setPassword(resultSet.getString(tableInfo.getPasswordColumn(ColumnNameStyle.FULL)))
                .setRole(resultSet.getLong(tableInfo.getRoleColumn(ColumnNameStyle.FULL)))
                .build();
    }
}
