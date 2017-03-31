package dao.jdbc.query.supply;

import domain.dto.DoctorDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorDtoValueSupplier implements StuffDtoValueSupplier<DoctorDTO> {
    DoctorDtoValueSupplier() {}

    @Override
    public int supplyValues(PreparedStatement statement, DoctorDTO dto) throws SQLException {
        statement.setLong(1, dto.getId());
        return 1;
    }

    @Override
    public int supplyStuffValues(PreparedStatement statement, DoctorDTO dto) throws SQLException {
        statement.setString(1, dto.getName());
        statement.setString(2, dto.getSurname());
        statement.setLong(3, dto.getDepartmentId());
        return 3;
    }
}
