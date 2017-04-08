package dao.jdbc.query.supply;

import domain.dto.MedicDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicDtoValueSupplier implements StuffDtoValueSupplier<MedicDTO> {
    MedicDtoValueSupplier() {}

    @Override
    public int supplyValues(PreparedStatement statement, MedicDTO dto) throws SQLException {
        statement.setLong(1, dto.getId());
        statement.setLong(2, dto.getCredentialsId());
        return 2;
    }

    @Override
    public int supplyStuffValues(PreparedStatement statement, MedicDTO dto) throws SQLException {
        statement.setString(1, dto.getName());
        statement.setString(2, dto.getSurname());
        statement.setLong(3, dto.getDepartmentId());
        return 3;
    }

}
