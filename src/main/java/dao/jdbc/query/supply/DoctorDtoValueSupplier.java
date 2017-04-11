package dao.jdbc.query.supply;

import domain.DoctorDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorDtoValueSupplier implements StuffDtoValueSupplier<DoctorDTO> {
    DoctorDtoValueSupplier() {}

    @Override
    public int supplyValues(PreparedStatement statement, DoctorDTO dto) throws SQLException {
        statement.setLong(1, dto.getId());
        statement.setLong(2, dto.getCredentialsId());
        return 2;
    }

    @Override
    public int supplyStuffValues(PreparedStatement statement, DoctorDTO dto) throws SQLException {
        statement.setString(1, dto.getName());
        statement.setString(2, dto.getSurname());
        statement.setLong(3, dto.getDepartmentId());
        return 3;
    }
}
