package dao.jdbc.supply;

import domain.Doctor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorValueSupplier implements ValueSupplier<Doctor> {
    @Override
    public void supplyValues(PreparedStatement statement, Doctor entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
    }
}
