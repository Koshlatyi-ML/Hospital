package dao.jdbc.query.supply;

import domain.AbstractStuffDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StuffDtoValueSupplier<T extends AbstractStuffDTO> extends DtoValueSupplier<T> {
    int supplyStuffValues(PreparedStatement statement, T dto) throws SQLException;
}
