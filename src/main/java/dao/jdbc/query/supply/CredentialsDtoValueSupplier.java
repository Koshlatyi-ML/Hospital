package dao.jdbc.query.supply;

import domain.CredentialsDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CredentialsDtoValueSupplier implements DtoValueSupplier<CredentialsDTO> {
    @Override
    public int supplyValues(PreparedStatement statement, CredentialsDTO dto) throws SQLException {
        statement.setString(1, dto.getLogin());
        statement.setString(2, dto.getPassword());
        statement.setLong(3, dto.getRole());
        return 3;
    }
}
