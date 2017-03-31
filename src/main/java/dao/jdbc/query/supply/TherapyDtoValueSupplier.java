package dao.jdbc.query.supply;

import domain.dto.TherapyDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TherapyDtoValueSupplier implements DtoValueSupplier<TherapyDTO> {
    TherapyDtoValueSupplier() {}

    @Override
    public int supplyValues(PreparedStatement statement, TherapyDTO dto) throws SQLException {
        statement.setString(1, dto.getName());
        statement.setString(2, dto.getType().toString());
        statement.setString(3, dto.getDescription());
        statement.setTimestamp(4, Timestamp.from(dto.getAppointmentDateTime()));
        statement.setTimestamp(5, Timestamp.from(dto.getCompleteDateTime()));
        statement.setLong(6, dto.getPatientId());
        statement.setLong(7, dto.getPerformerId());
        return 7;
    }
}
