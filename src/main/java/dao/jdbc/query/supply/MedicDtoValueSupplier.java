package dao.jdbc.query.supply;

import domain.dto.MedicDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicDtoValueSupplier implements StuffDtoValueSupplier<MedicDTO> {
    MedicDtoValueSupplier() {}

    @Override
    public int supplyValues(PreparedStatement statement, MedicDTO dto) throws SQLException {
        statement.setLong(1, dto.getId());
        return 1;
    }

    @Override
    public int supplyStuffValues(PreparedStatement statement, MedicDTO dto) throws SQLException {
        statement.setString(1, dto.getName());
        statement.setString(2, dto.getSurname());
        return 2;
    }

}
