package dao.jdbc.supply;

import domain.IdHolder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ValueSupplier<T extends IdHolder> {
    void supplyValues(PreparedStatement statement, T entity) throws SQLException;
}
