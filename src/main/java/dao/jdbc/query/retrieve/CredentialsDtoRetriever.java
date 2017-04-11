package dao.jdbc.query.retrieve;

import domain.CredentialsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialsDtoRetriever extends AbstractDtoRetriever<CredentialsDTO> {
    @Override
    protected CredentialsDTO retrieve(ResultSet resultSet) throws SQLException {
        return new CredentialsDTO.Builder()
                .setId(resultSet.getLong(1))
                .setLogin(resultSet.getString(2))
                .setPassword(resultSet.getString(3))
                .setRole(resultSet.getLong(4))
                .build();
    }
}
