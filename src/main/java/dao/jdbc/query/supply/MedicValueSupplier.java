package dao.jdbc.query.supply;

import domain.Medic;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicValueSupplier implements StuffValueSupplier<Medic> {
    MedicValueSupplier() {}

    @Override
    public int supplyValues(PreparedStatement statement, Medic entity) throws SQLException {
        statement.setLong(1, entity.getId());
        return 1;
    }

    @Override
    public int supplyStuffValues(PreparedStatement statement, Medic entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        return 2;
    }

}
