package dao.jdbc.query.supply;

import domain.AbstractDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface DtoValueSupplier<T extends AbstractDTO> {
    int supplyValues(PreparedStatement statement, T dto) throws SQLException;
}
