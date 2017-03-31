package dao.jdbc.query.supply;

import domain.dto.AbstractDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DtoValueSupplier<T extends AbstractDTO> {
    int supplyValues(PreparedStatement statement, T dto) throws SQLException;
}
