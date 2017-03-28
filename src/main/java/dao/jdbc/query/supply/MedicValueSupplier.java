package dao.jdbc.query.supply;

import domain.Medic;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicValueSupplier implements StuffValueSupplier<Medic> {
    MedicValueSupplier() {}

    @Override
    public void supplyValues(PreparedStatement statement, Medic entity) throws SQLException {
    }

    @Override
    public void supplyStuffValues(PreparedStatement statement, Medic entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
    }

}
