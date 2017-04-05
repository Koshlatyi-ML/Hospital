package dao.jdbc.query.supply;

import domain.dto.TherapyDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TherapyDtoValueSupplier implements DtoValueSupplier<TherapyDTO> {
    TherapyDtoValueSupplier() {}

    @Override
    public int supplyValues(PreparedStatement statement, TherapyDTO dto) throws SQLException {
        statement.setString(1, dto.getTitle());
        statement.setString(2, dto.getType());
        statement.setString(3, dto.getDescription());
        statement.setTimestamp(4, dto.getAppointmentDateTime());
        statement.setTimestamp(5, dto.getCompleteDateTime());
        statement.setLong(6, dto.getPatientId());
        statement.setLong(7, dto.getPerformerId());
        return 7;
    }
}
