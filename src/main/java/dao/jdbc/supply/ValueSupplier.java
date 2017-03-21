package dao.jdbc.supply;

import domain.IdHolder;

import java.sql.PreparedStatement;

public interface ValueSupplier<T extends IdHolder> {
    void supplyValues(PreparedStatement statement, T entity);
}
