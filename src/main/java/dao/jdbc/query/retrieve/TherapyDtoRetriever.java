package dao.jdbc.query.retrieve;

import domain.TherapyDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TherapyDtoRetriever extends AbstractDtoRetriever<TherapyDTO> {
    @Override
    protected TherapyDTO retrieve(ResultSet resultSet) throws SQLException {
        return new TherapyDTO.Builder()
                .setId(resultSet.getLong(1))
                .setTitle(resultSet.getString(2))
                .setType(TherapyDTO.Type.valueOf(resultSet.getString(3)))
                .setDescription(resultSet.getString(4))
                .setAppointmentDateTime(resultSet.getTimestamp(5))
                .setCompleteDateTime(resultSet.getTimestamp(6))
                .setPatientId(resultSet.getLong(7))
                .setPerformerId(resultSet.getLong(8))
                .build();
    }
}
