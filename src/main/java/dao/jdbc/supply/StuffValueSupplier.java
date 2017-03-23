package dao.jdbc.supply;

import domain.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StuffValueSupplier<T extends Person> extends ValueSupplier<T> {
    void supplyStuffValues(PreparedStatement statement, T entity) throws SQLException;
}
