package dao.jdbc.query.supply;

import domain.Doctor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorValueSupplier implements StuffValueSupplier<Doctor> {
    DoctorValueSupplier() {}

    @Override
    public void supplyValues(PreparedStatement statement, Doctor entity) throws SQLException {}

    @Override
    public void supplyStuffValues(PreparedStatement statement, Doctor entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
    }
}
