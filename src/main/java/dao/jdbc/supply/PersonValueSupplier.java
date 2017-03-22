package dao.jdbc.supply;

import domain.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonValueSupplier implements ValueSupplier<Person> {
    @Override
    public void supplyValues(PreparedStatement statement,
                             Person entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
    }
}
