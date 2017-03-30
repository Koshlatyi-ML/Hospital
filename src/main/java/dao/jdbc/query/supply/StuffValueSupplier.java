package dao.jdbc.query.supply;

import domain.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StuffValueSupplier<T extends Person> extends ValueSupplier<T> {
    int supplyStuffValues(PreparedStatement statement, T entity) throws SQLException;
}
