package dao.jdbc.query.supply;

import domain.Therapy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TherapyValueSupplier implements ValueSupplier<Therapy> {
    TherapyValueSupplier() {}

    @Override
    public void supplyValues(PreparedStatement statement, Therapy entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getDescription());
        statement.setString(3, entity.getType().toString());
        statement.setTimestamp(4, Timestamp.from(entity.getAppointmentDateTime()));
        statement.setTimestamp(5, Timestamp.from(entity.getCompleteDateTime()));
        statement.setLong(6, entity.getPatient().getId());
    }
}
