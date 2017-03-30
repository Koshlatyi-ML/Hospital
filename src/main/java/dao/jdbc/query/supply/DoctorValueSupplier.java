package dao.jdbc.query.supply;

import domain.Doctor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

public class DoctorValueSupplier implements StuffValueSupplier<Doctor> {
    DoctorValueSupplier() {}

    @Override
    public int supplyValues(PreparedStatement statement, Doctor entity) throws SQLException {
        statement.setLong(1, entity.getId());
        return 1;
    }

    @Override
    public int supplyStuffValues(PreparedStatement statement, Doctor entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        return 2;
    }
}
